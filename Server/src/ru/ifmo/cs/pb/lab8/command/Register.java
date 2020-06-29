package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

/**
 * Class implementing command 'register'
 */
public class Register extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111111000L;

      public Register() {
            this.setName("register");
            this.setType(Type.INFO);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);

            boolean bool = controller.register((User) this.getObject());
            aqPack.setFlag(bool);
            aqPack.setObject(this.getObject());

            return aqPack;
      }
}
