package ch.actifsource.example.searchstrategyaspect.simple;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import ch.actifsource.core.INode;
import ch.actifsource.core.Statement;
import ch.actifsource.core.job.IReadJobExecutor;
import ch.actifsource.core.job.Select;
import ch.actifsource.core.set.IStatementSet;
import ch.actifsource.example.searchstrategyaspect.generic.GenericPackage;
import ch.actifsource.ui.search.query.IActifsourceSearchQuery;
import ch.actifsource.ui.search.query.strategy.ISearchQueryStrategy;
import ch.actifsource.ui.search.query.strategy.aspect.DefaultSearchStrategyAspect;
import ch.actifsource.ui.search.util.AsMatch;
import ch.actifsource.ui.search.util.SelectSearchUtil;


public class SimpleSearchStrategyAspect extends DefaultSearchStrategyAspect {
  
  public static final String SEARCH_STRATEGY_ID = "SimpleSearchTestID";
  
  @Override
  public ISearchQueryStrategy createSearchQueryStrategy(IReadJobExecutor executor, IActifsourceSearchQuery query) {
    return new DefaultAspectQuery() {
      
      @Override
      public IStatus executeQuery(IReadJobExecutor executor, IProgressMonitor monitor, IActifsourceSearchQuery query, String taskName) {
        INode resource = SelectSearchUtil.getResourceFromQueryData(query);
        if (resource == null)  return Status.CANCEL_STATUS;
               
        List<AsMatch> matchResults = new ArrayList<AsMatch>();
        IStatementSet childs = Select.statementsForRelation(executor, GenericPackage.CompositionClass_composition, resource);
        for (Statement child: childs) {
        	AsMatch match = new AsMatch(child);    
          match.setAssignmentGroup(SelectSearchUtil.getFirstNamedResourceInsideStatementPath(executor, child.getPackage(), child.subject()));
          matchResults.add(match);
        }
        
        query.getSearchResult().addMatches(matchResults);
        return Status.OK_STATUS;
      }
    };
  }
}
