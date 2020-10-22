package JardinCollectif;

import Integer;
import String;

import java.util.Date;
import java.util.StringTokenizer;

public abstract class CommandHandler extends JardinCollectifCommandHandler {
  /* {src_lang=Java}*/


  protected CommandHandler() {
    return null;
  }

  protected static String readString(StringTokenizer tokenizer) {
    return null;
  }

  protected static Integer readInt(StringTokenizer tokenizer) {
    return null;
  }

  protected static Date readDate(StringTokenizer tokenizer) {
    return null;
  }

  public void handleCommand(String command) {
  }

}