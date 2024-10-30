package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;


/**
 * Map every node to a number,
 * The representation of node is String,
 * and what it maps to is its index in List.
 */
public class MetaData {
    public final List<String> packages;

    public final List<String> classes;

    public final List<String> methods;

    public final List<String> nodes;

    public final List<Long> sourcePoints;

    public final List<Long> sinkPoints;

    public MetaData(Set<Node> nodes, Set<SourcePoint> sourcePoints, Set<SinkPoint> sinkPoints, List<JMethod> methodList, List<JClass> classList){
        // nodes, sourcePoints, sinkPoints
        List<String> allNodes = new ArrayList<>(nodes.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(sourcePoints.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(sinkPoints.stream().map(Objects::toString).sorted().toList());
        this.nodes = allNodes;

        this.methods = methodList.stream().map(ClassMember::toString).distinct().sorted().toList();

        this.classes = classList.stream().map(JClass::toString).distinct().sorted().toList();

        this.packages = this.classes.stream()
                .map(s -> {
                    int index = s.lastIndexOf('.');
                    if(index != -1) {
                        return s.substring(0, s.lastIndexOf('.'));
                    }
                    else{
                        return s;
                    }
                })
                .distinct()
                .sorted()
                .toList();

        this.sourcePoints = sourcePoints.stream().map(Objects::toString).map(this::indexOfNode).toList();

        this.sinkPoints = sinkPoints.stream().map(Objects::toString).map(this::indexOfNode).toList();
    }

    public Long indexOfClass(String c){
        return (long) this.classes.indexOf(c);
    }

    public Long indexOfMethod(String m){
        return (long) this.methods.indexOf(m);
    }

    public Long indexOfNode(String vf){
        return (long) this.nodes.indexOf(vf);
    }

    /** get package from class */
    public Long packageFromClass(String className){
        int index = className.lastIndexOf('.');
        if(index == -1) {
            return (long) this.packages.indexOf(className);
        }
        return (long) this.packages.indexOf(className.substring(0, index));
    }

}
