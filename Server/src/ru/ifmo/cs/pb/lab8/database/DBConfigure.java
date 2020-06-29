package ru.ifmo.cs.pb.lab8.database;

import org.apache.log4j.Logger;

import ru.ifmo.cs.pb.lab8.database.sql.pattern.DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConfigure {

      /**
       * Connection to Data Base
       */
      private final Connection connection;

      /**
       * Logger for logging connection logs
       */
      private static final Logger LOGGER =
              Logger.getLogger(DBConfigure.class.getSimpleName());

      /**
       * Constructor
       */
      private DBConfigure(Connection connection) {
            this.connection = connection;
      }

      /**
       * Updates connection with Data Base, and return an object of current class
       */
      public static DBConfigure getConnection(String path) {
            try (InputStream propertiesData = new FileInputStream(path)) {
                  Properties properties = new Properties();
                  properties.load(propertiesData);
                  Connection connection = DriverManager.getConnection(
                          properties.getProperty(DB.HOST),
                          properties.getProperty(DB.LOGIN),
                          properties.getProperty(DB.PASSWORD));
                  return new DBConfigure(connection);
            } catch (FileNotFoundException exception) {
                  LOGGER.error("Couldn't find .properties file!");
                  System.exit(1);
            } catch (IOException exception) {
                  LOGGER.error("Couldn't read data from .properties file!");
                  System.exit(1);
            } catch (SQLException exception) {
                  LOGGER.error("Couldn't connect to the Data Base!");
                  System.exit(1);
            }
            return null;
      }

      /**
       * Gets connection
       */
      public Connection getConnection() {
            return connection;
      }
}
