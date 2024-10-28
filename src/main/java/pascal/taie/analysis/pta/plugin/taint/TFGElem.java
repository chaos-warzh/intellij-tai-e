package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.Node;

import java.util.HashSet;
import java.util.Set;

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
