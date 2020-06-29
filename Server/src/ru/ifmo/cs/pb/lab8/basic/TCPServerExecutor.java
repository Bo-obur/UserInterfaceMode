package ru.ifmo.cs.pb.lab8.basic;

import org.apache.log4j.Logger;
import ru.ifmo.cs.pb.lab8.command.AbstractCommand;
import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBController;

import java.util.concurrent.Callable;

public class TCPServerExecutor implements Callable<AQPackage> {

      private final AQPackage aqPackage;
      private final Collection collection;
      private final DBController controller;

      private static final Logger LOGGER = Logger.getLogger(TCPServerExecutor.class.getSimpleName());

      public TCPServerExecutor(AQPackage aqPackage, Collection collection, DBController controller) {
            this.aqPackage = aqPackage;
            this.collection = collection;
            this.controller = controller;
      }

      @Override
      public AQPackage call() {
            AbstractCommand command = aqPackage.getCommand();
            return command.execute(collection, controller, aqPackage.getUser());
      }
}
