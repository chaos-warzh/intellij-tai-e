package pascal.taie.analysis.pta.plugin.taint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.Node;
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
        TFGElem tfgElem = new TFGElem(tfg);

        Collection<FlowEdge> edges = tfg.getNodes().stream()
                .map(tfg::getOutEdgesOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());


        this.metadata = new MetaData(tfgElem);

        this.relation = new Relation(tfgElem, metadata);

        this.graph = Maps.newHybridMap();
        edges.forEach(edge -> {
            Long key = this.metadata.indexOfNode(edge.source().toString());
            Long value = this.metadata.indexOfNode(edge.target().toString());
            addEdge(key, value);
        });

        for (var entry: tfg.getSourceNode2SourcePoint().entrySet()) {
            Node sourceNode = entry.getKey();
            SourcePoint sourcePoint = entry.getValue();
            Long scp = this.metadata.indexOfNode(sourcePoint.toString());
            Long scn = this.metadata.indexOfNode(sourceNode.toString());
            addEdge(scp, scn);
        }

        for (var entry: tfg.getSinkNode2SinkPoint().entrySet()) {
            Node sinkNode = entry.getKey();
            SinkPoint sinkPoint = entry.getValue();
            Long skn = this.metadata.indexOfNode(sinkNode.toString());
            Long skp = this.metadata.indexOfNode(sinkPoint.toString());
            addEdge(skn, skp);
        }

        tfgElem.sourcePoints.forEach(sourcePoint -> {
            Long sc = this.metadata.indexOfNode(sourcePoint.source().toString());
            Long scp = this.metadata.indexOfNode(sourcePoint.toString());
            addEdge(sc, scp);
        });

        tfgElem.sinkPoints.forEach(sinkPoint -> {
            Long sk = this.metadata.indexOfNode(sinkPoint.sink().toString());
            Long skp = this.metadata.indexOfNode(sinkPoint.toString());
            addEdge(skp, sk);
        });

        this.graph.replaceAll((k, v) -> this.graph.get(k).stream().sorted().distinct().toList());

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
            // replace " with ' to avoid yaml parse error
            this.metadata.nodes.replaceAll(node -> node.replace("\"", "'"));
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }
}
