package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

/**
 * Class implementing command 'show'
 */
public class Show extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111111100L;

      public Show() {
            this.setName("show");
            this.setType(Type.INFO);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);
            aqPack.setFlag(true);
            aqPack.setObject(collection.getAsList());
            return aqPack;
      }
}
