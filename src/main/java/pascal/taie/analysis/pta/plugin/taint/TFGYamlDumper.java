package pascal.taie.analysis.pta.plugin.taint;

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
import pascal.taie.analysis.graph.flowgraph.StaticFieldNode;
import pascal.taie.analysis.graph.flowgraph.VarNode;
import pascal.taie.language.classes.ClassMember;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Maps;

import java.util.*;
import java.util.stream.Collectors;

class TFGYamlDumper {

    private static final Logger logger = LogManager.getLogger(TFGYamlDumper.class);

    public final MetaData metadata;

    /**
     * represent the relations among packages, classes, methods, variables and fields
     */
    public final Relation relation;

    /**
     * represent the taint flow path
     */
    public final Map<Long, List<Long>> graph;

    public TFGYamlDumper(TaintFlowGraph tfg) {
        Set<Node> nodes = tfg.getNodes();
        Collection<FlowEdge> edges = tfg.getNodes().stream()
                .map(tfg::getOutEdgesOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Set<SourcePoint> sourcePoints = new HashSet<>(tfg.getSourceNode2SourcePoint().values());

        Set<SinkPoint> sinkPoints = new HashSet<>(tfg.getSinkNode2SinkPoint().values());

        List<JMethod> methodList = allMethods(nodes, sourcePoints, sinkPoints);
        List<JClass> classList = allClassFromNodes(nodes, methodList);

        this.metadata = new MetaData(nodes, sourcePoints, sinkPoints, methodList, classList);

        this.relation = new Relation(nodes, sourcePoints, sinkPoints, metadata);

        this.graph = Maps.newHybridMap();
        edges.forEach(edge -> {
            Long key = this.metadata.indexOfNode(edge.source().toString());
            Long value = this.metadata.indexOfNode(edge.target().toString());
            addEdge(key, value);
        });

        for (var entry : tfg.getSourceNode2SourcePoint().entrySet()) {
            Node sourceNode = entry.getKey();
            SourcePoint sourcePoint = entry.getValue();
            Long key = this.metadata.indexOfNode(sourcePoint.toString());
            Long value = this.metadata.indexOfNode(sourceNode.toString());
            addEdge(key, value);
        }

        for (var entry : tfg.getSinkNode2SinkPoint().entrySet()) {
            Node sinkNode = entry.getKey();
            SinkPoint sinkPoint = entry.getValue();
            Long key = this.metadata.indexOfNode(sinkNode.toString());
            Long value = this.metadata.indexOfNode(sinkPoint.toString());
            addEdge(key, value);
        }

        this.graph.replaceAll((k, v) -> this.graph.get(k).stream().sorted().toList()); // delete distinct()

    }

    /**
     * get all methods that contain at least one variable in nodes
     */
    private List<JMethod> allMethods(Set<Node> nodes, Set<SourcePoint> sourcePoints, Set<SinkPoint> sinkPoints) {
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
        methodList.addAll(sourcePoints.stream().map(SourcePoint::getContainer).toList());
        methodList.addAll(sinkPoints.stream().map(it -> it.sinkCall().getContainer()).toList());
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

    private void addEdge(Long from, Long to) {
        if (!this.graph.containsKey(from)) {
            this.graph.put(from, new ArrayList<>());
        }
        this.graph.get(from).add(to);
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
