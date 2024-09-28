package pascal.taie.intellij.messages;

import com.intellij.util.messages.Topic;
import pascal.taie.intellij.analysis.AnalysisStatus;

@FunctionalInterface
public interface StatusListener {

    Topic<StatusListener> TAI_E_STATUS_TOPIC = Topic.create("Tai-e Analyzer Status", StatusListener.class);

    /**
     * Called when the status of the user-initiated analysis changes.
     */
    void changed(AnalysisStatus.Status newStatus);
}
