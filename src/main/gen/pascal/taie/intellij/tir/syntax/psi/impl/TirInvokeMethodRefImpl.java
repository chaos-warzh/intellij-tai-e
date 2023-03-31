// This is a generated file. Not intended for manual editing.
package pascal.taie.intellij.tir.syntax.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.tir.syntax.psi.*;

import java.util.List;

public class TirInvokeMethodRefImpl extends ASTWrapperPsiElement implements TirInvokeMethodRef {

  public TirInvokeMethodRefImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TirVisitor visitor) {
    visitor.visitInvokeMethodRef(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TirVisitor) accept((TirVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public TirIdentifierClass getIdentifierClass() {
    return findNotNullChildByClass(TirIdentifierClass.class);
  }

  @Override
  @NotNull
  public TirIdentifierMethod getIdentifierMethod() {
    return findNotNullChildByClass(TirIdentifierMethod.class);
  }

  @Override
  @NotNull
  public List<TirIdentifierType> getIdentifierTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TirIdentifierType.class);
  }

}
