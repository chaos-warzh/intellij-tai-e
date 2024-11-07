package pascal.taie.intellij.util.flowgraph;

import pascal.taie.analysis.graph.flowgraph.*;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.*;

public class MetaData {

    public final List<Var> vars = new ArrayList<>();
    private final Map<String, Long> idMap = new HashMap<>();

    public final Map<String, List<Long>> groups = new HashMap<>();

    public MetaData(String[] groupNames) {
        for (String groupName : groupNames) {
            groups.put(groupName, new ArrayList<>());
        }
    }

    public Long getId(String nodeName) {
        String name = nodeName.replace('\"', '\'');
        return idMap.getOrDefault(name, -1L);
    }

    public void addVar(Var var) {
        vars.add(var);
        Long idx = (long) vars.size() - 1;
        idMap.put(var.name, idx);
        groups.get(var.groupType).add(idx);
    }

    /** get package from class */
    public static String packageFromClass(String className){
        int index = className.lastIndexOf('.');
        if(index != -1) {
            return className.substring(0, className.lastIndexOf('.'));
        }
        return "ROOT";
    }

    /**
     * get all methods that contain at least one variable in nodes
     */
    public static List<JMethod> allMethodsFromNodes(Set<Node> nodes) {
        List<JMethod> methodList = new ArrayList<>();
        methodList.addAll(nodes.stream().filter(n -> (n instanceof VarNode))
                .map(n -> ((VarNode) n).getVar().getMethod())
                .toList());
        methodList.addAll(nodes.stream().filter(n -> (n instanceof ArrayIndexNode))
                .map(n -> {
                    ArrayIndexNode ain = ((ArrayIndexNode) n);
                    if (ain.getBase().getContainerMethod().isPresent()) {
                        return ain.getBase().getContainerMethod().get();
                    }
                    throw new RuntimeException("Error occurs while finding container of an ArrayIndexNode ");
                })
                .toList());
        return methodList;
    }

    /**
     * get all classes which:
     * 1.contain at least one field in nodes,
     * 2.contain methods that contain at least one variable in nodes
     */
    public static List<JClass> allClassesFromNodes(Set<Node> nodes, List<JMethod> methodList) {
        /* add class containing taint field */
        List<JClass> classList = new ArrayList<>();
        classList.addAll(nodes.stream().filter(n -> (n instanceof InstanceFieldNode))
                .map(n -> ((InstanceFieldNode) n).getField().getDeclaringClass())
                .toList());
        classList.addAll(nodes.stream().filter(n -> (n instanceof StaticFieldNode))
                .map(n -> ((StaticFieldNode) n).getField().getDeclaringClass())
                .toList());
        /* add class containing methods that contain taint */
        classList.addAll(methodList.stream()
                .map(ClassMember::getDeclaringClass)
                .toList());
        return classList;
    }
}
