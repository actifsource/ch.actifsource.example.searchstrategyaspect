package ch.actifsource.example.searchstrategyaspect.hierarchy;

import ch.actifsource.core.PackagedResource;
import ch.actifsource.core.update.IModifiable;
import ch.actifsource.ui.search.pattern.ResourcePatternData;
import ch.actifsource.ui.search.query.QueryData;
import ch.actifsource.ui.search.util.SelectSearchUtil;

public class HistorySearchMenuItemAction implements ch.actifsource.environment.modelmenu.aspect.IMenuItemActionAspect {

	@Override
	public void run(IModifiable modifiable, PackagedResource selection) {
	 
    ResourcePatternData patternData = new ResourcePatternData(selection,"Resource: ");
    QueryData queryData = new QueryData(patternData, TestHierarchySearchStrategyAspect.SEARCH_STRATEGY_ID);
    SelectSearchUtil.executeSearchQuery(queryData);
	}
	
}
