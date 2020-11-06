package JardinCollectif;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.<br>
 * <br>
 * <p>
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La méthode serveursSupportes() indique les serveurs supportés.<br>
 * 
 * <pre>
 * Pré-condition
 *   Le driver JDBC approprié doit être accessible.
 *
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et sérialisable,
 *   (s'il est supporté par le serveur).
 * </pre>
 * 
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * @author Marc Frappier - Université de Sherbrooke
 * @author Vincent Ducharme - Université de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 */
public class Connexion {
  private EntityManager em;
  private final EntityManagerFactory emf;

  /**
   * Ouverture d'une connexion en mode autocommit false et sérialisable (si
   * supporté)
   *
   * @param serveur Le type de serveur SQL à utiliser (Valeur : local, dinf).
   * @param bd      Le nom de la base de données sur le serveur.
   * @param user    Le nom d'utilisateur à utiliser pour se connecter à la base de
   *                données.
   * @param pass    Le mot de passe associé à l'utilisateur.
   */
  public Connexion(String serveur, String bd, String user, String pass) throws IFT287Exception, SQLException {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put("javax.persistence.jdbc.user", user);
    properties.put("javax.persistence.jdbc.password", pass);

    if (serveur.equals("local")) {
      emf = Persistence.createEntityManagerFactory(bd + ".odb", properties);
    } else if (serveur.equals("dinf")) {
      emf = Persistence.createEntityManagerFactory("objectdb://bd-info2.dinf.usherbrooke.ca:6136/" + user + "/" + bd,
          properties);
    } else {
      throw new IFT287Exception("Serveur inconnu");
    }

    em = emf.createEntityManager();

    System.out
        .println("Ouverture de la connexion :\n" + "Connecté sur la BD ObjectDB " + bd + " avec l'utilisateur " + user);
  }

  /**
   * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
   */
  public static String serveursSupportes() {
    return "local : PostgreSQL installé localement\n" + "dinf  : PostgreSQL installé sur les serveurs du département\n";
  }

  /**
   * Fermeture d'une connexion
   */
  public void close() throws SQLException {
    em.clear();
    em.close();
    emf.close();
    System.out.println("Connexion fermée");
  }

  public EntityTransaction getTransaction() {
    return em.getTransaction();
  }

  /**
   * Retourne la Connection JDBC
   */
  public EntityManager getEntityManager() {
    return em;
  }
}