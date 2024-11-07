package pascal.taie.intellij.util.flowgraph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.analysis.graph.flowgraph.*;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class FGYamlDumper {
    private static final Logger logger = LogManager.getLogger(FGYamlDumper.class);

    public MetaData metadata;

    public Map<Long, Long> parent = new HashMap<>();
    public Map<Long, Set<Long>> children = new HashMap<>();

    public Map<Long, Set<Long>> graph = new HashMap<>();

    public void addRelation(Long key, Long value) {
        parent.put(value, key);
        children.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    public void addEdge(Long key, Long value) {
        graph.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    public String dump(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .disable(YAMLGenerator.Feature.SPLIT_LINES)
                .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR));
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }

    protected void addFromMethod(Object node, JMethod method) {
        Long nodeId = metadata.getId(node.toString());
        Long methodId = metadata.getId(method.toString());
        String className = method.getDeclaringClass().toString();
        Long classId = metadata.getId(className);
        String packageName = MetaData.packageFromClass(className);
        Long packageId = metadata.getId(packageName);
        addRelation(packageId, classId);
        addRelation(classId, methodId);
        addRelation(methodId, nodeId);
    }

    protected void addFromClass(Object node, JClass clazz) {
        Long nodeId = metadata.getId(node.toString());
        String className = clazz.toString();
        Long classId = metadata.getId(className);
        String packageName = MetaData.packageFromClass(className);
        Long packageId = metadata.getId(packageName);
        addRelation(packageId, classId);
        addRelation(classId, nodeId);
    }

    protected void buildRelationFromNodes(Iterable<Node> nodes) {
        for (Node n : nodes) {
            if(n instanceof VarNode vn){
                addFromMethod(vn, vn.getVar().getMethod());
            }
            else if(n instanceof InstanceFieldNode ifn){
                addFromClass(ifn, ifn.getField().getDeclaringClass());
            }
            else if(n instanceof StaticFieldNode sfn){
                addFromClass(sfn, sfn.getField().getDeclaringClass());
            }
            else if(n instanceof ArrayIndexNode ain){
                if(ain.getBase().getContainerMethod().isPresent()) {
                    addFromMethod(ain, ain.getBase().getContainerMethod().get());
                }
                else{
                    throw new RuntimeException("ArrayIndexNode not contained by a method");
                }
            }
            else{
                throw new RuntimeException("Can't process new Node");
            }
        }
    }
}
