package pascal.taie.intellij.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import pascal.taie.analysis.pta.plugin.taint.TaintFlow;
import pascal.taie.intellij.common.FindPsiElement;
import pascal.taie.analysis.pta.plugin.taint.TFAnnotation;

import java.util.HashSet;
import java.util.Set;

@Service(Service.Level.PROJECT)
public final class CacheTaintFlowsService {

    public static final Logger logger = Logger.getInstance(CacheTaintFlowsService.class);

    private final Project project;

    public record Annotation(PsiElement element, TextRange range, String message, long timeStamp) { }

    Set<TaintFlow> taintFlows = null;
    Set<Annotation> annotations = null;

    public CacheTaintFlowsService(Project project) {
        this.project = project;
    }

    static TextRange getLineTextRange(Document document, PsiElement element, int lineNumber) {
        if (document == null || lineNumber < 0 || lineNumber >= document.getLineCount()) {
            return element.getTextRange();
        }
        int startOffset = document.getLineStartOffset(lineNumber);
        int endOffset = document.getLineEndOffset(lineNumber);
        if (startOffset < element.getTextRange().getStartOffset()
                || endOffset > element.getTextRange().getEndOffset()) {
            return element.getTextRange();
        }
        return new TextRange(startOffset, endOffset);
    }

    public void updateTaintFlows(Set<TaintFlow> taintFlows) {
        this.taintFlows = taintFlows;
        this.annotations = new HashSet<>();

        for (TaintFlow tf : taintFlows) {
            TFAnnotation annotation = new TFAnnotation(tf);
            PsiElement scpContainer = FindPsiElement.findMethod(project, annotation.getSourcePointContainer());
            if (scpContainer != null) {
                PsiFile file = scpContainer.getContainingFile();
                Document document = PsiDocumentManager.getInstance(project)
                        .getDocument(file);
                annotations.add(new Annotation(
                        scpContainer,
                        getLineTextRange(document, scpContainer, annotation.getSourcePointLineNumber()),
                        annotation.getSourcePointMessage(),
                        file.getModificationStamp()));
            }
            PsiElement skpContainer = FindPsiElement.findMethod(project, annotation.getSinksPointContainer());
            if (skpContainer != null) {
                PsiFile file = skpContainer.getContainingFile();
                Document document = PsiDocumentManager.getInstance(project)
                        .getDocument(skpContainer.getContainingFile());
                annotations.add(new Annotation(
                        skpContainer,
                        getLineTextRange(document, skpContainer, annotation.getSinksPointLineNumber()),
                        annotation.getSinksPointMessage(),
                        file.getModificationStamp()));
            }
        }
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

}
