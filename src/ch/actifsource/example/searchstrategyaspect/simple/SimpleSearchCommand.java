package ch.actifsource.example.searchstrategyaspect.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.CheckForNull;
import org.eclipse.core.commands.*;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import ch.actifsource.core.PackagedResource;
import ch.actifsource.environment.util.EnvironmentUtil;
import ch.actifsource.ui.search.pattern.ResourcePatternData;
import ch.actifsource.ui.search.query.QueryData;
import ch.actifsource.ui.search.util.SelectSearchUtil;
import ch.actifsource.util.ObjectUtil;
import ch.actifsource.util.collection.CollectionUtil;

public class SimpleSearchCommand extends AbstractHandler {

	public static final String SEARCH_STRATEGY_ID = "SimpleSearchTestID";

	public static java.util.List<?> getSelection(@CheckForNull ISelection currentSelection) {
		IStructuredSelection structuredSelection = ObjectUtil.castOrNull(currentSelection, IStructuredSelection.class);
		if (structuredSelection == null) return Collections.emptyList();
		return structuredSelection.toList();
	}
	 
	public static <T> T getSingleSelectionOrNull(@CheckForNull ISelection selection, Class<T> clazz) {
		if (selection == null) return null;
		Collection<?> collection = getSelection(selection);
		if (collection.size() != 1)return null;
		return EnvironmentUtil.adaptToObjectOrNull(CollectionUtil.first(collection), clazz);
	}

	public static <T> T getSingleSelectionOrNull(ExecutionEvent event, Class<T> clazz) {
		return getSingleSelectionOrNull(HandlerUtil.getCurrentSelection(event),clazz);
	}

	@Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
	  PackagedResource resource = getSingleSelectionOrNull(event, PackagedResource.class);
	  if (resource == null) return null;
	  
	  ResourcePatternData patternData = new ResourcePatternData(resource,"Resource: ");
	  QueryData queryData = new QueryData(patternData, SEARCH_STRATEGY_ID, null, null, null, new ArrayList<IProject>(), HandlerUtil.getCurrentSelection(event));
	  SelectSearchUtil.executeSearchQuery(queryData);
	  return null;
  }
}
