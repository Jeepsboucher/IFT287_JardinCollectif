package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.annotations.Initializer;
import JardinCollectif.repositories.helpers.ColumnHelper;
import JardinCollectif.repositories.helpers.GenericHelper;
import JardinCollectif.repositories.helpers.TableHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

public abstract class Repository<T> extends GenericHelper<T> {
  protected final Connection connection;

  public MongoCollection<Document> collection;

  private final TableHelper tableHelper;

  public Repository(Connection connection) throws ClassNotFoundException, IFT287Exception {
    this.connection = connection;
    tableHelper = new TableHelper(getGenericType());

    String tableName = tableHelper.getTableName();

    collection = connection.getDatabase().getCollection(tableName);
  }

  public Connection getConnection() {
    return connection;
  }

  public void create(T toCreate) {
    try {

      Document doc = getDocument(toCreate);

      collection.insertOne(doc);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public T retrieve(Object... id) throws IFT287Exception {
    List<ColumnHelper> idNames = tableHelper.getPrimaryKey();
    if (idNames.size() != id.length)
      throw new IFT287Exception("Wrong number of primary key provided.");

    BasicDBObject options = new BasicDBObject();
    for (int i = 0; i < id.length; i++) {
      options.put(idNames.get(i).getName(), id[i]);
    }

    MongoCursor<Document> retrieveCollection = collection.find(options).iterator();
    List<T> entities = new LinkedList<T>();
    try {
      while (retrieveCollection.hasNext()) {

        entities.add(instantiateEntity(retrieveCollection.next()));
      }
    } finally {
      retrieveCollection.close();
    }

    return entities.isEmpty() ? null : entities.get(0);
  }

  public List<T> retrieveAll() throws IFT287Exception {
    List<T> entities = new LinkedList<T>();
    MongoCursor<Document> allCollection = collection.find().iterator();
    try {
      while (allCollection.hasNext()) {

        entities.add(instantiateEntity(allCollection.next()));
      }
    } finally {
      allCollection.close();
    }

    return entities;
  }

  protected T instantiateEntity(Document doc) throws IFT287Exception {
    Class type = getGenericType();

    List<Constructor> constructors = Arrays.asList(type.getConstructors());
    Constructor constructor = null;
    if (constructors.size() == 1) {
      constructor = constructors.get(0);
    } else {
      for (int i = 0; i < constructors.size(); i++) {
        if (constructors.get(i).getAnnotation(Initializer.class) != null) {
          constructor = constructors.get(i);
          break;
        }
      }
    }

    if (constructor == null) {
      throw new IFT287Exception(
          type.getName() + " should have only one constructor or a constructor with the \"Initializer\" annotation.");
    }
    T instance = null;
    try {
      instance = (T) constructor.newInstance(doc);
    } catch (InstantiationException e) {
      throw new IFT287Exception("Cannot instantiate " + type.getName());
    } catch (IllegalAccessException e) {
      throw new IFT287Exception(type.getName() + " should have a public initializer.");
    } catch (InvocationTargetException e) {
      System.out.println(e.getMessage());
    }

    return instance;
  }

  public long update(T toUpdate) {
    long modifiedCount = 0;
    try {
      List<ColumnHelper> columns = tableHelper.getColumns().stream().filter(columnHelper -> !columnHelper.isPrimary())
          .collect(Collectors.toList());

      BasicDBObject updateFields = new BasicDBObject();

      for (ColumnHelper column : columns) {
        Field field = column.getField();
        updateFields.append(column.getName(), field.get(toUpdate));
      }
      BasicDBObject setQuery = new BasicDBObject();
      setQuery.append("$set", updateFields);

      BasicDBObject primaryArgs = new BasicDBObject();
      List<ColumnHelper> primaryKey = tableHelper.getPrimaryKey();
      for (ColumnHelper column : primaryKey) {
        Field field = column.getField();
        primaryArgs.append(column.getName(), field.get(toUpdate));
      }
      modifiedCount = collection.updateOne(primaryArgs, setQuery).getModifiedCount();

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return modifiedCount;
  }

  public void delete(T toDelete) {
    Document doc;
    try {
      doc = getDocument(toDelete);
      collection.deleteOne(doc);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public long delete(Object... id) throws IFT287Exception {
    List<ColumnHelper> idNames = tableHelper.getPrimaryKey();
    if (idNames.size() != id.length)
      throw new IFT287Exception("Wrong number of primary key provided.");

    BasicDBObject options = new BasicDBObject();
    for (int i = 0; i < id.length; i++) {
      options.put(idNames.get(i).getName(), id[i]);
    }

    return collection.deleteMany(options).getDeletedCount();
  }

  public boolean exists(Object... id) throws IFT287Exception {

    List<ColumnHelper> idNames = tableHelper.getPrimaryKey();
    if (idNames.size() != id.length)
      throw new IFT287Exception("Wrong number of primary key provided.");

    BasicDBObject options = new BasicDBObject();
    for (int i = 0; i < id.length; i++) {
      options.put(idNames.get(i).getName(), id[i]);
    }

    MongoCursor<Document> existCollection = collection.find(options).iterator();
    boolean exist = false;
    try {
      exist = existCollection.hasNext();
    } finally {
      existCollection.close();
    }

    return exist;
  }

  public Document getDocument(T toGet) throws IllegalArgumentException, IllegalAccessException {
    List<ColumnHelper> columns = tableHelper.getColumns();
    Document doc = new Document();

    for (ColumnHelper column : columns) {
      Field field = column.getField();
      doc.append(column.getName(), field.get(toGet));
    }

    return doc;
  }
}
