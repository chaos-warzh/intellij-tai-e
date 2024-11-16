package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.*;
import pascal.taie.intellij.util.flowgraph.FGYamlDumper;
import pascal.taie.intellij.util.flowgraph.MetaData;
import pascal.taie.intellij.util.flowgraph.Var;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;

import java.util.*;
import java.util.stream.Collectors;

class TFGYamlDumper extends FGYamlDumper {
    public TFGYamlDumper(TaintFlowGraph tfg) {
        TFGElem tfgElem = new TFGElem(tfg);
        TFGMetaDataBuilder tfgMetaDataBuilder = new TFGMetaDataBuilder(tfgElem);
        metadata = tfgMetaDataBuilder.build();
        buildRelation(tfgElem);
        buildEdges(tfg, tfgElem);
    }

    private void buildRelation(TFGElem tfgElem) {
        buildRelationFromNodes(tfgElem.nodes);

        tfgElem.sourcePoints.forEach(scp -> addFromMethod(scp, scp.getContainer()));

        tfgElem.sinkPoints.forEach(skp -> addFromMethod(skp, skp.sinkCall().getContainer()));

        tfgElem.sources.forEach(sc -> {
            if (sc instanceof CallSource callSource){
                addFromMethod(callSource, callSource.method());
            } else if (sc instanceof FieldSource fieldSource){
                addFromClass(fieldSource, fieldSource.field().getDeclaringClass());
            } else if (sc instanceof ParamSource paramSource){
                addFromMethod(paramSource, paramSource.method());
            } else {
                throw new RuntimeException("Can't process new Source");
            }
        });

        tfgElem.sinks.forEach(sk -> addFromMethod(sk, sk.method()));
    }

    private void buildEdges(TaintFlowGraph tfg, TFGElem tfgElem) {
        Collection<FlowEdge> edges = tfg.getNodes().stream()
                .map(tfg::getOutEdgesOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        edges.forEach(edge -> {
            Long key = metadata.getId(edge.source().toString());
            Long value = metadata.getId(edge.target().toString());
            addEdge(key, value);
        });

        for (var entry: tfg.getSourceNode2SourcePoint().entrySet()) {
            Node sourceNode = entry.getKey();
            SourcePoint sourcePoint = entry.getValue();
            Long scp = metadata.getId(sourcePoint.toString());
            Long scn = metadata.getId(sourceNode.toString());
            addEdge(scp, scn);
        }

        for (var entry: tfg.getSinkNode2SinkPoint().entrySet()) {
            Node sinkNode = entry.getKey();
            SinkPoint sinkPoint = entry.getValue();
            Long skn = metadata.getId(sinkNode.toString());
            Long skp = metadata.getId(sinkPoint.toString());
            addEdge(skn, skp);
        }

        tfgElem.sourcePoints.forEach(sourcePoint -> {
            Long sc = metadata.getId(sourcePoint.source().toString());
            Long scp = metadata.getId(sourcePoint.toString());
            addEdge(sc, scp);
        });

        tfgElem.sinkPoints.forEach(sinkPoint -> {
            Long sk = metadata.getId(sinkPoint.sink().toString());
            Long skp = metadata.getId(sinkPoint.toString());
            addEdge(skp, sk);
        });
    }
}

class TFGElem {
    public Set<Node> nodes;
    public Set<SourcePoint> sourcePoints;
    public Set<SinkPoint> sinkPoints;
    public Set<Source> sources;
    public Set<Sink> sinks;

    public TFGElem(TaintFlowGraph tfg) {
        nodes = tfg.getNodes();
        sourcePoints = new HashSet<>(tfg.getSourceNode2SourcePoint().values());
        sinkPoints = new HashSet<>(tfg.getSinkNode2SinkPoint().values());
        sources = new HashSet<>(sourcePoints.stream().map(SourcePoint::source).toList());
        sinks = new HashSet<>(sinkPoints.stream().map(SinkPoint::sink).toList());
    }
}


class TFGMetaDataBuilder {
    private final TFGElem tfgElem;

    public TFGMetaDataBuilder(TFGElem tfgElem) {
        this.tfgElem = tfgElem;
    }

    public MetaData build() {
        MetaData metadata = new MetaData(new String[]{"package", "class", "method", "node"});

        // nodes, sourcePoints, sinkPoints, sources, sinks
        List<String> nodes = tfgElem.nodes.stream().map(Objects::toString).sorted().distinct().toList();
        nodes.forEach(n -> {
            metadata.addVar(new Var(n, false, "node", "node"));
        });
        List<String> sourcePoints = tfgElem.sourcePoints.stream().map(Objects::toString).sorted().distinct().toList();
        sourcePoints.forEach(scp -> {
            metadata.addVar(new Var(scp, false, "node", "source-point", "lightcoral"));
        });
        List<String> sinkPoints = tfgElem.sinkPoints.stream().map(Objects::toString).sorted().distinct().toList();
        sinkPoints.forEach(scp -> {
            metadata.addVar(new Var(scp, false, "node", "sink-point", "lightgreen"));
        });
        List<String> sources = tfgElem.sources.stream().map(Objects::toString).sorted().distinct().toList();
        sources.forEach(sc -> {
            metadata.addVar(new Var(sc, false, "node", "source", "gold"));
        });
        List<String> sinks = tfgElem.sinks.stream().map(Objects::toString).sorted().distinct().toList();
        sinks.forEach(sk -> {
            metadata.addVar(new Var(sk, false, "node", "sink", "aquamarine"));
        });


        List<JMethod> allMethods = allMethods();
        List<String> methods = allMethods.stream().map(JMethod::toString).sorted().distinct().toList();
        methods.forEach(m -> {
            metadata.addVar(new Var(m, true, "method"));
        });

        List<String> classes = allClass(allMethods).stream().map(JClass::toString).sorted().distinct().toList();
        classes.forEach(c -> {
            metadata.addVar(new Var(c, true, "class"));
        });

        List<String> packages = classes.stream().map(MetaData::packageFromClass).sorted().distinct().toList();
        packages.forEach(p -> {
            metadata.addVar(new Var(p, true, "package"));
        });
        return metadata;
    }

    /**
     * get all methods that contain at least one variable in nodes
     */
    private List<JMethod> allMethods() {
        List<JMethod> methodList = new ArrayList<>(MetaData.allMethodsFromNodes(tfgElem.nodes));

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
    private List<JClass> allClass(List<JMethod> methodList) {
        List<JClass> classList = new ArrayList<>(MetaData.allClassesFromNodes(tfgElem.nodes, methodList));

        tfgElem.sources.forEach(sc -> {
            if (sc instanceof FieldSource fieldSource) {
                classList.add(fieldSource.field().getDeclaringClass());
            }
        });

        return classList;
    }
}
