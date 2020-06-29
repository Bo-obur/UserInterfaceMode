package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.Laboratory;
import ru.ifmo.cs.pb.lab8.object.User;

public class Add extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111110000L;

      public Add() {
            this.setName("add");
            this.setType(Type.MODIFY);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);
            Laboratory laboratory = (Laboratory) getObject();
            long ID = controller.insert(laboratory, user);
            if (ID == -1) {
                  aqPack.setFlag(false);
            } else {
                  aqPack.setFlag(true);
                  /* adding new item to the collection */
                  laboratory.setID(ID);
                  laboratory.setThisUser(user.getUsername());
                  collection.add(laboratory);
            }
            return aqPack;
      }
}
