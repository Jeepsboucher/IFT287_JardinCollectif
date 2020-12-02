package JardinCollectif.repositories.helpers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericHelper<T> extends GenericWrapper<T> {
  private final Class genericType;

  public GenericHelper() throws ClassNotFoundException {
    Type superClass = getClass().getGenericSuperclass();
    genericType = Class.forName(((ParameterizedType) superClass).getActualTypeArguments()[0].getTypeName());
  }

  protected Class getGenericType() {
    return genericType;
  }
}
