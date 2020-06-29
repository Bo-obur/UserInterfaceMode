package ru.ifmo.cs.pb.lab8.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.ifmo.cs.pb.lab8.basic.EventHandler;
import ru.ifmo.cs.pb.lab8.command.Add;
import ru.ifmo.cs.pb.lab8.command.UpdateByID;
import ru.ifmo.cs.pb.lab8.gui.EventListener;
import ru.ifmo.cs.pb.lab8.gui.I18N;
import ru.ifmo.cs.pb.lab8.gui.TableFX;
import ru.ifmo.cs.pb.lab8.object.Coordinates;
import ru.ifmo.cs.pb.lab8.object.Difficulty;
import ru.ifmo.cs.pb.lab8.object.Discipline;
import ru.ifmo.cs.pb.lab8.object.Laboratory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AUController implements Initializable {

      public static final Stage stage = new Stage();

      @FXML
      public void closeHyperLinkAction() {
            stage.close();
      }

      //****************************************************************************//

      @FXML
      public javafx.scene.control.Label labelTitleAU;
      @FXML
      public javafx.scene.control.Button buttonAU;

      @FXML
      private javafx.scene.control.TextField idField;
      @FXML
      private javafx.scene.control.TextField nameField;
      @FXML
      private javafx.scene.control.TextField xField;
      @FXML
      private javafx.scene.control.TextField yField;
      @FXML
      private javafx.scene.control.TextField creationDateField;
      @FXML
      private javafx.scene.control.TextField minPointField;
      @FXML
      private javafx.scene.control.TextField perQuaMinField;
      @FXML
      private javafx.scene.control.TextField tuneInWField;
      @FXML
      private javafx.scene.control.ComboBox difficultyField;
      @FXML
      private javafx.scene.control.TextField disciplineField;
      @FXML
      private javafx.scene.control.TextField selfStudHField;
      @FXML
      private javafx.scene.control.TextField labsCountField;

      @FXML
      public Laboratory checkFields() {
            boolean allRight = true;
            /* CSS pattern to set red color for border */
            String css = "-fx-border-color: red; -fx-focus-color: red";
            Laboratory laboratory = new Laboratory();

            if (!nameField.getText().trim().equals("")) {
                  laboratory.setName(nameField.getText().trim());
                  nameField.setStyle(null);
            } else {
                  nameField.setStyle(css);
                  allRight = false;
            }

            Coordinates coordinates = new Coordinates();
            try {
                  coordinates.setX(Long.parseLong(
                          xField.getText().trim().replace(",", ".")));
                  if (coordinates.getX() > 547) throw new RuntimeException();
                  xField.setStyle(null);
            } catch (RuntimeException exception) {
                  xField.setStyle(css);
                  allRight = false;
            }
            try {
                  coordinates.setY(Integer.parseInt(yField.getText().trim()));
                  if (coordinates.getY() <= -583) throw new RuntimeException();
                  yField.setStyle(null);
            } catch (RuntimeException exception) {
                  yField.setStyle(css);
                  allRight = false;
            }

            laboratory.setCoordinates(coordinates);

            try {
                  laboratory.setMinimalPoint(Float.parseFloat(
                          minPointField.getText().trim().replace(",", ".")));
                  if (laboratory.getMinimalPoint() <= 0) throw new RuntimeException();
                  minPointField.setStyle(null);
            } catch (RuntimeException exception) {
                  minPointField.setStyle(css);
                  allRight = false;
            }
            try {
                  laboratory.setPerQualityMin(Double.parseDouble(
                          perQuaMinField.getText().trim().replace(",", ".")));
                  if (laboratory.getPerQualityMin() <= 0) throw new RuntimeException();
                  perQuaMinField.setStyle(null);
            } catch (RuntimeException exception) {
                  perQuaMinField.setStyle(css);
                  allRight = false;
            }
            try {
                  laboratory.setTunedInWorks(Integer.parseInt(tuneInWField.getText().trim()));
                  tuneInWField.setStyle(null);
            } catch (RuntimeException exception) {
                  tuneInWField.setStyle(css);
                  allRight = false;
            }
            try {
                  laboratory.setDifficulty(Difficulty.valueOf(
                          difficultyField.getSelectionModel().getSelectedItem().toString()));
                  difficultyField.setStyle(null);
            } catch (RuntimeException exception) {
                  difficultyField.setStyle(css);
                  allRight = false;
            }
            Discipline discipline = new Discipline();
            if (!disciplineField.getText().trim().equals("")) {
                  discipline.setDisName(disciplineField.getText().trim());
                  disciplineField.setStyle(null);
            } else {
                  disciplineField.setStyle(css);
                  allRight = false;
            }
            try {
                  discipline.setSelfStudyHours(Integer.parseInt(selfStudHField.getText().trim()));
                  selfStudHField.setStyle(null);
            } catch (RuntimeException exception) {
                  selfStudHField.setStyle(css);
                  allRight = false;
            }
            try {
                  discipline.setLabsCount(Long.parseLong(labsCountField.getText().trim()));
                  labsCountField.setStyle(null);
            } catch (RuntimeException exception) {
                  labsCountField.setStyle(css);
                  allRight = false;
            }
            laboratory.setDiscipline(discipline);
            if (allRight) return laboratory;
            else return null;
      }


      //****************************************************************************//

      private TableFX item = null;

      public void setItems(TableFX item) {
            this.item = item;
            idField.setText(String.valueOf(item.getID()));
            nameField.setText(item.getName());
            xField.setText(String.valueOf(item.getX()));
            yField.setText(String.valueOf(item.getY()));
            creationDateField.setText(item.getCreationDate().toString());
            minPointField.setText(String.valueOf(item.getMinimalPoint()));
            perQuaMinField.setText(String.valueOf(item.getPersonQualitiesMinimum()));
            tuneInWField.setText(String.valueOf(item.getTunedInWorks()));
            difficultyField.getSelectionModel().select(item.getDifficulty());
            disciplineField.setText(item.getDiscipline());
            selfStudHField.setText(String.valueOf(item.getSelfStudyHours()));
            labsCountField.setText(String.valueOf(item.getLabsCount()));

            labelTitleAU.textProperty().bind(I18N.createStringBinding("label.update"));
            buttonAU.textProperty().bind(I18N.createStringBinding("button.update"));
      }

      //****************************************************************************//

      @FXML
      public void auButtonAction() {
            Laboratory laboratory = checkFields();
            /* Command 'UPDATE' */
            if (item != null && laboratory != null) {
                  stage.close();
                  laboratory.setID(item.getID());
                  laboratory.setCreationDate(item.getCreationDate());
                  laboratory.setThisUser(item.getThisUser());
                  UpdateByID updateById = new UpdateByID.Builder().setObject(laboratory).build();
                  EventHandler.getInstance().sentCommandToServer(updateById, new EventListener() {
                        @Override
                        public void onSuccessful(Object object) {
                              Platform.runLater(() -> {infoMessage("Success",
                                      "Successfully edited item (id = " + laboratory.getID() + ")"); }); }
                        @Override
                        public void onError() {
                              Platform.runLater(() -> { errorMessage("Edit failed!",
                                      "You don't have permission"); });}
                  });
                  item = null;
                  return;
            }

            /* Command 'ADD' */
            if (laboratory != null) {
                  Add add = new Add.Builder().setObject(laboratory).build();
                  EventHandler.getInstance().sentCommandToServer(add, new EventListener() {
                        @Override
                        public void onSuccessful(Object object) {
                              Platform.runLater(() -> { stage.close(); infoMessage("Success!",
                                      "Successfully added new item"); });
                        }
                        @Override
                        public void onError() { }
                  });
            }
      }

      //****************************************************************************//

      @Override
      public void initialize(URL location, ResourceBundle resources) {
            creationDateField.setText(String.valueOf(LocalDate.now()));

            idField.promptTextProperty().bind(I18N.createStringBinding("field.id"));
            nameField.promptTextProperty().bind(I18N.createStringBinding("field.name"));
            xField.promptTextProperty().bind(I18N.createStringBinding("field.x"));
            yField.promptTextProperty().bind(I18N.createStringBinding("field.y"));
            creationDateField.promptTextProperty().bind(I18N.createStringBinding("field.creation.date"));
            minPointField.promptTextProperty().bind(I18N.createStringBinding("field.minimal.point"));
            perQuaMinField.promptTextProperty().bind(I18N.createStringBinding("field.personal.qualities.minimum"));
            tuneInWField.promptTextProperty().bind(I18N.createStringBinding("field.tuned.in.works"));
            difficultyField.promptTextProperty().bind(I18N.createStringBinding("field.difficulty"));
            disciplineField.promptTextProperty().bind(I18N.createStringBinding("field.discipline"));
            selfStudHField.promptTextProperty().bind(I18N.createStringBinding("field.self.study.hours"));
            labsCountField.promptTextProperty().bind(I18N.createStringBinding("field.labs.count"));

            labelTitleAU.textProperty().bind(I18N.createStringBinding("label.add"));
            buttonAU.textProperty().bind(I18N.createStringBinding("button.add"));
      }

      /**
       * Shows error message
       */
      private void errorMessage(String header, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
      }

      /**
       * Shows info message
       */
      private void infoMessage(String header, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
      }
}
