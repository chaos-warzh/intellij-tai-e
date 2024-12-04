package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JMethod;

public class TFAnnotation {

    JMethod sourcePointContainer, sinksPointContainer;

    String sourcePointMessage, sinksPointMessage;

    int sourcePointLineNumber, sinksPointLineNumber;

    public TFAnnotation(TaintFlow taintFlow) {
        SourcePoint sourcePoint = taintFlow.sourcePoint();
        SinkPoint sinkPoint = taintFlow.sinkPoint();
        sourcePointContainer = sourcePoint.getContainer();
        sourcePointMessage = String.format(
                "Detect %s. This is the source point.",
                taintFlow);
        sourcePointLineNumber = -1;
        if (sourcePoint instanceof FieldSourcePoint fsp) {
            sourcePointLineNumber = fsp.loadField().getLineNumber() - 1;
        } else if (sourcePoint instanceof CallSourcePoint csp) {
            sourcePointLineNumber = csp.sourceCall().getLineNumber() - 1;
        }

        sinksPointContainer = sinkPoint.sinkCall().getContainer();
        sinksPointMessage = String.format(
                "Detect %s. This is the sink point.",
                taintFlow);
        sinksPointLineNumber = sinkPoint.sinkCall().getLineNumber() - 1;
    }


    public JMethod getSourcePointContainer() {
        return sourcePointContainer;
    }

    public String getSourcePointMessage() {
        return sourcePointMessage;
    }

    public int getSourcePointLineNumber() {
        return sourcePointLineNumber;
    }

    public JMethod getSinksPointContainer() {
        return sinksPointContainer;
    }

    public String getSinksPointMessage() {
        return sinksPointMessage;
    }

    public int getSinksPointLineNumber() {
        return sinksPointLineNumber;
    }
}
