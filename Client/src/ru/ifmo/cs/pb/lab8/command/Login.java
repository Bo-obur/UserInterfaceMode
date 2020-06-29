package ru.ifmo.cs.pb.lab8.command;

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


      public static class Builder {

            private final Login loginCmd;

            public Builder() { this.loginCmd = new Login(); }

            public Builder setObject(User user) {
                  loginCmd.setObject(user);
                  return this;
            }

            public Login build() { return loginCmd; }
      }
}
