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

import java.util.ArrayList;
import java.util.List;

import static com.intellij.psi.util.PsiMethodUtil.isMainMethod;
import static pascal.taie.intellij.gui.UiUtils.runOnUiThread;

public abstract class AnalyzeStarter implements Cancelable {

    public static final Logger logger = Logger.getInstance(AnalyzeStarter.class);

    @NotNull
    private final Project project;

    @NotNull
    private final String _title;

    private boolean finished = false;

    private boolean canceled = false;

    private ProgressIndicator indicator;

    private String cp;

    private String mainClass;

    protected AnalyzeStarter(
            @NotNull final Project project,
            @NotNull final String title
    ) {
        this.project = project;
        this._title = title;
    }

    public void start() {
        final AnalysisStatus status = AnalysisStatus.get(project);
        if (project.isDisposed() || !status.tryRun()) {
            logger.error("Project is disposed or another one is already running");
        } else { // ready to start analysis
            buildThenStart();
        }
    }

    public void buildThenStart() {
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
                            project, _title, true, PerformInBackgroundOption.ALWAYS_BACKGROUND
                    ) {
                        @Override
                        public void run(@NotNull ProgressIndicator indicator) {
                            AnalyzeStarter.this.indicator = indicator;
                            try {
                                finished = false;
                                indicator.setIndeterminate(true); // progress last time unknown
                                taskStart();
                            } finally {
                                if (!project.isDisposed()) {
                                    finished = true;
                                    status.stopRun();
                                }
                            }
                        }
                    };

                    DumbService.getInstance(project).runWhenSmart(task::queue);
                }
            });
        }
    }

    private void taskStart() {
        prepareAnalyze(); // set the mainClass, cp
        if (!checkIfCompiled()) return;

        try {
            startAnalyze(cp, mainClass);
            runOnUiThread(project, this::onFinish);
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

                mainClass = locateMainClass(project, module);
                cp = compilerOutputPath.getPath();
            }
        }
    }

    protected abstract void startAnalyze(String cp, String mainClass);

    /**
     * allowing UI updates
     */
    protected abstract void onFinish();

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void cancel() {
        if (!isFinished()) {
            canceled = true;

            logger.info("Analysis canceled");
        }
    }

    private String locateMainClass(Project project, Module module) {
        return ApplicationManager.getApplication().runReadAction((Computable<String>) () -> {
            String mainClassPath = "";

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

            if (mainClassPath == null || mainClassPath.isEmpty()) {
                logger.error("Main class not found");
                throw new RuntimeException("Main class not found");
            }

            return mainClassPath;
        });
    }

    private boolean checkIfCompiled() {
        /* must be compiled first */
        if (cp == null || mainClass == null) {
            return false;
        }
        return true;
    }

}
