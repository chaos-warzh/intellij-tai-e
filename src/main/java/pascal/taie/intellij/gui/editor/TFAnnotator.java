package pascal.taie.intellij.gui.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.services.CacheTaintFlowsService;

import java.util.Set;

public class TFAnnotator implements Annotator {

    public static final Logger logger = Logger.getInstance(TFAnnotator.class);

    TFAnnotator() {}

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        Project project = element.getProject();
        CacheTaintFlowsService cacheService = project.getService(CacheTaintFlowsService.class);
        Set<CacheTaintFlowsService.Annotation> annotations = cacheService.getAnnotations();
        if (annotations == null) {
            return;
        }
        for (CacheTaintFlowsService.Annotation annotation : annotations) {
            if (annotation.element().equals(element)) {
                TextRange range = element.getTextRange();
                PsiFile file = element.getContainingFile();
                if (file.getModificationStamp() <= annotation.timeStamp()) {
                    range = annotation.range();
                }
                holder.newAnnotation(HighlightSeverity.WARNING, annotation.message())
                        .range(range)
                        .create();
            }
        }
    }
}
