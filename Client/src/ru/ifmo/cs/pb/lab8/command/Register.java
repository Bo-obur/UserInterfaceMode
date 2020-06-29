package ru.ifmo.cs.pb.lab8.command;

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

      public static class Builder {

            private final Register registerCmd;

            public Builder() { this.registerCmd = new Register(); }

            public Builder setObject(User user) {
                  registerCmd.setObject(user);
                  return this;
            }

            public Register build() { return registerCmd; }
      }
}
