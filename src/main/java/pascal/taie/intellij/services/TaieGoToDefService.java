package pascal.taie.intellij.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import pascal.taie.intellij.common.FindPsiElement;


@Service(Service.Level.PROJECT)
public final class TaieGoToDefService {

    public static final Logger logger = Logger.getInstance(TaieGoToDefService.class);

    private final Project project;

    public TaieGoToDefService(Project project) {
        this.project = project;
    }

    public static class GoToDefQueryData {
        public String groupType;
        public String signature;
    }

    public boolean goToDef(String signature) {
        ObjectMapper mapper = new ObjectMapper();
        GoToDefQueryData queryData;
        try {
            queryData = mapper.readValue(signature, GoToDefQueryData.class);
        } catch (Exception e) {
            logger.error("Failed to parse query", e);
            return false;
        }
        PsiElement element = switch (queryData.groupType) {
            case "package" -> FindPsiElement.findPackage(project, queryData.signature);
            case "class" -> FindPsiElement.findClass(project, queryData.signature);
            case "method" -> FindPsiElement.findMethod(project, queryData.signature);
            default -> null;
        };
        if (element == null) {
            logger.info("Definition not found");
            return false;
        } else {
            ApplicationManager.getApplication().invokeLater(() -> NavigationUtil.activateFileWithPsiElement(element));
            return true;
        }
    }
}
