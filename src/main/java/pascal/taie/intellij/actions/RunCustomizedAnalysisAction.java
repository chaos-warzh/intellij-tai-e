package pascal.taie.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import pascal.taie.World;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.analysis.pta.plugin.taint.TaintFlow;
import pascal.taie.intellij.IntellijTaieMain;
import pascal.taie.intellij.gui.modal.view.TaieCustomizedConfigurable;
import pascal.taie.intellij.analysis.AnalyzeStarter;
import pascal.taie.intellij.gui.toolwindow.view.TaieToolWindowPanel;
import pascal.taie.intellij.gui.webwindow.WebWindowService;
import pascal.taie.intellij.services.CacheTaintFlowsService;
import pascal.taie.intellij.util.OFGYamlDumper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
                    List<String> args = new ArrayList<>(List.of("-pp", "-acp", cp, "-m", mainClass)); // todo: NO Hardcode here!
                     List<String> jarPaths = new ArrayList<>();
                    // Enumerate through all dependencies
                    OrderEnumerator.orderEntries(project).forEachLibrary(library -> {
                        for (VirtualFile file : library.getFiles(OrderRootType.CLASSES)) {
                            String jarPath = file.getPath();
                            if (jarPath.endsWith(".jar!/")) {
                                jarPaths.add(jarPath.substring(0, jarPath.length() - 2));
                            }
                        }
                        return true;
                    });
                    args.addAll(jarPaths.stream()
                            .flatMap(jarPath -> Stream.of("-cp", jarPath))
                            .toList());
                    String projectPath = project.getBasePath();
                    if (projectPath != null) {
                        args.addAll(List.of("--output-dir", projectPath + "/output"));
                    }
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

                            String tfgYaml = pointerAnalysisResult.getResult("pascal.taie.analysis.pta.plugin.taint.TFGYamlDumper");
                            if(tfgYaml != null) {
                                project.getService(WebWindowService.class).reload("index.html", tfgYaml);
                            } else {
                                // dump OFG
                                String ofgYaml = new OFGYamlDumper(pointerAnalysisResult.getObjectFlowGraph()).dump();
                                if (ofgYaml != null) {
                                    project.getService(WebWindowService.class).reload("index.html", ofgYaml);
                                }
                            }

                            Set<TaintFlow> taintFlows = pointerAnalysisResult.getResult("pascal.taie.analysis.pta.plugin.taint.TaintAnalysis");
                            if (taintFlows != null) {
                                project.getService(CacheTaintFlowsService.class).updateTaintFlows(taintFlows);
                            }
                        }
                    }

                    World.reset();
                }
        );
    }
}
