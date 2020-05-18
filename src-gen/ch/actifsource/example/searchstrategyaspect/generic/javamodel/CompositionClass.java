package ch.actifsource.example.searchstrategyaspect.generic.javamodel;

import ch.actifsource.util.collection.IMultiMapOrdered;
import ch.actifsource.core.dynamic.*;

@edu.umd.cs.findbugs.annotations.SuppressWarnings("EQ_DOESNT_OVERRIDE_EQUALS")
public class CompositionClass extends DynamicResource implements ICompositionClass {

  public static final ch.actifsource.core.dynamic.IDynamicResource.IFactory<ICompositionClass> FACTORY = new ch.actifsource.core.dynamic.IDynamicResource.IFactory<ICompositionClass>() {
    
    @Override
    public ICompositionClass create() {
      return new CompositionClass();
    }
    
    @Override
    public ICompositionClass create(IDynamicResourceRepository resourceRepository, ch.actifsource.core.Resource resource) {
      return new CompositionClass(resourceRepository, resource);
    }
  
  };

  public CompositionClass() {
    super(ICompositionClass.TYPE_ID);
  }
  
  public CompositionClass(IDynamicResourceRepository resourceRepository, ch.actifsource.core.Resource resource) {
    super(resourceRepository, resource, ICompositionClass.TYPE_ID);
  }

  // attributes
  
  @Override
  public java.lang.String selectName() {
    return _getSingleAttribute(java.lang.String.class, ch.actifsource.core.CorePackage.NamedResource_name);
  }
    
  public void setName(java.lang.String name) {
     _setSingleAttribute(ch.actifsource.core.CorePackage.NamedResource_name, name);
  }

  // relations
  
  @Override
  public java.util.List<? extends ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass> selectComposition() {
    return _getList(ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass.class, ch.actifsource.example.searchstrategyaspect.generic.GenericPackage.CompositionClass_composition);
  }

  public CompositionClass setComposition(java.util.List<? extends ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass> composition) {
    _setList(ch.actifsource.example.searchstrategyaspect.generic.GenericPackage.CompositionClass_composition, composition);
    return this;
  }
    
  @Override
  public ch.actifsource.core.javamodel.IClass selectTypeOf() {
    return _getSingle(ch.actifsource.core.javamodel.IClass.class, ch.actifsource.core.CorePackage.Resource_typeOf);
  }

  public CompositionClass setTypeOf(ch.actifsource.core.javamodel.IClass typeOf) {
    _setSingle(ch.actifsource.core.CorePackage.Resource_typeOf, typeOf);
    return this;
  }
    
  // accept property value visitor
  @Override
  public void accept(IPropertyValueVisitor visitor) {
    // attributes
    _acceptSingleAttribute(java.lang.String.class, ch.actifsource.core.CorePackage.NamedResource_name, visitor);
    // relations
    _acceptList(ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass.class, ch.actifsource.example.searchstrategyaspect.generic.GenericPackage.CompositionClass_composition, visitor);
    _acceptSingle(ch.actifsource.core.javamodel.IClass.class, ch.actifsource.core.CorePackage.Resource_typeOf, visitor);
  }

  // toMeRelations
  
  public static ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass selectToMeComposition(ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass object) {
    return _getToMeSingle(object.getRepository(), ch.actifsource.example.searchstrategyaspect.generic.javamodel.ICompositionClass.class, ch.actifsource.example.searchstrategyaspect.generic.GenericPackage.CompositionClass_composition, object.getResource());
  }
  
}
/* Actifsource ID=[4d723cb5-db37-11de-82b8-17be2e034a3b,84759c2c-98ee-11ea-8ef1-cb1cd2be9da3,129RHptSKhOKlwKalUqSJE99K/M=] */
