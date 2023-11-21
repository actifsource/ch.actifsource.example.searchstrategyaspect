package ch.actifsource.example.searchstrategyaspect.custom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.CheckForNull;
import org.eclipse.jface.viewers.StyledString;
import ch.actifsource.core.INode;
import ch.actifsource.core.PackagedResource;
import ch.actifsource.core.Statement;
import ch.actifsource.core.job.IReadJobExecutor;
import ch.actifsource.core.job.Select;
import ch.actifsource.core.set.IStatementSet;
import ch.actifsource.example.searchstrategyaspect.generic.GenericPackage;
import ch.actifsource.ui.search.label.ElementLabelProvider.Mode;
import ch.actifsource.ui.search.query.IActifsourceSearchQuery;
import ch.actifsource.ui.search.query.strategy.aspect.SimpleHierarchySearchStrategyAspect;
import ch.actifsource.ui.search.styler.Stylers;
import ch.actifsource.ui.search.util.AsMatch;
import ch.actifsource.ui.search.util.ICustomElement;
import ch.actifsource.ui.widget.cache.IImageReference;
import ch.actifsource.util.ObjectUtil;


public class TestCustomSearchStrategyAspect extends SimpleHierarchySearchStrategyAspect {

  public static final String SEARCH_STRATEGY_ID = "CustromElementSearchTestID";
  
  public class TestCustomElement implements ICustomElement {

    private final PackagedResource fRoot;
    
    private final PackagedResource fResource;
    
    public TestCustomElement(PackagedResource root, PackagedResource resource) {
      fRoot = root;
      fResource = resource;
    }

    public PackagedResource getRoot() {
      return fRoot;
    }
    
    public PackagedResource getResource() {
      return fResource;
    }
    
    @Override
    public Object getElementID() {
      return this;
    }

    @Override
    public INode getElementNode() {
      return fResource.getResource();
    }
    
    public String toString() {
      return fRoot.toString()+"->"+fResource.toString();
    }
    
    @Override
    public int hashCode() {
      return fRoot.hashCode() + fResource.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      TestCustomElement other = (TestCustomElement)obj;
      if (!fRoot.equals(other.fRoot)) return false;
      if (!fResource.equals(other.fResource)) return false;
      return true;
    }
  }
  
  @Override
  protected List<Object> getChildren(IReadJobExecutor executor, IActifsourceSearchQuery query, PackagedResource parent) {
    IStatementSet childs = Select.statementsForRelation(executor, GenericPackage.CompositionClass_composition, parent.getResource());
    List<Object> customElements = new ArrayList<Object>(childs.size());
    for (Statement child: childs) {
      customElements.add( new TestCustomElement(parent, Select.asPackagedResource(executor, child.object())));
    }
    return customElements;
  }
  
  @Override
  protected List<Object> getChildren(final IReadJobExecutor executor, final IActifsourceSearchQuery query, ICustomElement element) {
    TestCustomElement customElement = ObjectUtil.cast(element, TestCustomElement.class);
    IStatementSet childs = Select.statementsForRelation(executor, GenericPackage.CompositionClass_composition, customElement.getResource().getResource());
    List<Object> customElements = new ArrayList<Object>(childs.size());
    for (Statement child: childs) {
      customElements.add( new TestCustomElement(customElement.getRoot(), Select.asPackagedResource(executor, child.object())));
    }
    return customElements;
  }
  
  /********************
   * Override Display Name
   ********************/
  
  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, ICustomElement element, AsMatch match) {
    // Default use the name from the toString function inside the ICustomElement.
    // return getCustomElementText(element);
    TestCustomElement customElement = ObjectUtil.cast(element, TestCustomElement.class);
    StyledString result = new StyledString();
    result.append(Select.simpleName(executor, customElement.getRoot().getResource()), Stylers.getAdditionalInfoStyler());
    result.append("->");
    result.append(Select.simpleName(executor, customElement.getResource().getResource()));
    return result;
  }
  
  /********************
   * Override Display Image
   ********************/
  
  @Override
  protected IImageReference getDisplayImageReference(IReadJobExecutor executor, IActifsourceSearchQuery query, ICustomElement customElement) {
    return null;
  }

}