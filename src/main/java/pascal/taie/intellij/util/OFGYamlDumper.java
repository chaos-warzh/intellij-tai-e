package pascal.taie.intellij.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.analysis.graph.flowgraph.ObjectFlowGraph;
import pascal.taie.intellij.util.flowgraph.FGYamlDumper;
import pascal.taie.intellij.util.flowgraph.MetaData;
import pascal.taie.intellij.util.flowgraph.Var;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.*;
import java.util.stream.Collectors;

public class OFGYamlDumper extends FGYamlDumper {

    private static final Logger logger = LogManager.getLogger(pascal.taie.intellij.util.OFGYamlDumper.class);

    public OFGYamlDumper(ObjectFlowGraph ofg) {
        OFGMetaDataBuilder ofgMetaDataBuilder = new OFGMetaDataBuilder(ofg);
        metadata = ofgMetaDataBuilder.build();
        Set<Node> nodes = ofg.getNodes();

        buildRelationFromNodes(nodes);

        Collection<FlowEdge> edges = nodes.stream()
                .map(ofg::getOutEdgesOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for (FlowEdge edge : edges) {
            Long key = metadata.getId(edge.source().toString());
            Long value = metadata.getId(edge.target().toString());
            addEdge(key, value);
        }
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


class OFGMetaDataBuilder {
    private final ObjectFlowGraph ofg;

    public OFGMetaDataBuilder(ObjectFlowGraph ofg) {
        this.ofg = ofg;
    }

    public MetaData build() {
        MetaData metadata = new MetaData(new String[]{"package", "class", "method", "node"});

        List<String> nodes = ofg.getNodes().stream().map(Objects::toString).sorted().distinct().toList();
        nodes.forEach(n -> {
            metadata.addVar(new Var(n, false, "node", "node"));
        });

        List<JMethod> allMethods = MetaData.allMethodsFromNodes(ofg.getNodes());
        List<String> methods = allMethods.stream().map(JMethod::toString).sorted().distinct().toList();
        methods.forEach(m -> {
            metadata.addVar(new Var(m, true, "method"));
        });

        List<String> classes = MetaData.allClassesFromNodes(ofg.getNodes(), allMethods)
                .stream().map(JClass::toString).sorted().distinct().toList();
        classes.forEach(c -> {
            metadata.addVar(new Var(c, true, "class"));
        });

        List<String> packages = classes.stream().map(MetaData::packageFromClass).sorted().distinct().toList();
        packages.forEach(p -> {
            metadata.addVar(new Var(p, true, "package"));
        });
        return metadata;
    }

}
