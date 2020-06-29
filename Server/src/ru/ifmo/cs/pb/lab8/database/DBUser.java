package ru.ifmo.cs.pb.lab8.database;

import ru.ifmo.cs.pb.lab8.database.sql.pattern.ADD;
import ru.ifmo.cs.pb.lab8.database.sql.pattern.GET;
import ru.ifmo.cs.pb.lab8.database.sql.table.USER;
import ru.ifmo.cs.pb.lab8.object.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {

      /**
       * Connection to Data Base
       */
      private final Connection connection;

      /**
       * Constructor
       */
      public DBUser(Connection connection) {
            this.connection = connection;
      }

      /**
       * Register of new user to the server
       */
      public boolean register(User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(ADD.NEW_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, DBUser.hashBySHA_1(user.getPassword()));
            statement.executeUpdate();
            statement.close();
            return true;
      }

      /**
       * Login of new user to the server
       */
      public boolean login(User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(GET.PASSWORD_BY_USER);
            statement.setString(1, user.getUsername());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                  if (result.getString(USER.PASSWORD)
                          .equals(DBUser.hashBySHA_1(user.getPassword())))
                        return true;
            }
            statement.close();
            return false;
       }


      /**
       * Hashes by algorithm 'SHA-1'
       */
      private static String hashBySHA_1(String password) {
            try {
                  MessageDigest digest = MessageDigest.getInstance("SHA-1");
                  byte[] passwordInBytes = password.getBytes(StandardCharsets.UTF_8);
                  byte[] hashOfPassword = digest.digest(passwordInBytes);
                  BigInteger integer = new BigInteger(1, hashOfPassword);
                  return integer.toString(16);
            } catch (NoSuchAlgorithmException exception) {
                  return password;
            }
      }
}
