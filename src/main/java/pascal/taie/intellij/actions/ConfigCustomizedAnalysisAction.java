package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.gui.modal.view.TaieCustomizedConfigurable;

public class ConfigCustomizedAnalysisAction extends DefaultConfig {

    @Override
    void actionPerformedImpl(@NotNull AnActionEvent e, @NotNull Project project) {
        if (e.getProject() != null) {
            Configurable configurable = new TaieCustomizedConfigurable(project);
            ShowSettingsUtil.getInstance().editConfigurable(e.getProject(), configurable);
        }
    }
}
