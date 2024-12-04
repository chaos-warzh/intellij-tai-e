package pascal.taie.intellij.common;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPsiElement {

    static public PsiPackage findPackage(Project project, String signature) {
        return ApplicationManager.getApplication().runReadAction(
                (com.intellij.openapi.util.Computable<PsiPackage>)
                        () -> JavaPsiFacade.getInstance(project)
                                .findPackage(signature));
    }

    static public PsiClass findClass(Project project, String signature) {
        return ApplicationManager.getApplication().runReadAction(
                (com.intellij.openapi.util.Computable<PsiClass>)
                        () -> JavaPsiFacade.getInstance(project)
                                .findClass(signature, GlobalSearchScope.allScope(project)));
    }

    static public PsiClass findClass(Project project, JClass jClass) {
        return ApplicationManager.getApplication().runReadAction(
                (com.intellij.openapi.util.Computable<PsiClass>)
                        () -> JavaPsiFacade.getInstance(project)
                                .findClass(jClass.getName(), GlobalSearchScope.allScope(project)));
    }

    static private boolean checkParamType(PsiMethod m, String[] param_types_arr) {
        PsiParameterList parameterList = m.getParameterList();
        if (param_types_arr.length != parameterList.getParametersCount()) {
            return false;
        }
        for (int i = 0; i < param_types_arr.length; i++) {
            if (!parameterList.getParameters()[i].getType().getCanonicalText().equals(param_types_arr[i])) {
                return false;
            }
        }
        return true;
    }

    static private boolean checkRetType(PsiMethod m, String returnType) {
        return Objects.requireNonNull(m.getReturnType()).getCanonicalText().equals(returnType);
    }

    static public PsiMethod findMethod(Project project,
                                       String className,
                                       String methodName,
                                       String[] param_types_arr,
                                       String returnType) {
        return ApplicationManager.getApplication().runReadAction(
                (com.intellij.openapi.util.Computable<PsiMethod>)
                        () -> {
                            PsiClass psiClass = JavaPsiFacade.getInstance(project)
                                    .findClass(className, GlobalSearchScope.allScope(project));
                            if (psiClass == null) {
                                return null;
                            }
                            if (methodName.equals("<init>")) {
                                return Arrays.stream(psiClass.getConstructors())
                                        .filter(m -> checkParamType(m, param_types_arr))
                                        .findFirst()
                                        .orElse(null);
                            } else {
                                return Arrays.stream(psiClass.findMethodsByName(methodName, true))
                                        .filter(m -> checkParamType(m, param_types_arr) && checkRetType(m, returnType))
                                        .findFirst()
                                        .orElse(null);
                            }
                        });
    }

    static public PsiMethod findMethod(Project project, JMethod jMethod) {
        String className = jMethod.getDeclaringClass().getName();
        String methodName = jMethod.getName();
        String[] param_types_arr = jMethod
                .getParamTypes()
                .stream()
                .map(Type::getName)
                .toArray(String[]::new);
        String returnType = jMethod.getReturnType().getName();
        return findMethod(project, className, methodName, param_types_arr, returnType);
    }

    static public PsiMethod findMethod(Project project, String signature) {
        // <CLASS_NAME: RET_TYPE name(PARAM_TYPES..)>
        Pattern pattern = Pattern.compile("<([^:]+): (\\S+) ([^()]+)\\(([^()]*)\\)>");
        Matcher matcher = pattern.matcher(signature);
        if (!matcher.matches()) {
            return null;
        }
        String className = matcher.group(1);
        String returnType = matcher.group(2);
        String methodName = matcher.group(3);
        String param_types = matcher.group(4);
        String[] param_types_arr;
        if (!param_types.isEmpty()) {
            param_types_arr = param_types.split(",");
        } else {
            param_types_arr = new String[]{};
        }
        return findMethod(project, className, methodName, param_types_arr, returnType);
    }
}
