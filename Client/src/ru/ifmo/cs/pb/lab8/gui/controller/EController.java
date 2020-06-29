package ru.ifmo.cs.pb.lab8.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.ifmo.cs.pb.lab8.basic.EventHandler;
import ru.ifmo.cs.pb.lab8.command.AbstractCommand;
import ru.ifmo.cs.pb.lab8.command.CommandInvoker;
import ru.ifmo.cs.pb.lab8.gui.EventListener;
import ru.ifmo.cs.pb.lab8.gui.I18N;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EController implements Initializable {

      public static final Stage stage = new Stage();

      @FXML
      public void closeButtonAction() {
            stage.close();
      }

      //****************************************************************************//

      static int counter = 0;

      private EController getInstance() {
            if (counter++ == Byte.MAX_VALUE)
                  throw new RuntimeException();
            return new EController();
      }

      //****************************************************************************//

      private final FileChooser chooser = new FileChooser();

      @FXML
      public javafx.scene.control.TextField pathField;
      @FXML
      public javafx.scene.control.TextArea areaField;
      @FXML
      public javafx.scene.control.Button fileButton;
      @FXML
      public void chooseFileAction() {
            File selectedFile = chooser.showOpenDialog(fileButton.getScene().getWindow());
            if (selectedFile != null) {
                  pathField.setText(selectedFile.getAbsolutePath());
                  try {
                        scaner = new Scanner(selectedFile);
                  } catch (FileNotFoundException exception) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setGraphic(null);
                        alert.setHeaderText("Error");
                        alert.setContentText("File not found!");
                        alert.showAndWait();
                  }
            }
            areaField.clear();
      }

      //****************************************************************************//

      private static Scanner scaner = null;

      @FXML
      public void runButtonAction() {
            if (scaner != null) {
                  Scanner scanner = scaner;
                  scaner = null;
                  String string;
                  String[] cmd;
                  while (scanner.hasNextLine()) {
                        string = scanner.nextLine().trim();
                        cmd = string.split("\\s+");
                        if (cmd[0].toLowerCase().equals("execute_script")){
                              try {
                                    runScript(string.substring(15));
                              } catch (RuntimeException exception) {
                                    if (counter-- == 1) {
                                          stage.close();
                                          Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                          alert.setGraphic(null);
                                          alert.setHeaderText("Error");
                                          alert.setContentText("Stack Overflowed!!!");
                                          alert.showAndWait();
                                    } else throw new RuntimeException();
                              }
                        }
                        AbstractCommand command = CommandInvoker.parse(cmd, scanner);
                        if (command == null) continue;
                        EventHandler.getInstance().sentCommandToServer(command, new EventListener() {
                              @Override
                              public void onSuccessful(Object object) {
                              }
                              @Override
                              public void onError() {
                              }
                        });
                  }
            }
      }

      private void runScript(String path) {
            try {
                  scaner = new Scanner(Paths.get(path));
                  this.getInstance().runButtonAction();
            } catch (IOException ignored) {
            }
      }

      //****************************************************************************//

      @FXML
      public javafx.scene.control.Label label;
      @FXML
      public javafx.scene.control.Button runButton;

      double xOffset = 0;
      double yOffset = 0;

      @Override
      public void initialize(URL location, ResourceBundle resources) {

            label.textProperty().bind(I18N.createStringBinding("label.execute"));
            fileButton.textProperty().bind(I18N.createStringBinding("button.file"));
            runButton.textProperty().bind(I18N.createStringBinding("button.run"));
            pathField.promptTextProperty().bind(I18N.createStringBinding("field.path"));

            //grab your label here
            label.setOnMousePressed(event -> {
                  xOffset = event.getSceneX();
                  yOffset = event.getSceneY();
            });
            //move around here
            label.setOnMouseDragged(event -> {
                  stage.setX(event.getScreenX() - xOffset);
                  stage.setY(event.getScreenY() - yOffset);
            });
      }
}
