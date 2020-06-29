package ru.ifmo.cs.pb.lab8.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import ru.ifmo.cs.pb.lab8.basic.EventHandler;
import ru.ifmo.cs.pb.lab8.command.Login;
import ru.ifmo.cs.pb.lab8.gui.EventListener;
import ru.ifmo.cs.pb.lab8.gui.I18N;
import ru.ifmo.cs.pb.lab8.gui.Paths;
import ru.ifmo.cs.pb.lab8.object.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

      //****************************************************************************//

      @FXML
      private javafx.scene.control.Button closeButton;

      @FXML
      public void closeButtonAction() {

            /* Getting stage from button */
            Stage stage = (Stage) closeButton.getScene().getWindow();

            /* Closing stage wholly */
            stage.close();
            System.exit(1);
      }

      //****************************************************************************//

      @FXML
      private javafx.scene.control.Button continueButton;

      @FXML
      private javafx.scene.control.TextField textField;

      @FXML
      private javafx.scene.control.PasswordField passwordField;

      @FXML
      public void continueButtonAction() {

            boolean allRight = true;

            /* Reading text from text and password fields */
            String username = textField.getText().trim();
            String password = passwordField.getText().trim();

            /* CSS pattern to set red color for border */
            String css = "-fx-border-color: red; -fx-focus-color: red";

            /* Checking field username */
            textField.setStyle(username.length() < 4 ?
                    ((allRight = false) ? css : css) : null);

            /* Checking field password */
            passwordField.setStyle(password.length() < 6 ?
                    ((allRight = false) ? css : css) : null);

            if (!allRight) return; // returns - fields filled not correctly

            /* Creating an object 'user' */
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            /* Building command 'login' */
            Login loginCmd = new Login.Builder().setObject(user).build();

            /* Sending to the server */
            EventHandler.getInstance().sentCommandToServer(loginCmd, new EventListener() {
                  @Override
                  public void onSuccessful(Object object) {
                        Platform.runLater(new Runnable() {

                              double xOffset = 0;
                              double yOffset = 0;

                              @Override
                              public void run() {
                                    EventHandler.user = (User) object;

                                    try {
                                          Parent root = FXMLLoader.load(getClass().getResource(Paths.fromControllerToTableFXML));
                                          Scene scene = new Scene(root);

                                          Stage primaryStage = (Stage) continueButton.getScene().getWindow();

                                          //grab your root here
                                          root.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
                                                @Override
                                                public void handle(MouseEvent event) {
                                                      xOffset = event.getSceneX();
                                                      yOffset = event.getSceneY();
                                                }
                                          });

                                          //move around here
                                          root.setOnMouseDragged(new javafx.event.EventHandler<MouseEvent>() {
                                                @Override
                                                public void handle(MouseEvent event) {
                                                      primaryStage.setX(event.getScreenX() - xOffset);
                                                      primaryStage.setY(event.getScreenY() - yOffset);
                                                }
                                          });

                                          primaryStage.setWidth(900);
                                          primaryStage.setHeight(600);
                                          primaryStage.centerOnScreen();

                                          primaryStage.setTitle("GUI Mode");
                                          primaryStage.setScene(scene);

                                    } catch (IOException exception) {
                                          exception.printStackTrace();
                                    }
                              }
                        });

                  }

                  @Override
                  public void onError() {
                        Platform.runLater(() -> {
                              Alert alert = new Alert(Alert.AlertType.ERROR);
                              alert.setGraphic(null);
                              alert.setHeaderText("Login failed!");
                              alert.setContentText("Username or password is not correct");
                              alert.showAndWait();
                        });
                  }
            });
      }

      //****************************************************************************//

      @FXML
      public javafx.scene.control.Hyperlink registerHyperlink;

      double xOffset = 0;
      double yOffset = 0;

      @FXML
      public void registerHyperlinkAction() throws IOException {

            /* Loading .fxml file and setting scene */
            Parent root = FXMLLoader.load(getClass().getResource(Paths.fromControllerToRegisterFXML));
            Scene scene = new Scene(root);

            /* Getting stage from hyperlink */
            Stage primaryStage = (Stage) registerHyperlink.getScene().getWindow();

            //grab your root here
            root.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                  }
            });

            //move around here
            root.setOnMouseDragged(new javafx.event.EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent event) {
                        primaryStage.setX(event.getScreenX() - xOffset);
                        primaryStage.setY(event.getScreenY() - yOffset);
                  }
            });

            primaryStage.setWidth(600);
            primaryStage.setHeight(400);
            primaryStage.centerOnScreen();

            /* Setting title text of the primary stage */
            primaryStage.setTitle("User Register");
            primaryStage.setScene(scene);
      }

      //****************************************************************************//

      @FXML
      private javafx.scene.control.Label loginLabel;
      @FXML
      private javafx.scene.control.Label loginQuaLabel;

      @Override
      public void initialize(URL location, ResourceBundle resources) {

            loginLabel.textProperty().bind(I18N.createStringBinding("label.login"));
            loginQuaLabel.textProperty().bind(I18N.createStringBinding("label.login.question"));

            textField.promptTextProperty().bind(I18N.createStringBinding("field.username"));
            passwordField.promptTextProperty().bind(I18N.createStringBinding("field.password"));

            registerHyperlink.textProperty().bind(I18N.createStringBinding("hyper.register"));

            continueButton.textProperty().bind(I18N.createStringBinding("button.continue"));
      }
}
