package ru.ifmo.cs.pb.lab8.database;

import ru.ifmo.cs.pb.lab8.object.Laboratory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Collection {

      private final LinkedList<Laboratory> laboratories;

      public Collection(LinkedList<Laboratory> laboratories) {
            this.laboratories = laboratories;
      }

      public ArrayList<Laboratory> getAsList() {
            laboratories.sort(Comparator.comparing(Laboratory::getID));
            return new ArrayList<>(laboratories);
      }

      private LocalDate initDate = LocalDate.now();

      private void refresh() {
            initDate = LocalDate.now();
      }

      /**
       * Add new item to the collection
       */
      public synchronized void add(Laboratory laboratory) {
            laboratories.add(laboratory);
            refresh();
      }

      /**
       * Clears the collection
       */
      public synchronized void clear() {
            laboratories.clear();
            refresh();
      }

      /**
       * Deletes from the collection item where Id equals entered
       */
      public synchronized void delete(Long ID) {
            laboratories.removeIf(laboratory -> laboratory.getID().equals(ID));
      }

      /**
       * Returns information about collection
       */
      public String info() {
            return "type of collection        -  " + laboratories.getClass().getSimpleName() + "\n" +
                   "number of collection   -  " + laboratories.size() + "\n" +
                   "initialization date         -  " + initDate.toString();
      }

      /**
       * Edits item in collection
       */
      public synchronized void update(Laboratory laboratory) {
            for (Laboratory l : laboratories) {
                  if (l.getID().equals(laboratory.getID())) {
                        laboratories.remove(l);
                        laboratories.add(laboratory);
                        refresh();
                        return;
                  }
            }
      }
}
