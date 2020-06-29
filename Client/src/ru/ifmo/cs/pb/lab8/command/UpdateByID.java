package ru.ifmo.cs.pb.lab8.command;

import ru.ifmo.cs.pb.lab8.object.Laboratory;

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

      public static class Builder {

            private final UpdateByID updateByID;

            public Builder() { this.updateByID = new UpdateByID(); }

            public Builder setObject(Laboratory laboratory) {
                  updateByID.setObject(laboratory);
                  return this;
            }

            public UpdateByID build() { return updateByID; }
      }
}
