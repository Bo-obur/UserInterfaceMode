package ru.ifmo.cs.pb.lab8.command;

/**
 * Class implementing command 'clear'
 */
public class Clear extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111100000L;

      public Clear() {
            this.setName("clear");
            this.setType(Type.MODIFY);
      }
}
