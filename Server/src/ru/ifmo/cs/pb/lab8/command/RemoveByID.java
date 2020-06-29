package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

/**
 * Class implementing command 'remove by ID'
 */
public class RemoveByID extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111110000000L;

      public RemoveByID() {
            this.setName("remove_by_id");
            this.setType(Type.MODIFY);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);
            if (controller.deleteByID((Long) this.getArgument(), user)) {
                  collection.delete((Long) this.getArgument());
                  aqPack.setFlag(true);
            } else {
                  aqPack.setFlag(false);
            }
            return aqPack;
      }
}
