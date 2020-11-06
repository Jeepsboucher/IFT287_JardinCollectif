package JardinCollectif.repositories.helpers;

import JardinCollectif.IFT287Exception;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableHelper {
  private final String tableName;
  private final List<String> primaryKey;

  public TableHelper(Class entityType) throws IFT287Exception {
    tableName = entityType.getSimpleName();

    primaryKey = new ArrayList<>();
    for (Field field : entityType.getFields()) {
      Id id = field.getAnnotation(Id.class);
      if (id != null) {
        String idName = field.getName();
        primaryKey.add(idName);
      }
    }

    if (primaryKey.isEmpty()) {
      throw new IFT287Exception("Entity should have a primary key.");
    }
  }

  public String getTableName() {
    return tableName;
  }

  public List<String> getPrimaryKey() {
    return primaryKey;
  }
}
