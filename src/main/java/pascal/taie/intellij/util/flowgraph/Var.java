package pascal.taie.intellij.util.flowgraph;

public class Var {
    public String name;
    public boolean isGroup;
    public String groupType;
    public String nodeType;
    public String color;

    public Var(String name, boolean isGroup, String groupType, String nodeType, String color) {
        // FIXME: replace " with ' to avoid yaml parse error
        this.name = name.replace('\"', '\'');
        this.isGroup = isGroup;
        this.groupType = groupType;
        this.nodeType = nodeType;
        this.color = color;
    }

    public Var(String name, boolean isGroup, String groupType, String nodeType) {
        this(name, isGroup, groupType, nodeType, "");
    }

    public Var(String name, boolean isGroup, String groupType) {
        this(name, isGroup, groupType, "", "");
    }

}
