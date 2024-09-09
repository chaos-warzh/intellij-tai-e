package pascal.taie.intellij.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.ui.Messages;
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

public abstract class TaieStarter {

    public static final Logger logger = Logger.getInstance(TaieStarter.class);

    @NotNull
    private final Project project;

    @NotNull
    private final String _title;

    private String cp;
    private String mainClass;
    private String basePath;

    protected TaieStarter(
            @NotNull final Project project,
            @NotNull final String title
    ) {
        this.project = project;
        this._title = title;
    }

    public void start() {
        logger.info("Start Intellij Tai-e analysis");

        final CompilerManager compilerManager = CompilerManager.getInstance(project);

        boolean compileBeforeAnalyze = false;
        compileBeforeAnalyze = true; // not compiled
        if (compileBeforeAnalyze) {
            CompileScope compileScope = compilerManager.createProjectCompileScope(project);
            if (compileScope == null) {
                logger.error("Failed to create compile scope");
                return;
            }
            // compile:
            compilerManager.make(compileScope, (aborted, errors, warnings, compileContext) -> {
                if (!aborted && errors == 0) {
                    DumbService.getInstance(project).runWhenSmart(this::taskStart);
                }
            });
        } else {
            taskStart();
        }
    }

    private void taskStart() {
        /* async start */
        ProgressManager.getInstance().run(new Task.Backgroundable(
                project,
                "Running Tai-e analysis for project '" + project.getName() + "'..."
        ) {
            @Override
            public void run(@NotNull final ProgressIndicator indicator) {
                asyncStart(indicator);
                ApplicationManager.getApplication().invokeLater(() -> {
                    checkIfCompiled();
                });
                startAnalyze(cp, mainClass, basePath); // asyncStart
                ApplicationManager.getApplication().invokeLater(() -> {
                    onFinish(); // run on UI thread
                });
            }
        });
    }

    private void asyncStart(@NotNull final ProgressIndicator indicator) {
        indicator.setIndeterminate(true); // which means how long the progress will last is unknown

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

                String mainClass = locateMainClass(project, module);
                /**
                 * todo: analyze
                 */
                cp = compilerOutputPath.getPath();
                this.mainClass = mainClass;

                basePath = project.getBasePath();
            }
        }

    }

    protected abstract void startAnalyze(String cp, String mainClass, String basePath);

    protected abstract void onFinish();

    private String locateMainClass(Project project, Module module) {
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
    }

    private void checkIfCompiled() {
        /* must be compiled first */
        if (cp == null) {
            logger.error("No compiler output path found");
            Messages.showMessageDialog(project, "Please compile your project first!", "No Compiler Output Found", Messages.getErrorIcon());
        }
    }

}
