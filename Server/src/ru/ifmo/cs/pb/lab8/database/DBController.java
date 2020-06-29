package ru.ifmo.cs.pb.lab8.database;

import org.apache.log4j.Logger;
import ru.ifmo.cs.pb.lab8.object.Laboratory;
import ru.ifmo.cs.pb.lab8.object.User;

import java.sql.SQLException;
import java.util.Random;

public class DBController {

      private final DBCollection collection;
      private final DBUser users;

      /**
       * Logger for logging data base controller logs
       */
      private static final Logger LOGGER = Logger.getLogger(DBController.class.getSimpleName());

      /**
       * Constructor
       */
      public DBController(DBCollection collection, DBUser users) {
            this.collection = collection;
            this.users = users;
      }


      /**
       * Loads collection from the Data Base
       */
      public Collection loadCollection() {
            try {
                  Random random = new Random();
                  LOGGER.info("Loading collection from the Data Base...");
                  Thread.sleep(100 + random.nextInt(500));
                  LOGGER.info("Complete loading - " + (10 + random.nextInt(15)) + "%");
                  Thread.sleep(100 + random.nextInt(500));
                  LOGGER.info("Complete loading - " + (35 + random.nextInt(15)) + "%");
                  /* Loading collection from the Data Base */
                  Collection collection = new Collection(this.collection.load()); // -> loading collection
                  Thread.sleep(100 + random.nextInt(500));
                  LOGGER.info("Complete loading - " + (60 + random.nextInt(15)) + "%");
                  Thread.sleep(100 + random.nextInt(500));
                  LOGGER.info("Complete loading - " + (75 + random.nextInt(15)) + "%");
                  Thread.sleep(100 + random.nextInt(500));
                  LOGGER.info("Complete loading - 100%");
                  return collection;
            } catch (SQLException exception) {
                  LOGGER.error("Couldn't load collection from the Data Base!");
                  System.exit(1);
            } catch (InterruptedException exception) {
                  LOGGER.error("Thread interrupted!");
                  System.exit(1);
            }
            return null;
      }

      /**
       * Login new user to the server
       */
      public synchronized boolean login(User user) {
            try {
                  return users.login(user);
            } catch (SQLException exception) {
                  LOGGER.error("Couldn't login user to the server!");
                  return false;
            }
      }

      /**
       * Register new user to the server
       */
      public synchronized boolean register(User user) {
            try {
                  return users.register(user);
            } catch (SQLException exception) {
                  LOGGER.error("Couldn't register user to the server!");
                  return false;
            }
      }

      /**
       * Adding new user to the collection
       */
      public synchronized long insert(Laboratory laboratory, User user) {
            try {
                  return collection.insert(laboratory, user);
            } catch (SQLException exception) {
                  LOGGER.error("Couldn't add new item to the collection!");
                  return -1;
            }
      }

      /**
       * Clearing table 'laboratory' in Data Base
       */
      public synchronized void clear() {
            try {
                  collection.clear();
            } catch (SQLException ignored) {
                  LOGGER.error("Couldn't clear table in Data Base!");
            }
      }

      /**
       * Deletes item from the collection where id equals to entered
       */
      public synchronized boolean deleteByID(Long ID, User user) {
            try {
                  return collection.removeByID(ID, user);
            } catch (SQLException exception) {
                  LOGGER.error("User doesn't have permission!");
                  return false;
            }
      }

      /**
       * Edites item in Data Base
       */
      public synchronized boolean update(Laboratory laboratory, User user) {
            try {
                  return collection.update(laboratory, user);
            } catch (SQLException exception) {
                  LOGGER.error("User doesn't have permission!");
                  return false;
            }
      }

}
