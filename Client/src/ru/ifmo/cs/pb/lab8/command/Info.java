package ru.ifmo.cs.pb.lab8.command;

public class Info extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111000000L;

      public Info() {
            this.setName("info");
            this.setType(Type.INFO);
      }
}
