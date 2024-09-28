package pascal.taie.intellij.gui

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.project.Project

class UiUtils {

    companion object {
        @JvmStatic
        fun runOnUiThread(project: Project, runnable: Runnable) {
            runOnUiThread(project, ModalityState.defaultModalityState(), runnable)
        }

        @JvmStatic
        fun runOnUiThread(project: Project, modality: ModalityState, runnable: Runnable) {
            ApplicationManager.getApplication().invokeLater({
                if (!project.isDisposed) {
                    runnable.run()
                }
            }, modality)
        }

    }
}