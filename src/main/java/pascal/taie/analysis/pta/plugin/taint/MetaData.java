package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.*;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


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

    public final List<Long> source;

    public final List<Long> sink;

    public MetaData(TFGElem tfgElem) {
        // nodes, sourcePoints, sinkPoints
        List<String> allNodes = new ArrayList<>(tfgElem.nodes.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(tfgElem.sourcePoints.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(tfgElem.sinkPoints.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(tfgElem.sources.stream().map(Objects::toString).sorted().toList());
        allNodes.addAll(tfgElem.sinks.stream().map(Objects::toString).sorted().toList());
        this.nodes = allNodes;

        List<JMethod> allMethods = allMethods(tfgElem);
        this.methods = allMethods.stream().map(ClassMember::toString).distinct().sorted().toList();

        this.classes = allClass(tfgElem, allMethods).stream().map(JClass::toString).distinct().sorted().toList();

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

        this.sourcePoints = tfgElem.sourcePoints.stream().map(Objects::toString).map(this::indexOfNode).toList();

        this.sinkPoints = tfgElem.sinkPoints.stream().map(Objects::toString).map(this::indexOfNode).toList();

        this.source = tfgElem.sources.stream().map(Objects::toString).map(this::indexOfNode).toList();

        this.sink = tfgElem.sinks.stream().map(Objects::toString).map(this::indexOfNode).toList();
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

    /**
     * get all methods that contain at least one variable in nodes
     */
    private List<JMethod> allMethods(TFGElem tfgElem) {
        List<JMethod> methodList = new ArrayList<>();
        methodList.addAll(tfgElem.nodes.stream().filter(n -> (n instanceof VarNode))
                .map(n -> ((VarNode) n).getVar().getMethod())
                .toList());
        methodList.addAll(tfgElem.nodes.stream().filter(n -> (n instanceof ArrayIndexNode))
                .map(n -> {
                    ArrayIndexNode ain = ((ArrayIndexNode) n);
                    if (ain.getBase().getContainerMethod().isPresent()) {
                        return ain.getBase().getContainerMethod().get();
                    }
                    throw new RuntimeException("Error occurs while finding container of an ArrayIndexNode ");
                })
                .toList());
        methodList.addAll(tfgElem.sourcePoints.stream().map(SourcePoint::getContainer).toList());
        methodList.addAll(tfgElem.sinkPoints.stream().map(it -> it.sinkCall().getContainer()).toList());

        tfgElem.sources.forEach(sc -> {
            if (sc instanceof CallSource callSource) {
                methodList.add(callSource.method());
            } else if (sc instanceof ParamSource paramSource) {
                methodList.add(paramSource.method());
            }
        });

        methodList.addAll(tfgElem.sinks.stream().map(Sink::method).toList());

        return methodList;
    }

    /**
     * get all classes which:
     * 1.contain at least one field in nodes,
     * 2.contain methods that contain at least one variable in nodes
     */
    private List<JClass> allClass(TFGElem tfgElem, List<JMethod> methodList) {
        /* add class containing taint field */
        List<JClass> classList = new ArrayList<>();
        classList.addAll(tfgElem.nodes.stream().filter(n -> (n instanceof InstanceFieldNode))
                .map(n -> ((InstanceFieldNode) n).getField().getDeclaringClass())
                .toList());
        classList.addAll(tfgElem.nodes.stream().filter(n -> (n instanceof StaticFieldNode))
                .map(n -> ((StaticFieldNode) n).getField().getDeclaringClass())
                .toList());

        tfgElem.sources.forEach(sc -> {
            if (sc instanceof FieldSource fieldSource) {
                classList.add(fieldSource.field().getDeclaringClass());
            }
        });

        /* add class containing methods that contain taint */
        classList.addAll(methodList.stream()
                .map(ClassMember::getDeclaringClass)
                .toList());
        return classList;
    }

}
