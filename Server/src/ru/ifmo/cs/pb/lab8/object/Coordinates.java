package ru.ifmo.cs.pb.lab8.object;

import java.io.Serializable;

public class Coordinates implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 2222000022222222222L;

      private long x;

      public long getX() {
            return x;
      }

      public void setX(long x) {
            this.x = x;
      }

      private int y;

      public int getY() {
            return y;
      }

      public void setY(int y) {
            this.y = y;
      }
}
