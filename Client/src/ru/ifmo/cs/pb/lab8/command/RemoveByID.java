package ru.ifmo.cs.pb.lab8.command;

/**
 * Class imlementing command 'remove by ID'
 */
public class RemoveByID extends AbstractCommand {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 1111111111110000000L;

      public RemoveByID() {
            this.setName("remove_by_id");
            this.setType(Type.MODIFY);
      }

      public static class Builder {

            private final RemoveByID removeByID;

            public Builder() { this.removeByID = new RemoveByID(); }

            public Builder setArgument(Object argument) {
                  removeByID.setArgument(argument);
                  return this;
            }

            public RemoveByID build() {
                  return removeByID;
            }
      }
}
