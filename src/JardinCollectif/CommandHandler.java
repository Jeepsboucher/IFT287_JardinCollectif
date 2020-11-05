package JardinCollectif;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import JardinCollectif.annotations.Command;

public abstract class CommandHandler {
  private final HashMap<String, Method> dispatcher;

  protected CommandHandler() {
    dispatcher = new HashMap<>();

    for (Method method : this.getClass().getMethods()) {
      Command command = method.getAnnotation(Command.class);
      if (command != null) {
        dispatcher.put(command.value(), method);
      }
    }
  }

  private static String readString(StringTokenizer tokenizer) throws IFT287Exception {
    if (tokenizer.hasMoreElements()) {
      return tokenizer.nextToken();
    } else {
      throw new IFT287Exception("Mauvais nombre de paramètres.");
    }
  }

  /**
   * Lecture d'un int java de la transaction entree a l'ecran
   */
  private static int readInt(StringTokenizer tokenizer) throws IFT287Exception {
    if (tokenizer.hasMoreElements()) {
      String token = tokenizer.nextToken();
      try {
        return Integer.valueOf(token).intValue();
      } catch (NumberFormatException e) {
        throw new IFT287Exception("Nombre attendu a la place de \"" + token + "\"");
      }
    } else {
      throw new IFT287Exception("Mauvais nombre de paramètres.");
    }
  }

  /**
   * Lecture d'un long java de la transaction entree a l'ecran
   */
  private static long readLong(StringTokenizer tokenizer) throws IFT287Exception {
    if (tokenizer.hasMoreElements()) {
      String token = tokenizer.nextToken();
      try {
        return Long.valueOf(token).longValue();
      } catch (NumberFormatException e) {
        throw new IFT287Exception("Nombre attendu a la place de \"" + token + "\"");
      }
    } else {
      throw new IFT287Exception("Mauvais nombre de paramètres.");
    }
  }

  private static Date readDate(StringTokenizer tokenizer) throws IFT287Exception {
    if (tokenizer.hasMoreElements()) {
      String token = tokenizer.nextToken();
      try {
        return Date.valueOf(token);
      } catch (IllegalArgumentException e) {
        throw new IFT287Exception("Date dans un format invalide - \"" + token + "\"");
      }
    } else {
      throw new IFT287Exception("Mauvais nombre de paramètres.");
    }
  }

  public void handleCommand(String command) throws IFT287Exception {
    StringTokenizer tokenizer = new StringTokenizer(command, " ");
    if (tokenizer.hasMoreTokens()) {
      String commandName = tokenizer.nextToken();
      Method method = dispatcher.get(commandName);

      if (method == null) {
        throw new IFT287Exception(command + " : Transaction non reconnue.");
      }

      List<Object> params = new ArrayList<>();
      Class[] paramTypes = method.getParameterTypes();
      for (Class type : paramTypes) {
        if (type.equals(Integer.class) || type.equals(int.class)) {
          params.add(readInt(tokenizer));
        } else if (type.equals(Long.class) || type.equals(long.class)) {
          params.add(readLong(tokenizer));
        } else if (type.equals(String.class)) {
          params.add(readString(tokenizer));
        } else if (type.equals(Date.class)) {
          params.add(readDate(tokenizer));
        } else {
          throw new IFT287Exception("Unsupported parameter type in command " + method.getName() + ".");
        }
      }

      try {
        method.invoke(this, params.toArray());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        System.out.println(e.getCause().getMessage());
      }
    }
  }
}
