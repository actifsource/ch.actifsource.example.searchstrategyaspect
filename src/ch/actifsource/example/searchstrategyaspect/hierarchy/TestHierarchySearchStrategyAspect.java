package ch.actifsource.example.searchstrategyaspect.hierarchy;

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
import ch.actifsource.ui.search.util.AsMatch;
import ch.actifsource.ui.search.util.ICustomElement;
import ch.actifsource.ui.widget.cache.IImageReference;


public class TestHierarchySearchStrategyAspect extends SimpleHierarchySearchStrategyAspect {

  public static final String SEARCH_STRATEGY_ID = "HirarchySearchTestID";
  
  @Override
  protected List<Object> getChildren(IReadJobExecutor executor, IActifsourceSearchQuery query, PackagedResource parent) {
	IStatementSet childs = Select.statementsForRelation(executor, GenericPackage.CompositionClass_composition, parent.getResource());
    List<Object> nodes = new ArrayList<Object>(childs.size());
    for (Statement child: childs) {
      nodes.add(Select.asPackagedResource(executor, child.object()));
    }
    return nodes;
  }
  
  @Override
  protected List<Object> getChildren(IReadJobExecutor executor, IActifsourceSearchQuery query, Statement parent) {
    List<Object> nodes = new ArrayList<Object>();
    return nodes;
  }
  
  @Override
  protected List<Object> getChildren(final IReadJobExecutor executor, final IActifsourceSearchQuery query, INode node) {
    return super.getChildren(executor, query, node);
  }
  
  @Override
  protected List<Object> getChildren(final IReadJobExecutor executor, final IActifsourceSearchQuery query, ch.actifsource.core.Package pkg) {
    return super.getChildren(executor, query, pkg);
  }
  
  @Override
  protected List<Object> getChildren(final IReadJobExecutor executor, final IActifsourceSearchQuery query, ICustomElement element) {
    return super.getChildren(executor, query, element);
  }
  
  /********************
   * Override Show match
   ********************/
 
  @Override
  protected boolean showMatchInsideEditor(final IReadJobExecutor executor, AsMatch match, final int currentOffset, final int currentLength, final boolean activate) {
    return super.showMatchInsideEditor(executor, match, currentOffset, currentLength, activate);
  }
  
  /********************
   * Override Display Name
   ********************/
  
  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, Statement statement, AsMatch match) {
    return super.getDisplayText(executor, mode, parent, statement, match);
  }

  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, PackagedResource packagedResource, AsMatch match) {
    return super.getDisplayText(executor, mode, parent, packagedResource, match);
  }

  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, INode node, AsMatch match) {
    return super.getDisplayText(executor, mode, parent, node, match);
  }
  
  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, ch.actifsource.core.Package pkg, AsMatch match) {
    return super.getDisplayText(executor, mode, parent, pkg, match);
  }
  
  @Override
  protected StyledString getDisplayText(IReadJobExecutor executor, Mode mode, @CheckForNull Object parent, ICustomElement element, AsMatch match) {
    return super.getDisplayText(executor, mode, parent, element, match);
  }
  
  /********************
   * Override Display Image
   ********************/
  
  @Override
  protected IImageReference getDisplayImageReference(IReadJobExecutor executor, IActifsourceSearchQuery query, Statement statement) {
    return super.getDisplayImageReference(executor, query, statement);
  }
  
  @Override
  protected IImageReference getDisplayImageReference(IReadJobExecutor executor, IActifsourceSearchQuery query, PackagedResource packagedResource) {
    return super.getDisplayImageReference(executor, query, packagedResource);
  }
  
  @Override
  protected IImageReference getDisplayImageReference(IReadJobExecutor executor, IActifsourceSearchQuery query, INode node) {
    return super.getDisplayImageReference(executor, query, node);
  }
  
  @Override
  protected IImageReference getDisplayImageReference(IReadJobExecutor executor, IActifsourceSearchQuery query, ch.actifsource.core.Package pkg) {
    return super.getDisplayImageReference(executor, query, pkg);
  }

}