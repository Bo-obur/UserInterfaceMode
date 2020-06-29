package ru.ifmo.cs.pb.lab8.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandInvoker {


      public static Map<String, AbstractCommand> availableCommands = new HashMap<String, AbstractCommand>() {
            {
                  put(new Clear());
                  put(new Info());
            }
            void put(AbstractCommand command) {
                  put(command.getName(), command);
            }
      };

      public static AbstractCommand parse(String[] args, Scanner scanner) {
            return availableCommands.getOrDefault(args[0].toLowerCase().trim(), null);
      }
}
