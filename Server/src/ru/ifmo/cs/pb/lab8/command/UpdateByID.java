package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.Laboratory;
import ru.ifmo.cs.pb.lab8.object.User;

/**
 * Class implementing command 'update_by_id'
 */
public class UpdateByID extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111000000000L;

      public UpdateByID() {
            this.setName("update_by_id");
            this.setType(Type.MODIFY);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);
            if (controller.update((Laboratory) getObject(), user)) {
                  collection.update((Laboratory) getObject());
                  aqPack.setFlag(true);
            } else {
                  aqPack.setFlag(false);
            }
            return aqPack;
      }
}
