package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.object.Laboratory;

/**
 * Class implementing command 'add'
 */
public class Add extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111111110000L;

      public Add() {
            this.setName("add");
            this.setType(Type.MODIFY);
      }

      public static class Builder {

            private Add add;

            public Builder() { this.add = new Add(); }

            public Builder setObject(Laboratory laboratory) {
                  add.setObject(laboratory);
                  return this;
            }

            public Add build() {
                  return this.add;
            }
      }
}
