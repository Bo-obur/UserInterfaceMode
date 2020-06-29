package ru.ifmo.cs.pb.lab8.basic;

import ru.ifmo.cs.pb.lab8.command.AbstractCommand;
import ru.ifmo.cs.pb.lab8.object.User;

import java.io.Serializable;

/**
 * Answer/Question Package class
 */
public class AQPackage implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 100010101010111111L;

      public AQPackage(AbstractCommand command) {
            this.command = command;
      }

      private User user;

      public User getUser() { return user; }


      private AbstractCommand command;

      public AbstractCommand getCommand() { return command; }


      private boolean flag;

      public void setFlag(boolean flag) { this.flag = flag; }

      public boolean getFlag() { return flag; }


      private Object object;

      public void setObject(Object object) {
            this.object = object;
      }

      public Object getObject() { return object; }
}
