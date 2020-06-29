package ru.ifmo.cs.pb.lab8.basic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.ifmo.cs.pb.lab8.gui.Paths;

import java.net.InetSocketAddress;

public class TCPClientLauncher extends Application {
      
      private static final Integer PORT = 9090;
      private static final String HOST = "localhost";

      //define your offsets here
      private double xOffset = 0;
      private double yOffset = 0;

      @Override
      public void start(Stage primaryStage) throws Exception {

            /* Loading .fxml file and setting scene */
            Parent root = FXMLLoader.load(getClass().getResource(Paths.fromMainToLoginFXML));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            //grab your root here
            root.setOnMousePressed(event -> {
                  xOffset = event.getSceneX();
                  yOffset = event.getSceneY();
            });

            //move around here
            root.setOnMouseDragged(event -> {
                  primaryStage.setX(event.getScreenX() - xOffset);
                  primaryStage.setY(event.getScreenY() - yOffset);
            });

            /* Setting title text of the primary stage */
            primaryStage.setTitle("User Login");

            /* Setting icon image to the primary stage */
            Image imageIcon = new Image(getClass().getResourceAsStream(Paths.fromMainToIconPNG));
            primaryStage.getIcons().add(imageIcon);

            primaryStage.setWidth(600);
            primaryStage.setHeight(400);
            primaryStage.centerOnScreen();

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();

            /* Starts of new thread */
            EventHandler handler = new EventHandler(new InetSocketAddress(HOST, PORT));
            Thread thread = new Thread(handler);
            thread.start();

            primaryStage.setOnCloseRequest(event -> thread.interrupt());
      }

      public static void main(String[] args) {
            Application.launch(args);
      }
}
