package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.analysis.AnalysisStatus;
import pascal.taie.intellij.analysis.AnalyzeStarter;

public class StopCustomizedAction extends DefaultStop implements DumbAware {

    @Override
    void actionPerformedImpl(@NotNull AnActionEvent e, @NotNull Project project) {
        if (e.getProject() != null) {
            AnalysisStatus.get(e.getProject()).cancel();
            AnalyzeStarter.get().cancel();
        }
    }
}
