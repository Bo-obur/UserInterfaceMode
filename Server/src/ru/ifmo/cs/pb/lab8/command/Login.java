package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.basic.AQPackage;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.object.User;

/**
 * Class implementing command 'login'
 */
public class Login extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111111110L;

      public Login() {
            this.setName("login");
            this.setType(Type.INFO);
      }

      @Override
      public AQPackage execute(Collection collection, DBController controller, User user) {
            AQPackage aqPack = new AQPackage(this);

            boolean bool = controller.login((User) this.getObject());
            aqPack.setFlag(bool);
            aqPack.setObject(this.getObject());

            return aqPack;
      }
}
