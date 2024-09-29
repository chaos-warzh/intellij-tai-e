package pascal.taie.intellij.analysis;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.intellij.psi.util.PsiMethodUtil.isMainMethod;
import static pascal.taie.intellij.gui.UiUtils.runOnUiThread;

public class AnalyzeStarter implements Cancelable {

    public static final Logger logger = Logger.getInstance(AnalyzeStarter.class);

    @NotNull
    private static AnalyzeStarter analyzeStarter = new AnalyzeStarter();

    private Project project;

    private String title;

    private boolean finished = false;

    private boolean canceled = false;

    private ProgressIndicator indicator;

    private String cp;

    private String mainClass;


    private AnalyzeStarter() {}

    @NotNull
    public static AnalyzeStarter get() {
        return analyzeStarter;
    }

    public void start(@NotNull final Project project,
            @NotNull final String title,
            @NotNull final BiConsumer<String, String> startAnalyze,
            /* Run on UI Thread */
            @NotNull final Runnable onFinish
    ) {
        this.project = project;
        this.title = title;
        final AnalysisStatus status = AnalysisStatus.get(project);
        if (project.isDisposed() || !status.tryRun()) {
            logger.error("Project is disposed or another one is already running");
        } else { // ready to start analysis
            buildThenStart(startAnalyze, onFinish);
        }
    }

    private void buildThenStart(
            @NotNull final BiConsumer<String, String> startAnalyze,
            @NotNull final Runnable onFinish
    ) {
        logger.info("Start Intellij Tai-e analysis");
        final CompilerManager compilerManager = CompilerManager.getInstance(project);

        boolean compileBeforeAnalyze;
        compileBeforeAnalyze = true; // not compiled

        if (compileBeforeAnalyze) {
            // compile:
            CompileScope compileScope = compilerManager.createProjectCompileScope(project);
            compilerManager.make(compileScope, (aborted, errors, warnings, compileContext) -> {
                final AnalysisStatus status = AnalysisStatus.get(project);
                if (aborted || errors != 0) {
                    finished = true;
                    status.stopRun();
                } else { // no compile error
                    Task task = new Task.Backgroundable(
                            project, title, true, PerformInBackgroundOption.ALWAYS_BACKGROUND
                    ) {
                        @Override
                        public void run(@NotNull ProgressIndicator indicator) {
                            AnalyzeStarter.this.indicator = indicator;
                            try {
                                finished = false;
                                indicator.setIndeterminate(true); // progress last time unknown
                                taskStart(startAnalyze, onFinish);
                            } finally {
                                if (!project.isDisposed()) {
                                    finished = true;
                                    canceled = false;
                                    status.stopRun();
                                    AnalyzeStarter.this.indicator = null;
                                }
                            }
                        }
                    };

                    DumbService.getInstance(project).runWhenSmart(task::queue);
                }
            });
        }
    }

    private void taskStart(@NotNull final BiConsumer<String, String> startAnalyze, @NotNull final Runnable onFinish) {
        if (canceled) return;
        prepareAnalyze(); // set the mainClass, cp
        if (!checkIfCompiled()) return;

        try {
            if (canceled) return;
            startAnalyze.accept(cp, mainClass);
            if (canceled) return;
            runOnUiThread(project, onFinish);
        } catch (Exception t) {
            runOnUiThread(project, () -> Messages
                    .showWarningDialog("Error during analysis: \n" + t.getMessage(), "Error")
            );
        }
    }

    private void prepareAnalyze() {
        // todo: what does the codes below do?
        final Module[] modules = ModuleManager.getInstance(project).getModules();
        final List<Pair.NonNull<Module, VirtualFile>> compilerOutputPaths = new ArrayList<>();

        for (final Module module : modules) {
            final CompilerModuleExtension extension = CompilerModuleExtension.getInstance(module);
            if (extension == null) {
                throw new IllegalStateException("No compiler extension for module " + module.getName());
            }
            final VirtualFile compilerOutputPath = extension.getCompilerOutputPath();
            if (compilerOutputPath != null) {
                compilerOutputPaths.add(Pair.createNonNull(module, compilerOutputPath));
            }
        }

        if (!compilerOutputPaths.isEmpty()) {
            for (final Pair.NonNull<Module, VirtualFile> pair : compilerOutputPaths) {
                final Module module = pair.first;
                final VirtualFile compilerOutputPath = pair.second;
                logger.info("Module: " + module.getName() + ", compiler output path: " + compilerOutputPath.getName());

                cp = compilerOutputPath.getPath();
                mainClass = locateMainClass(project, module);
                if (mainClass == null || mainClass.isEmpty()) {
                    runOnUiThread(project, () -> Messages.showWarningDialog(
                            "Please make sure a main class is contained in your project!",
                            "Main Class Not Found"
                            )
                    );
                }
            }
        }
    }

    private boolean isFinished() {
        return finished;
    }

    @Override
    public void cancel() {
        if (!isFinished()) {
            logger.info("Analysis canceled");
            canceled = true;
            if (indicator != null) {
                indicator.cancel();
            }
        }
    }

    @Nullable
    private static String locateMainClass(final Project project, final Module module) {
        return ApplicationManager.getApplication().runReadAction((Computable<String>) () -> {
            String mainClassPath = null;

            GlobalSearchScope searchScope = GlobalSearchScope.moduleScope(module);
            String[] classNames = PsiShortNamesCache.getInstance(project).getAllClassNames();

            for (String className : classNames) {
                PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className, searchScope);
                for (PsiClass psiClass : psiClasses) {
                    PsiMethod[] methods = psiClass.getMethods();
                    for (PsiMethod method : methods) {
                        if (isMainMethod(method)) {
                            mainClassPath = psiClass.getQualifiedName();
                            logger.info("Main class found: " + mainClassPath);
                        }
                    }
                }
            }
            return mainClassPath;
        });
    }

    private boolean checkIfCompiled() {
        /* must be compiled first */
        return cp != null && mainClass != null && !mainClass.isEmpty();
    }

}
