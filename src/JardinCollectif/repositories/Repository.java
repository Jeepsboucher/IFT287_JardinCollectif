package JardinCollectif.repositories;

import Boolean;
import JardinCollectif.Connexion;

public abstract class Repository {
  /* {src_lang=Java}*/


  private final PreparedStatement createStatement;

  private PreparedStatement retrieveStatement;

  private PreparedStatement updateStatement;

  private PreparedStatement deleteStatement;

  private PreparedStatement existsStatement;

  protected Repository(Connexion connexion) {
  return null;
  }

  public void Create(T toCreate) {
  }

  public T Retrieve(Object id) {
  return null;
  }

  public void Update(T toUpdate) {
  }

  public void Delete(T toDelete) {
  }

  public Boolean Exists(Object id) {
  return null;
  }

  protected T InstanciateEntity(ResultSet resultSet) {
  return null;
  }

}