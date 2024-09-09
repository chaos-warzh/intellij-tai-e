package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractAction extends AnAction {

    /*
     * get the project,and start analysis
     */
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Project project = CommonDataKeys.PROJECT.getData(e.getDataContext()); // get the project
        if (project == null) {
            e.getPresentation().setEnabled(false);
            e.getPresentation().setVisible(false);
            return;
        }
        actionPerformedImpl(e, project);
    }

    abstract void actionPerformedImpl(
            @NotNull final AnActionEvent e,
            @NotNull final Project project
    );


    @Nullable
    private static Module getModule(@NotNull final AnActionEvent e) {
        return PlatformCoreDataKeys.MODULE.getData(e.getDataContext()); // get the module
    }
}
