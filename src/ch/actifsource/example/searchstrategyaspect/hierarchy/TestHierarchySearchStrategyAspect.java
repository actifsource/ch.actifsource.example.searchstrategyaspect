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
import ch.actifsource.ui.search.query.strategy.aspect.HierarchySearchStrategyAspect;
import ch.actifsource.ui.search.util.AsMatch;
import ch.actifsource.ui.search.util.AsMatch.IMatchHandler;
import ch.actifsource.ui.search.util.SelectSearchUtil.IMatchElementVisitor;
import ch.actifsource.ui.search.util.SelectSearchUtil;


public class TestHierarchySearchStrategyAspect extends HierarchySearchStrategyAspect {

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
  
  /**
   * Returns the child element match 
   */
  @CheckForNull
  protected AsMatch createChildMatch(@CheckForNull Object parentElement, final Object childElement) {
	  return new AsMatch(childElement, new IMatchHandler() {
		
		@Override
		public StyledString getMatchText(final IReadJobExecutor executor, final AsMatch match, final Mode mode) {
		  return SelectSearchUtil.visitElement(executor, childElement, new IMatchElementVisitor<StyledString>() {
			
		      @Override
		      public StyledString visit(Statement statement) {
		    	  return new StyledString(Select.simpleName(executor, statement.object()));
		      }
		
		      @Override
		      public StyledString visit(PackagedResource packagedResource) {
		    	  return new StyledString(Select.simpleName(executor, packagedResource.getResource()));
		      }
		
		      @Override
		      public StyledString visit(INode node) {
		        return getNodeResourceText(executor, node);
		      }
		      
		      @Override
		      public StyledString visit(ch.actifsource.core.Package pkg) {
		        return getPackageText(pkg);
		      }
		    }, null);
		  
		}
		
		@Override
		public boolean showMatch(final IReadJobExecutor executor, AsMatch match, final int currentOffset, final int currentLength, final boolean activate) {
			return SelectSearchUtil.showMatchInsideEditor(executor, match, activate);
		}
	});
  }
  

}