package ru.ifmo.cs.pb.lab8.database.sql.pattern;

public abstract class DELETE {

      public static final String COLLECTION = "DELETE FROM LABORATORY";

      public static final String LABORATORY = "DELETE FROM LABORATORY WHERE ID=?";

      public static final String LABORATORY_USER = "DELETE FROM LABORATORY WHERE ID=? AND THIS_USER=?";
}
