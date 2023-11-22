package ch.actifsource.example.searchstrategyaspect.reference;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.StyledString;
import ch.actifsource.core.INode;
import ch.actifsource.core.Statement;
import ch.actifsource.core.job.IReadJobExecutor;
import ch.actifsource.core.job.Select;
import ch.actifsource.core.set.IStatementSet;
import ch.actifsource.example.searchstrategyaspect.generic.GenericPackage;
import ch.actifsource.ui.search.label.ElementLabelProvider.Mode;
import ch.actifsource.ui.search.query.IActifsourceSearchQuery;
import ch.actifsource.ui.search.query.strategy.ISearchQueryStrategy;
import ch.actifsource.ui.search.query.strategy.aspect.DefaultSearchStrategyAspect;
import ch.actifsource.ui.search.util.AsMatch;
import ch.actifsource.ui.search.util.ICustomElement;
import ch.actifsource.ui.search.util.SelectSearchUtil;
import ch.actifsource.ui.search.util.AsMatch.IMatchHandler;

public class TestExtendReferenceSearchStrategyAspect extends DefaultSearchStrategyAspect {

  public static class TestCustomElement implements ICustomElement {

    private final INode fResource;
    
    public TestCustomElement(INode resource) {
      fResource = resource;
    }

    @Override
    public Object getElementID() {
      return this;
    }

    @Override
    public INode getElementNode() {
      return fResource;
    }
    
    public String toString() {
      return fResource.toString();
    }
    
    @Override
    public int hashCode() {
      return fResource.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      TestCustomElement other = (TestCustomElement)obj;
      if (!fResource.equals(other.fResource)) return false;
      return true;
    }
  }
  
  @Override
  public ISearchQueryStrategy createSearchQueryStrategy(IReadJobExecutor executor, IActifsourceSearchQuery query) {
    return new DefaultAspectQuery() {
      
      @Override
      public IStatus executeQuery(IReadJobExecutor executor, IProgressMonitor monitor, IActifsourceSearchQuery query, String taskName) {
        INode resource = SelectSearchUtil.getResourceFromQueryData(query);
        if (resource == null)  return Status.CANCEL_STATUS;
           
        List<AsMatch> matchResults = new ArrayList<AsMatch>();
        IStatementSet childs = Select.statementsForRelation(executor, GenericPackage.CompositionClass_composition, resource);
        for (final Statement child: childs) {
          AsMatch match = new AsMatch(new TestCustomElement(child.object()), new IMatchHandler() {
            
            @Override
            public boolean showMatch(final IReadJobExecutor executor, AsMatch match, final int currentOffset, final int currentLength, final boolean activate) {
              return SelectSearchUtil.showMatchInsideEditor(executor, match, activate);
            }
            
            @Override
            public StyledString getMatchText(final IReadJobExecutor executor, final AsMatch match, final Mode mode) {
              StyledString result = new StyledString();
              result.append("Child ");
              result.append(Select.simpleName(executor, child.object()));
              result.append(" has ref to parent ");
              result.append(Select.simpleName(executor, resource));
              return result; 
            }
            
          });    
            
          // Set the parent from the match is used to if 'Show as Tree' is active
          match.setAssignmentGroup(Select.asPackagedResource(executor, resource));
          matchResults.add(match);
        }
        
        query.getSearchResult().addMatches(matchResults);
        return Status.OK_STATUS;
      }
    };
  }
  

}