package pascal.taie.intellij.analysis;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

import javax.annotation.concurrent.ThreadSafe;

import pascal.taie.intellij.messages.StatusListener;

@ThreadSafe
@Service(Service.Level.PROJECT)
public final class AnalysisStatus {

    private final StatusListener statusListener;

    private Status status = Status.STOPPED;

    public AnalysisStatus(Project project) {
        this.statusListener = project.getMessageBus().syncPublisher(StatusListener.TAI_E_STATUS_TOPIC);
    }

    public enum Status {RUNNING, STOPPED, CANCELLING}

    public static AnalysisStatus get(Project p) {
        AnalysisStatus status = p.getService(AnalysisStatus.class);
        if (status == null) {
            throw new IllegalStateException("AnalysisStatus not found for project " + p);
        }
        return status;
    }

    /**
     * Whether a manually-initiated task is running.
     * Used, for example, to enable/disable task-related actions (run, stop).
     */
    public synchronized boolean isRunning() {
        return status == Status.RUNNING || status == Status.CANCELLING;
    }

    public synchronized boolean isCanceled() {
        return status == Status.CANCELLING;
    }

    public void stopRun() {
        Status callback = null;
        synchronized (this) {
            if (isRunning()) {
                status = Status.STOPPED;
                callback = status;
            }
        }

        //don't lock while calling listeners
        if (callback != null) {
            statusListener.changed(callback);
        }
    }

    /**
     * Cancel the task currently running
     */
    public void cancel() {
        Status callback = null;
        synchronized (this) {
            if (status == Status.RUNNING) {
                status = Status.CANCELLING;
                callback = Status.CANCELLING;
            }
        }

        //don't lock while calling listeners
        if (callback != null) {
            statusListener.changed(callback);
        }
    }

    public boolean tryRun() {
        Status callback = null;
        synchronized (this) {
            if (!isRunning()) {
                status = Status.RUNNING;
                callback = Status.RUNNING;
            }
        }

        //don't lock while calling listeners
        if (callback != null) {
            statusListener.changed(callback);
            return true;
        } else {
            return false;
        }
    }
}
