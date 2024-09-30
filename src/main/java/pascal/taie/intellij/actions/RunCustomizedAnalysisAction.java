package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import pascal.taie.World;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.intellij.IntellijTaieMain;
import pascal.taie.intellij.gui.modal.view.TaieCustomizedConfigurable;
import pascal.taie.intellij.analysis.AnalyzeStarter;
import pascal.taie.intellij.gui.toolwindow.view.TaieToolWindowPanel;

import java.util.ArrayList;
import java.util.List;

public final class RunCustomizedAnalysisAction extends DefaultRun {

    @Override
    void actionPerformedImpl(@NotNull AnActionEvent e, @NotNull Project project) {
        String options = TaieCustomizedConfigurable.getOptions();

        if (options == null) {
            Messages.showWarningDialog("Please set the analysis options!", "Warning");
            return;
        }

        AnalyzeStarter.get().start(
                project, "Running Tai-e analysis for project '" + project.getName() + "'...",
                (String cp, String mainClass) -> {
                    List<String> args = new ArrayList<>(List.of("-pp", "-cp", cp, "-m", mainClass)); // todo: NO Hardcode here!
                    args.addAll(List.of(options.split(" ")));
                    IntellijTaieMain.main(args.toArray(new String[0]));
                },
                () -> {
                    if (World.get() != null && World.get().hasResult("pta")) {
                        Object ptaResult = World.get().getResult("pta");
                        if (ptaResult instanceof PointerAnalysisResult pointerAnalysisResult) {
                            int objNum = pointerAnalysisResult
                                    .getObjects().size();
                            TaieToolWindowPanel toolWindowPanel = TaieToolWindowPanel.getInstance();
                            if (toolWindowPanel != null) {
                                toolWindowPanel.showAnalysisResult(
                                        "Tai-e Analysis Finished!\nDetected " + objNum + " objects!"
                                );
                            }
                        }
                    }

                    World.reset();
                }
        );
    }
}
