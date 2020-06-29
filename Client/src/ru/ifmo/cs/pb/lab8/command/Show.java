package ru.ifmo.cs.pb.lab8.command;

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
}
