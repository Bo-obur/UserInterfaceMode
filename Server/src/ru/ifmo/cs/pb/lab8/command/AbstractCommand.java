package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

import java.io.Serializable;

/**
 * The abstract class of all Commands
 */
public abstract class AbstractCommand implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111111111L;


      public String name;

      public void setName(String name) { this.name = name; }

      public String getName() { return name; }


      private Type type;

      public void setType(Type type) { this.type = type; }

      public Type getType() { return type; }


      private Object argument;

      public Object getArgument() { return argument; }

      public void setArgument(Object argument) { this.argument = argument; }


      private Object object;

      public void setObject(Object object) { this.object = object; }

      public Object getObject() { return object; }


      public abstract AQPackage execute(Collection collection, DBController controller, User user);
}
