package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.analysis.AnalysisStatus;

public abstract class AbstractAction extends AnAction {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public final void update(@NotNull AnActionEvent e) {
        Project p = e.getProject();
        if (p == null || !p.isInitialized() || p.isDisposed()) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        boolean visible = isVisible(e);
        e.getPresentation().setVisible(visible);
        if (!visible) {
            e.getPresentation().setEnabled(false);
            return;
        }

        e.getPresentation().setEnabled(isEnabled(e, p, AnalysisStatus.get(p)));

    }

    boolean isVisible(@NotNull AnActionEvent e) {
        return true;
    }

    boolean isEnabled(@NotNull AnActionEvent e, @NotNull Project project, @NotNull AnalysisStatus status) {
        return true;
    }

    /*
     * get the project,and start analysis
     */
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Project project = CommonDataKeys.PROJECT.getData(e.getDataContext()); // get the project
        if (project == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        actionPerformedImpl(e, project);
    }

    abstract void actionPerformedImpl(
            @NotNull final AnActionEvent e,
            @NotNull final Project project
    );

}
