package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class RunTaintAnalysisAction extends DefaultRun {

    @Override
    void actionPerformedImpl(@NotNull AnActionEvent e, @NotNull Project project) {
        // TODO: implement me 2024/9/19
        Messages.showWarningDialog("Coming soon!", "Not Implemented Yet!");
    }

}
