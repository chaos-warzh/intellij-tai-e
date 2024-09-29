package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.analysis.AnalysisStatus;

public abstract class DefaultRun extends AbstractAction {

    @Override
    protected boolean isEnabled(@NotNull AnActionEvent e, @NotNull Project project, @NotNull AnalysisStatus status) {
        return !status.isRunning();
    }

}