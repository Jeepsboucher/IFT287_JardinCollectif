// Travail fait par :
//   Jean-Philippe Boucher - 19 125 046
//   Charles Lachance - 17 093 137

package JardinCollectif;

import java.io.*;

/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 *
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 7 juillet 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class JardinCollectif {
  private static Connection cx;
  private static JardinCollectifCommandHandler commandHandler;

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 4) {
      System.out.println(
          "Usage: java JardinCollectif.JardinCollectif <serveur> <bd> <user> <password> [<fichier-transactions>]");
      return;
    }

    cx = null;

    try {
      // Il est possible que vous ayez à déplacer la connexion ailleurs.
      // N'hésitez pas à le faire!
      cx = new Connection(args[0], args[1], args[2], args[3]);
      commandHandler = new JardinCollectifCommandHandler(cx);

      BufferedReader reader = ouvrirFichier(args);
      String transaction = lireTransaction(reader);
      while (!finTransaction(transaction)) {
        executerTransaction(transaction);
        transaction = lireTransaction(reader);
      }
    } finally {
      if (cx != null)
        cx.close();
    }
  }

  /**
   * Decodage et traitement d'une transaction
   */
  static void executerTransaction(String transaction) throws Exception, IFT287Exception {
    try {
      commandHandler.handleCommand(transaction);
    } catch (Exception e) {
      System.out.println("** " + e.toString());
    }
  }

  // ****************************************************************
  // * Les methodes suivantes n'ont pas besoin d'etre modifiees *
  // ****************************************************************

  /**
   * Ouvre le fichier de transaction, ou lit à partir de System.in
   */
  public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
    if (args.length < 5)
      // Lecture au clavier
      return new BufferedReader(new InputStreamReader(System.in));
    else
      // Lecture dans le fichier passe en parametre
      return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
  }

  /**
   * Lecture d'une transaction
   */
  static String lireTransaction(BufferedReader reader) throws IOException {
    return reader.readLine();
  }

  /**
   * Verifie si la fin du traitement des transactions est atteinte.
   */
  static boolean finTransaction(String transaction) {
    // fin de fichier atteinte
    return (transaction == null || transaction.equals("quitter"));
  }
}
