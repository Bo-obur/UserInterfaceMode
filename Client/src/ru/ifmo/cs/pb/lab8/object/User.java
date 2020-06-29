package ru.ifmo.cs.pb.lab8.object;

import java.io.Serializable;

/**
 * Class implementing object 'user'
 */
public class User implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 2002002020020020020L;

      /* Field 'username' with it's setter and getter */

      private String username;

      public String getUsername() { return username; }

      public void setUsername(String username) { this.username = username; }

      /* Field 'password' with it's setter and getter*/
      
      private String password;

      public String getPassword() { return password; }

      public void setPassword(String password) { this.password = password; }
}
