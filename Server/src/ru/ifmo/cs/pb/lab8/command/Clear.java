package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

public class Clear extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111100000L;

      public Clear() {
            this.setName("clear");
            this.setType(Type.MODIFY);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);
            if (!user.getUsername().equals("root")){
                  aqPack.setFlag(false);
            } else {
                  controller.clear();
                  collection.clear();
                  aqPack.setFlag(true);
            }
            return aqPack;
      }
}
