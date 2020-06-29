package ru.ifmo.cs.pb.lab8.database;

import ru.ifmo.cs.pb.lab8.database.sql.pattern.ADD;
import ru.ifmo.cs.pb.lab8.database.sql.pattern.DELETE;
import ru.ifmo.cs.pb.lab8.database.sql.pattern.GET;
import ru.ifmo.cs.pb.lab8.database.sql.pattern.UPDATE;
import ru.ifmo.cs.pb.lab8.database.sql.table.COORDINATES;
import ru.ifmo.cs.pb.lab8.database.sql.table.DISCIPLINE;
import ru.ifmo.cs.pb.lab8.database.sql.table.LABORATORY;
import ru.ifmo.cs.pb.lab8.object.*;

import java.sql.*;
import java.util.LinkedList;

public class DBCollection {

      /**
       * Connection to Data Base
       */
      private final Connection connection;

      /**
       * Constructor
       */
      public DBCollection(Connection connection) {
            this.connection = connection;
      }

      /**
       * Loads collection from the Data Base
       */
      public LinkedList<Laboratory> load() throws SQLException {
            LinkedList<Laboratory> laboratories = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(GET.COLLECTION);
            ResultSet result = statement.executeQuery();
            /* Loading collection from DataBase */
            while (result.next()) {

                  /* Creating an object of class laboratory */
                  Laboratory laboratory = new Laboratory();

                  /* Setting value of fields of the Laboratory*/
                  laboratory.setID(result.getLong(LABORATORY.ID));
                  laboratory.setName(result.getString(LABORATORY.NAME));
                  laboratory.setCreationDate(result.getTimestamp(LABORATORY.CREATION_DATE)
                            .toLocalDateTime().toLocalDate());
                  laboratory.setMinimalPoint(result.getFloat(LABORATORY.MINIMAL_POINT));
                  laboratory.setPerQualityMin(result.getDouble(LABORATORY.PER_QUALITY_MIN));
                  laboratory.setTunedInWorks(result.getInt(LABORATORY.TUNED_IN_WORKS));
                  laboratory.setDifficulty(Difficulty.valueOf(result.getString(LABORATORY.DIFFICULTY)));
                  laboratory.setThisUser(result.getString(LABORATORY.THIS_USER));

                  /* Creating an object of class discipline */
                  Discipline discipline = new Discipline();

                  /* Setting value of fields of the Discipline */
                  discipline.setDisName(result.getString(DISCIPLINE.DIS_NAME));
                  discipline.setSelfStudyHours(result.getInt(DISCIPLINE.SELF_STUDY_HOURS));
                  discipline.setLabsCount(result.getLong(DISCIPLINE.LABS_COUNT));

                  /* Creating an object of class coordinates */
                  Coordinates coordinates = new Coordinates();

                  /* Setting value of fields of the Coordinates*/
                  coordinates.setX(result.getLong(COORDINATES.X));
                  coordinates.setY(result.getInt(COORDINATES.Y));

                  laboratory.setDiscipline(discipline);
                  laboratory.setCoordinates(coordinates);
                  laboratories.add(laboratory);
            }
            statement.close();
            return laboratories;
      }

      /**
       * Inserts new item to the table in Data Base
       */
      public long insert(Laboratory laboratory, User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(ADD.NEW_LAB);
            statement.setString(1, laboratory.getName());
            statement.setTimestamp(2, Timestamp.valueOf(laboratory.getCreationDate().atStartOfDay()));
            statement.setFloat(3, laboratory.getMinimalPoint());
            statement.setDouble(4, laboratory.getPerQualityMin());
            statement.setInt(5, laboratory.getTunedInWorks());
            statement.setString(6, String.valueOf(laboratory.getDifficulty()));
            statement.setString(7, user.getUsername());
            ResultSet set = statement.executeQuery();
            int ID = 0;
            while (set.next())
                  ID = set.getInt(1);
            statement = connection.prepareStatement(ADD.NEW_COOR);
            statement.setLong(1, laboratory.getCoordinates().getX());
            statement.setInt(2, laboratory.getCoordinates().getY());
            statement.setInt(3, ID);
            statement.executeUpdate();
            statement = connection.prepareStatement(ADD.NEW_DIS);
            statement.setString(1, laboratory.getDiscipline().getDisName());
            statement.setInt(2, laboratory.getDiscipline().getSelfStudyHours());
            statement.setLong(3, laboratory.getDiscipline().hashCode());
            statement.setInt(4, ID);
            statement.executeUpdate();
            statement.close();
            return ID;
      }

      /**
       * Clears the collection in Data Base
       */
      public void clear() throws SQLException {
            PreparedStatement statement = connection.prepareStatement(DELETE.COLLECTION);
            statement.executeUpdate();
            statement.close();
      }

      /**
       * Deletes from table item where id equals entered
       */
      public boolean removeByID(Long ID, User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(GET.ITEM_BY_ID);
            statement.setInt(1, Math.toIntExact(ID));
            ResultSet set = statement.executeQuery();
            String username = "";
            while (set.next())
                  username = set.getString(LABORATORY.THIS_USER);
            if (username.equals(user.getUsername()) || user.getUsername().equals("root")) {
                  statement = connection.prepareStatement(DELETE.LABORATORY);
                  statement.setInt(1, Math.toIntExact(ID));
                  statement.executeUpdate();
                  statement.close();
                  return true;
            }
            return false;
      }

      /**
       * Updates item in Data Base
       */
      public boolean update(Laboratory laboratory, User user) throws SQLException {
            if (!laboratory.getThisUser().equals(user.getUsername())
                && !user.getUsername().equals("root")) return false;
            /* updating 'laboratory' table */
            PreparedStatement statement = connection.prepareStatement(UPDATE.LABORATORY);
            statement.setString(1, laboratory.getName());
            statement.setFloat(2, laboratory.getMinimalPoint());
            statement.setDouble(3, laboratory.getPerQualityMin());
            statement.setInt(4, laboratory.getTunedInWorks());
            statement.setString(5, String.valueOf(laboratory.getDifficulty()));
            statement.setLong(6, laboratory.getID());
            statement.executeUpdate();
            /* updating 'coordinates' table */
            statement = connection.prepareStatement(UPDATE.COORDINATES);
            statement.setLong(1, laboratory.getCoordinates().getX());
            statement.setInt(2, laboratory.getCoordinates().getY());
            statement.setInt(3, Math.toIntExact(laboratory.getID()));
            statement.executeUpdate();
            /* updating 'discipline' table */
            statement = connection.prepareStatement(UPDATE.DISCIPLINE);
            statement.setString(1, laboratory.getDiscipline().getDisName());
            statement.setInt(2, laboratory.getDiscipline().getSelfStudyHours());
            statement.setLong(3, laboratory.getDiscipline().getLabsCount());
            statement.setInt(4, Math.toIntExact(laboratory.getID()));
            statement.executeUpdate();
            return true;
      }
}
