package pascal.taie.intellij.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.analysis.graph.flowgraph.ArrayIndexNode;
import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.InstanceFieldNode;
import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.analysis.graph.flowgraph.ObjectFlowGraph;
import pascal.taie.analysis.graph.flowgraph.StaticFieldNode;
import pascal.taie.analysis.graph.flowgraph.VarNode;
import pascal.taie.analysis.pta.plugin.taint.MetaData;
import pascal.taie.analysis.pta.plugin.taint.Relation;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OFGYamlDumper {

    private static final Logger logger = LogManager.getLogger(pascal.taie.intellij.util.OFGYamlDumper.class);

    public final MetaData metadata;

    /**
     * represent the relations among packages, classes, methods, variables and fields
     */
    public final Relation relation;

    /**
     * represent the taint flow path
     */
    public final Map<Long, List<Long>> graph;

    public OFGYamlDumper(ObjectFlowGraph ofg) {
        Set<Node> nodes = ofg.getNodes();
        Collection<FlowEdge> edges = ofg.getNodes().stream()
                .map(ofg::getOutEdgesOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        List<JMethod> methodList = allMethods(nodes);
        List<JClass> classList = allClassFromNodes(nodes, methodList);

        metadata = new MetaData(nodes, Set.of(), Set.of(), methodList, classList);

        relation = new Relation(nodes, Set.of(), Set.of(), metadata);

        graph = Maps.newHybridMap();
        edges.forEach(edge -> {
            Long key = metadata.indexOfNode(edge.source().toString());
            Long value = metadata.indexOfNode(edge.target().toString());
            addEdge(key, value);
        });

        graph.replaceAll((k, v) -> graph.get(k).stream().sorted().toList()); // delete distinct()

    }

    /**
     * get all methods that contain at least one variable in nodes
     */
    private List<JMethod> allMethods(Set<Node> nodes) {
        List<JMethod> methodList = new ArrayList<>();
        methodList.addAll(nodes.stream().filter(VarNode.class::isInstance)
                .map(n -> ((VarNode) n).getVar().getMethod())
                .toList());
        methodList.addAll(nodes.stream().filter(ArrayIndexNode.class::isInstance)
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
    private List<JClass> allClassFromNodes(Set<Node> nodes, List<JMethod> methodList) {
        /* add class containing taint field */
        List<JClass> classList = new ArrayList<>();
        classList.addAll(nodes.stream().filter(InstanceFieldNode.class::isInstance)
                .map(n -> ((InstanceFieldNode) n).getField().getDeclaringClass())
                .toList());
        classList.addAll(nodes.stream().filter(StaticFieldNode.class::isInstance)
                .map(n -> ((StaticFieldNode) n).getField().getDeclaringClass())
                .toList());

        /* add class containing methods that contain taint */
        classList.addAll(methodList.stream()
                .map(ClassMember::getDeclaringClass)
                .toList());
        return classList;
    }

    private void addEdge(Long from, Long to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>());
        graph.get(from).add(to);
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
}
