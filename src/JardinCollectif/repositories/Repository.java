package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.repositories.helpers.GenericHelper;
import JardinCollectif.repositories.helpers.TableHelper;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Repository<T> extends GenericHelper<T> {
  protected final Connexion connexion;
  private final TypedQuery<T> retrieveQuery;
  private final TypedQuery<T> retrieveAllQuery;
  private final TableHelper tableHelper;

  public Repository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    this.connexion = connexion;
    tableHelper = new TableHelper(getGenericType());

    String tableName = tableHelper.getTableName();
    String primaryKeyName = tableHelper.getPrimaryKey().stream().map(idName -> "t." + idName + "= :" + idName)
            .collect(Collectors.joining(" AND ", "", ""));

    retrieveQuery = connexion.getEntityManager()
            .createQuery("SELECT t FROM " + tableName + " t WHERE " + primaryKeyName, getGenericType());
    retrieveAllQuery = connexion.getEntityManager().createQuery("SELECT t FROM " + tableName + " t", getGenericType());
  }

  public void create(T toCreate) {
    connexion.getEntityManager().persist(toCreate);
  }

  public T retrieve(Object... id) throws IFT287Exception {
    List<String> idNames = tableHelper.getPrimaryKey();
    if (idNames.size() != id.length)
      throw new IFT287Exception("Wrong number of primary key provided.");

    for (int i = 0; i < id.length; i++) {
      retrieveQuery.setParameter(idNames.get(i), id[i]);
    }

    List<T> results = retrieveQuery.getResultList();

    return results.isEmpty() ? null : results.get(0);
  }

  public List<T> retrieveAll() {
    return retrieveAllQuery.getResultList();
  }

  public void delete(T toDelete) {
    connexion.getEntityManager().remove(toDelete);
  }

  public boolean exists(Object... id) throws IFT287Exception {
    List<String> idNames = tableHelper.getPrimaryKey();
    if (idNames.size() != id.length)
      throw new IFT287Exception("Wrong number of primary key provided.");

    for (int i = 0; i < id.length; i++) {
      retrieveQuery.setParameter(idNames.get(i), id[i]);
    }

    return !retrieveQuery.getResultList().isEmpty();
  }
}
