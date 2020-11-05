package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.repositories.helpers.GenericHelper;
import JardinCollectif.repositories.helpers.TableHelper;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

public abstract class Repository<T> extends GenericHelper<T> {
  private final TypedQuery<T> retrieveQuery;
  private final TypedQuery<T> retrieveAllQuery;
  private final TableHelper tableHelper;
  protected final Connexion connexion;

  public Repository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    this.connexion = connexion;
    tableHelper = new TableHelper(getGenericType());

    String tableName = tableHelper.getTableName();
    String primaryKeyName = tableHelper.getPrimaryKey().stream().map(idName -> "t." + idName + ":" + idName)
        .collect(Collectors.joining(" AND ", "", ""));

    retrieveQuery = connexion.getEntityManager()
        .createQuery("SELECT t FROM " + tableName + " t WHERE " + primaryKeyName, getGenericType());
    retrieveAllQuery = connexion.getEntityManager().createQuery("SELECT t FROM " + tableName + " t", getGenericType());
  }

  public void create(T toCreate) {
    connexion.getEntityManager().persist(toCreate);
  }

  public T retrieve(Object... id) {
    return (T) connexion.getEntityManager().find(getGenericType(), id);
  }

  public List<T> retrieveAll() {
    return retrieveAllQuery.getResultList();
  }

  public void delete(T toDelete) {
    connexion.getEntityManager().remove(toDelete);
  }

  public boolean exists(Object... id) {
    return connexion.getEntityManager().find(getGenericType(), id) == null;
  }
}
