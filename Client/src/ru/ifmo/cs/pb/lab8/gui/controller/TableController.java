package ru.ifmo.cs.pb.lab8.gui.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ru.ifmo.cs.pb.lab8.basic.EventHandler;
import ru.ifmo.cs.pb.lab8.command.*;
import ru.ifmo.cs.pb.lab8.gui.EventListener;
import ru.ifmo.cs.pb.lab8.gui.*;
import ru.ifmo.cs.pb.lab8.object.Difficulty;
import ru.ifmo.cs.pb.lab8.object.Laboratory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TableController implements Initializable {

      @FXML
      public javafx.scene.control.Button closeButton;

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
      public javafx.scene.control.Button maximizeButton;

      private static boolean isMax = false;

      @FXML
      public void maximizeButtonAction() {
            Stage primaryStage = (Stage) maximizeButton.getScene().getWindow();
            if (isMax) {
                  primaryStage.setWidth(900);
                  primaryStage.setHeight(600);
                  primaryStage.centerOnScreen();
                  isMax = false;
            } else {
                  Screen screen = Screen.getPrimary();
                  Rectangle2D bounds = screen.getVisualBounds();
                  primaryStage.setWidth(bounds.getWidth());
                  primaryStage.setHeight(bounds.getHeight());
                  primaryStage.centerOnScreen();
                  isMax = true;
            }
      }

      //****************************************************************************//

      @FXML
      private javafx.scene.control.Button hiddenButton;

      /**
       * Hides window on taskbar
       */
      @FXML
      public void hiddenButtonAction() {

            Stage primaryStage = (Stage) hiddenButton.getScene().getWindow();
            primaryStage.setIconified(true);
      }

      //****************************************************************************//

      @FXML
      private javafx.scene.control.TableView<TableFX> collectionTable;

      @FXML
      private javafx.scene.control.TableColumn<Long, String> columnID;
      @FXML
      private javafx.scene.control.TableColumn<String, String> columnName;
      @FXML
      private javafx.scene.control.TableColumn<Long, String> columnX;
      @FXML
      private javafx.scene.control.TableColumn<Integer, String> columnY;
      @FXML
      private javafx.scene.control.TableColumn<LocalDate, String> columnCreationDate;
      @FXML
      private javafx.scene.control.TableColumn<Float, String> columnMinPoints;
      @FXML
      private javafx.scene.control.TableColumn<Double, String> columnPerQualMin;
      @FXML
      private javafx.scene.control.TableColumn<Integer, String> columnTunedInWorks;
      @FXML
      private javafx.scene.control.TableColumn<Difficulty, String> columnDifficulty;
      @FXML
      private javafx.scene.control.TableColumn<String, String> columnDiscipline;
      @FXML
      private javafx.scene.control.TableColumn<Integer, String> columnSelfStudHours;
      @FXML
      private javafx.scene.control.TableColumn<Long, String> columnLabsCount;
      @FXML
      private javafx.scene.control.TableColumn<String, String> columnUser;


      private final EventListener showListener = new EventListener() {
            @Override
            public void onSuccessful(Object object) {
                  Platform.runLater(() -> {
                        List<Laboratory> labs = (List) object;

                        /* Sending to visualization table */
                        updatePoints(labs);

                        ObservableList<TableFX> tableFXES = FXCollections.observableArrayList();
                        labs.forEach(laboratory -> {
                              TableFX table = new TableFX();
                              table.setID(laboratory.getID());
                              table.setName(laboratory.getName());
                              table.setX(laboratory.getCoordinates().getX());
                              table.setY(laboratory.getCoordinates().getY());
                              table.setCreationDate(laboratory.getCreationDate());
                              table.setMinimalPoint(laboratory.getMinimalPoint());
                              table.setPersonQualitiesMinimum(laboratory.getPerQualityMin());
                              table.setTunedInWorks(laboratory.getTunedInWorks());
                              table.setDifficulty(laboratory.getDifficulty().name());
                              table.setDiscipline(laboratory.getDiscipline().getDisName());
                              table.setSelfStudyHours(laboratory.getDiscipline().getSelfStudyHours());
                              table.setLabsCount(laboratory.getDiscipline().getLabsCount());
                              table.setThisUser(laboratory.getThisUser());
                              tableFXES.add(table);
                        });

                        filtered = new FilteredList<>(tableFXES, p -> true);
                        SortedList<TableFX> table = new SortedList<>(filtered);
                        table.comparatorProperty().bind(collectionTable.comparatorProperty());
                        collectionTable.setItems(table);
                        notifySearchTextField();
                  }); }
            @Override
            public void onError() {
            }
      };

      //****************************************************************************//

      @FXML
      private javafx.scene.control.Label mainLabel;
      @FXML
      private javafx.scene.control.Label labelUser;
      @FXML
      private javafx.scene.control.TextField searchField;
      @FXML
      private javafx.scene.control.MenuButton filterBox;

      @FXML
      private javafx.scene.control.Tab tableTab;
      @FXML
      private javafx.scene.control.Tab visualTab;

      @FXML
      private javafx.scene.control.Menu menuFile;
      @FXML
      private javafx.scene.control.Menu menuSittings;
      @FXML
      private javafx.scene.control.Menu menuLanguages;

      @FXML
      private javafx.scene.control.MenuItem itemExit;
      @FXML
      private javafx.scene.control.MenuItem itemOne;
      @FXML
      private javafx.scene.control.MenuItem itemTwo;
      @FXML
      private javafx.scene.control.MenuItem itemThree;
      @FXML
      private javafx.scene.control.MenuItem itemFour;
      @FXML
      private javafx.scene.control.MenuItem itemFive;

      //****************************************************************************//

      private FilteredList<TableFX> filtered;

      private boolean filteredIdSelect = false;
      private boolean filteredNameSelect = false;
      private boolean filteredXSelect = false;
      private boolean filteredYSelect = false;
      private boolean filteredCrDateSelect = false;
      private boolean filteredMinPointSelect = false;
      private boolean filteredPerQuaMinSelect = false;
      private boolean filteredTunedInWorksSelect = false;
      private boolean filteredDifficultySelect = false;
      private boolean filteredDisciplineSelect = false;
      private boolean filteredSelfDHoursSelect = false;
      private boolean filteredLabsCountSelect = false;
      private boolean filteredUserSelect = false;

      @Override
      public void initialize(URL location, ResourceBundle resources) {

            tooltip = new Tooltip();
            mainLabel.textProperty().bind(I18N.createStringBinding("label.main"));

            labelUser.textProperty().bind(I18N.createStringBinding("label.user",
                    "'" + EventHandler.user.getUsername() + "'"));

            searchField.promptTextProperty().bind(I18N.createStringBinding("field.search"));
            filterBox.textProperty().bind(I18N.createStringBinding("field.filter.by"));

            this.filterContextMenu();
            searchField.textProperty().addListener(((observable, oldValue, newValue) -> filtered.setPredicate(lab -> {
                  if (newValue == null || newValue.isEmpty()) return true;
                  String lowerCase = newValue.toLowerCase();
                  if (String.valueOf(lab.getID()).toLowerCase().contains(lowerCase) && filteredIdSelect)
                        return true;
                  if (String.valueOf(lab.getName()).toLowerCase().contains(lowerCase) && filteredNameSelect)
                        return true;
                  if (String.valueOf(lab.getX()).toLowerCase().contains(lowerCase) && filteredXSelect)
                        return true;
                  if (String.valueOf(lab.getY()).toLowerCase().contains(lowerCase) && filteredYSelect)
                        return true;
                  if (String.valueOf(lab.getCreationDate()).toLowerCase().contains(lowerCase) && filteredCrDateSelect)
                        return true;
                  if (String.valueOf(lab.getMinimalPoint()).toLowerCase().contains(lowerCase) && filteredMinPointSelect)
                        return true;
                  if (String.valueOf(lab.getPersonQualitiesMinimum()).toLowerCase().contains(lowerCase) && filteredPerQuaMinSelect)
                        return true;
                  if (String.valueOf(lab.getTunedInWorks()).toLowerCase().contains(lowerCase) && filteredTunedInWorksSelect)
                        return true;
                  if (String.valueOf(lab.getDifficulty()).toLowerCase().contains(lowerCase) && filteredDifficultySelect)
                        return true;
                  if (String.valueOf(lab.getDiscipline()).toLowerCase().contains(lowerCase) && filteredDisciplineSelect)
                        return true;
                  if (String.valueOf(lab.getSelfStudyHours()).toLowerCase().contains(lowerCase) && filteredSelfDHoursSelect)
                        return true;
                  if (String.valueOf(lab.getLabsCount()).toLowerCase().contains(lowerCase) && filteredLabsCountSelect)
                        return true;
                  return String.valueOf(lab.getThisUser()).toLowerCase().contains(lowerCase) && filteredUserSelect;
            })));

            tableTab.textProperty().bind(I18N.createStringBinding("tab.table.sheets"));
            visualTab.textProperty().bind(I18N.createStringBinding("tab.visualization"));

            menuFile.textProperty().bind(I18N.createStringBinding("menu.file"));
            menuSittings.textProperty().bind(I18N.createStringBinding("menu.sittings"));
            menuLanguages.textProperty().bind(I18N.createStringBinding("menu.languages"));

            itemExit.textProperty().bind(I18N.createStringBinding("item.exit"));
            itemOne.textProperty().bind(I18N.createStringBinding("item.one"));
            itemTwo.textProperty().bind(I18N.createStringBinding("item.two"));
            itemThree.textProperty().bind(I18N.createStringBinding("item.three"));
            itemFour.textProperty().bind(I18N.createStringBinding("item.four"));
            itemFive.textProperty().bind(I18N.createStringBinding("item.five"));

            columnID.textProperty().bind(I18N.createStringBinding("column.id"));
            columnName.textProperty().bind(I18N.createStringBinding("column.name"));
            columnX.textProperty().bind(I18N.createStringBinding("column.x"));
            columnY.textProperty().bind(I18N.createStringBinding("column.y"));
            columnCreationDate.textProperty().bind(I18N.createStringBinding("column.creation.date"));
            columnMinPoints.textProperty().bind(I18N.createStringBinding("column.minimal.point"));
            columnPerQualMin.textProperty().bind(I18N.createStringBinding("column.personal.qualities.minimum"));
            columnTunedInWorks.textProperty().bind(I18N.createStringBinding("column.tuned.in.works"));
            columnDifficulty.textProperty().bind(I18N.createStringBinding("column.difficulty"));
            columnDiscipline.textProperty().bind(I18N.createStringBinding("column.discipline"));
            columnSelfStudHours.textProperty().bind(I18N.createStringBinding("column.self.study.hours"));
            columnLabsCount.textProperty().bind(I18N.createStringBinding("column.labs.count"));
            columnUser.textProperty().bind(I18N.createStringBinding("column.user"));

            /* Setting Property Value Factory to each column */
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            columnX.setCellValueFactory(new PropertyValueFactory<>("X"));
            columnY.setCellValueFactory(new PropertyValueFactory<>("Y"));
            columnCreationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
            columnMinPoints.setCellValueFactory(new PropertyValueFactory<>("MinimalPoint"));
            columnPerQualMin.setCellValueFactory(new PropertyValueFactory<>("PersonQualitiesMinimum"));
            columnTunedInWorks.setCellValueFactory(new PropertyValueFactory<>("TunedInWorks"));
            columnDifficulty.setCellValueFactory(new PropertyValueFactory<>("Difficulty"));
            columnDiscipline.setCellValueFactory(new PropertyValueFactory<>("Discipline"));
            columnSelfStudHours.setCellValueFactory(new PropertyValueFactory<>("SelfStudyHours"));
            columnLabsCount.setCellValueFactory(new PropertyValueFactory<>("LabsCount"));
            columnUser.setCellValueFactory(new PropertyValueFactory<>("ThisUser"));

            /*
             * Creating raw menus
             */
            collectionTable.setRowFactory(item -> {
                  TableRow<TableFX> row = new TableRow<>();

                  MenuItem edit = new MenuItem();
                  edit.textProperty().bind(I18N.createStringBinding("item.edit"));

                  edit.setOnAction(event -> {
                        try {
                              FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.fromControllerToAddUpdateFXML));
                              Parent root = loader.load();
                              Scene scene = new Scene(root);
                              AUController controller = loader.getController();
                              controller.setItems(row.getItem());
                              //grab your root here
                              root.setOnMousePressed(event1 -> {
                                    xOffset = event1.getSceneX();
                                    yOffset = event1.getSceneY();
                              });
                              //move around here
                              root.setOnMouseDragged(event12 -> {
                                    AUController.stage.setX(event12.getScreenX() - xOffset);
                                    AUController.stage.setY(event12.getScreenY() - yOffset);
                              });
                              if (AUController.stage.getStyle() != StageStyle.TRANSPARENT)
                                    AUController.stage.initStyle(StageStyle.TRANSPARENT);

                              AUController.stage.setScene(scene);
                              AUController.stage.show();
                        } catch (IOException ignored) {
                        }
                  });

                  MenuItem remove = new MenuItem();
                  remove.textProperty().bind(I18N.createStringBinding("item.remove"));

                  remove.setOnAction(event -> {
                        RemoveByID removeByID = new RemoveByID.Builder().setArgument(row.getItem().getID()).build();
                        EventHandler.getInstance().sentCommandToServer(removeByID, new EventListener() {
                              @Override
                              public void onSuccessful(Object object) {
                              }

                              @Override
                              public void onError() {
                                    Platform.runLater(() -> {
                                          Alert alert = new Alert(Alert.AlertType.ERROR);
                                          alert.setGraphic(null);
                                          alert.setHeaderText("Remove failed!");
                                          alert.setContentText("You don't have permission!");
                                          alert.showAndWait();
                                    });
                              }
                        });
                  });

                  ContextMenu rowMenu = new ContextMenu(edit, remove);
                  row.contextMenuProperty().setValue(rowMenu);

                  return row;
            });

            /* Sending command 'show' to the server */
            EventHandler.getInstance().sentCommandToServer(new Show(), showListener);
      }

      //****************************************************************************//

      private void filterContextMenu() {
            RadioMenuItem itemID = new RadioMenuItem();
            itemID.textProperty().bind(I18N.createStringBinding("column.id"));
            itemID.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredIdSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemName = new RadioMenuItem();
            itemName.textProperty().bind(I18N.createStringBinding("column.name"));
            itemName.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredNameSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemX = new RadioMenuItem();
            itemX.textProperty().bind(I18N.createStringBinding("column.x"));
            itemX.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredXSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemY = new RadioMenuItem();
            itemY.textProperty().bind(I18N.createStringBinding("column.y"));
            itemY.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredYSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemCrDate = new RadioMenuItem();
            itemCrDate.textProperty().bind(I18N.createStringBinding("column.creation.date"));
            itemCrDate.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredCrDateSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemMinPoint = new RadioMenuItem();
            itemMinPoint.textProperty().bind(I18N.createStringBinding("column.minimal.point"));
            itemMinPoint.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredMinPointSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemPerQuaMin = new RadioMenuItem();
            itemPerQuaMin.textProperty().bind(I18N.createStringBinding("column.personal.qualities.minimum"));
            itemPerQuaMin.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredPerQuaMinSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemTunedINWorks = new RadioMenuItem();
            itemTunedINWorks.textProperty().bind(I18N.createStringBinding("column.tuned.in.works"));
            itemTunedINWorks.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredTunedInWorksSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemDiff = new RadioMenuItem();
            itemDiff.textProperty().bind(I18N.createStringBinding("column.difficulty"));
            itemDiff.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredDifficultySelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemDis = new RadioMenuItem();
            itemDis.textProperty().bind(I18N.createStringBinding("column.discipline"));
            itemDis.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredDisciplineSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemSelfStH = new RadioMenuItem();
            itemSelfStH.textProperty().bind(I18N.createStringBinding("column.self.study.hours"));
            itemSelfStH.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredSelfDHoursSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemLabsC = new RadioMenuItem();
            itemLabsC.textProperty().bind(I18N.createStringBinding("column.labs.count"));
            itemLabsC.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredLabsCountSelect = newValue;
                  notifySearchTextField();
            });
            RadioMenuItem itemU = new RadioMenuItem();
            itemU.textProperty().bind(I18N.createStringBinding("column.user"));
            itemU.selectedProperty().addListener((observable, oldValue, newValue)
                    -> {
                  filteredUserSelect = newValue;
                  notifySearchTextField();
            });
            filterBox.getItems().addAll(itemID, itemName, itemX, itemY, itemCrDate, itemMinPoint,
                    itemPerQuaMin, itemTunedINWorks, itemDiff, itemDis, itemSelfStH, itemLabsC, itemU);
      }

      private void notifySearchTextField() {
            String oldValue = searchField.getText();
            searchField.setText("");
            searchField.setText(oldValue);
      }

      //****************************************************************************//

      /* Changing language dynamically */

      @FXML
      public void change2English() {
            I18N.setLocale(new Locale("en_US"));
      }
      @FXML
      public void change2Russian() {
            I18N.setLocale(new Locale("ru_RU"));
      }
      @FXML
      public void change2Belorussian() {
            I18N.setLocale(new Locale("be_BY"));
      }
      @FXML
      public void change2Albanian() {
            I18N.setLocale(new Locale("sq_AL"));
      }
      @FXML
      public void change2Spanish() {
            I18N.setLocale(new Locale("es_CR"));
      }

      //****************************************************************************//

      private double xOffset = 0;
      private double yOffset = 0;

      @FXML
      public void addButtonAction() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.fromControllerToAddUpdateFXML));
            Scene scene = new Scene(root);

            //grab your root here
            root.setOnMousePressed(event -> {
                  xOffset = event.getSceneX();
                  yOffset = event.getSceneY();
            });
            //move around here
            root.setOnMouseDragged(event -> {
                  AUController.stage.setX(event.getScreenX() - xOffset);
                  AUController.stage.setY(event.getScreenY() - yOffset);
            });
            if (AUController.stage.getStyle() != StageStyle.TRANSPARENT)
                  AUController.stage.initStyle(StageStyle.TRANSPARENT);

            AUController.stage.setTitle("Add Item");

            AUController.stage.setScene(scene);
            AUController.stage.show();
      }

      @FXML
      public void deleteAllButtonAction() {
            EventHandler.getInstance().sentCommandToServer(new Clear(), new EventListener() {
                  @Override
                  public void onSuccessful(Object object) {
                        Platform.runLater(() -> {
                              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                              alert.setGraphic(null);
                              alert.setHeaderText("Success");
                              alert.setContentText("Successfully cleared collection");
                              alert.showAndWait();
                        });
                  }

                  @Override
                  public void onError() {
                        Platform.runLater(() -> {
                              Alert alert = new Alert(Alert.AlertType.ERROR);
                              alert.setGraphic(null);
                              alert.setHeaderText("Clear failed!");
                              alert.setContentText("You don't have permission!");
                              alert.showAndWait();
                        });
                  }
            });
      }

      @FXML
      public void infoButtonAction() {
            EventHandler.getInstance().sentCommandToServer(new Info(), new EventListener() {
                  @Override
                  public void onSuccessful(Object object) {
                        Platform.runLater(() -> {
                              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                              alert.setGraphic(null);
                              alert.setHeaderText("Information");
                              alert.setContentText((String) object);
                              alert.showAndWait();
                        });
                  }

                  @Override
                  public void onError() {
                  }
            });
      }

      @FXML
      public void executeButtonAction() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.getFromControllerToExecuteFXML));
            Scene scene = new Scene(root);

            if (EController.stage.getStyle() != StageStyle.TRANSPARENT)
                  EController.stage.initStyle(StageStyle.TRANSPARENT);

            EController.stage.setTitle("Execute script");

            EController.stage.setScene(scene);
            EController.stage.show();
      }


      //*****************************************************************************//
      //*                              Visualization                                *//
      //*****************************************************************************//

      @FXML
      public javafx.scene.layout.Pane visualPane;

      private Tooltip tooltip;

      private final Map<Long, Circle> circleMap = new HashMap<>();

      private List<Laboratory> points = new ArrayList<>();

      /**
       * Updates visual table items
       */
      private void updatePoints(List<Laboratory> labs) {
            List<Long> deletedItems = points.stream().filter(o -> labs.stream().noneMatch(p
                    -> p.getID().equals(o.getID()))).map(Laboratory::getID).collect(Collectors.toList());

            List<Laboratory> addedItems = labs.stream().filter(o -> points.stream().noneMatch(p
                    -> p.getID().equals(o.getID()))).collect(Collectors.toList());

            Map<Long, Laboratory> changedItems = labs.stream().filter(o -> points.stream().allMatch(p
                    -> p.compare(o))).collect(Collectors.toMap(Laboratory::getID, l -> l));

            deletedItems.forEach(this::deletedItem);
            addedItems.forEach(this::addItem);
            changedItems.forEach(this::changeItem);
            points = labs;
      }

      private void changeItem(Long ID, Laboratory laboratory) {
            double radius = 0;
            switch (laboratory.getDifficulty()){
                  case VERY_EASY: radius = 3.0; break;
                  case NORMAL: radius = 6.0; break;
                  case HARD: radius = 9.0; break;
                  case IMPOSSIBLE: radius = 12.0; break;
                  case TERRIBLE: radius = 15;
            }

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            double x = Math.abs(laboratory.getCoordinates().getX())
                       % (bounds.getWidth() / 2) + (bounds.getWidth() / 10);
            double y = Math.abs(laboratory.getCoordinates().getY())
                       % (bounds.getHeight()/ 2) + (bounds.getHeight() / 10);

            Circle circle = circleMap.get(ID);
            if(circle != null) {
                  /* Animation */
                  circle.setUserData(laboratory);
                  Timeline timeline = new Timeline();
                  Duration duration = Duration.millis(2000);
                  KeyValue keyRadius = new KeyValue(circle.radiusProperty(), radius);
                  KeyValue keyX = new KeyValue(circle.centerXProperty(), x);
                  KeyValue keyY = new KeyValue(circle.centerYProperty(), y);
                  KeyFrame keyFrame = new KeyFrame(duration, keyRadius);
                  KeyFrame keyFrame1 = new KeyFrame(duration, keyX, keyY);

                  timeline.getKeyFrames().addAll(keyFrame, keyFrame1);
                  timeline.play();


                  circleMap.put(ID, circle);

            }
      }

      /**
       * Adds new point to the visual table field
       */
      private void addItem(Laboratory laboratory) {
            double radius = 0;
            switch (laboratory.getDifficulty()){
                  case VERY_EASY: radius = 3.0; break;
                  case NORMAL: radius = 6.0; break;
                  case HARD: radius = 9.0; break;
                  case IMPOSSIBLE: radius = 12.0; break;
                  case TERRIBLE: radius = 15;
            }

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            double x = Math.abs(laboratory.getCoordinates().getX())
                       % (bounds.getWidth() / 2) + (bounds.getWidth() / 10);
            double y = Math.abs(laboratory.getCoordinates().getY())
                       % (bounds.getHeight()/ 2) + (bounds.getHeight() / 10);

            int red = laboratory.getThisUser().hashCode() * 3 % 255;
            int green = laboratory.getThisUser().hashCode() * 6 % 255;
            int blue = laboratory.getThisUser().hashCode() * 9 % 255;

            Circle circle = new Circle(x, y, radius, Color.rgb(Math.abs(red), Math.abs(green), Math.abs(blue)));
            circle.setCursor(Cursor.HAND);
            circle.setUserData(laboratory);

            circle.setOnMouseEntered(event -> {
                  Circle self = (Circle) event.getSource();
                  Laboratory lab = (Laboratory) self.getUserData();

                  double eventX = event.getX();
                  double eventY = event.getY();
                  double tooltipX = eventX + self.getScene().getX() + self.getScene().getWindow().getX();
                  double tooltipY = eventY + self.getScene().getY() + 10 + self.getScene().getWindow().getY();

                  tooltip.setText(lab.getName());
                  tooltip.show(circle, tooltipX, tooltipY);
            });

            circle.setOnMouseExited(event -> tooltip.hide());

            circle.setOnMouseClicked(event -> {
                  if (event.getButton() == MouseButton.PRIMARY
                      || event.getButton() == MouseButton.SECONDARY) {
                        Circle o = (Circle) event.getSource();
                        Laboratory lab = (Laboratory) o.getUserData();
                        TableFX fx = new TableFX();
                        fx.setID(lab.getID());
                        fx.setName(lab.getName());
                        fx.setX(lab.getCoordinates().getX());
                        fx.setY(lab.getCoordinates().getY());
                        fx.setCreationDate(lab.getCreationDate());
                        fx.setMinimalPoint(lab.getMinimalPoint());
                        fx.setPersonQualitiesMinimum(lab.getPerQualityMin());
                        fx.setTunedInWorks(lab.getTunedInWorks());
                        fx.setDifficulty(lab.getDifficulty().toString());
                        fx.setDiscipline(lab.getDiscipline().getDisName());
                        fx.setSelfStudyHours(lab.getDiscipline().getSelfStudyHours());
                        fx.setLabsCount(lab.getDiscipline().getLabsCount());
                        fx.setThisUser(lab.getThisUser());

                        try {
                              FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.fromControllerToAddUpdateFXML));
                              Parent root = loader.load();
                              Scene scene = new Scene(root);
                              AUController controller = loader.getController();
                              controller.setItems(fx);
                              //grab your root here
                              root.setOnMousePressed(event1 -> {
                                    xOffset = event1.getSceneX();
                                    yOffset = event1.getSceneY();
                              });
                              //move around here
                              root.setOnMouseDragged(event12 -> {
                                    AUController.stage.setX(event12.getScreenX() - xOffset);
                                    AUController.stage.setY(event12.getScreenY() - yOffset);
                              });
                              if (AUController.stage.getStyle() != StageStyle.TRANSPARENT)
                                    AUController.stage.initStyle(StageStyle.TRANSPARENT);

                              AUController.stage.setScene(scene);
                              AUController.stage.show();
                        } catch (IOException ignored) {
                        }
                  }
            });

            visualPane.getChildren().add(circle);
            circleMap.put(laboratory.getID(), circle);
      }

      /**
       * Deletes point from visual table field
       */
      private void deletedItem(Long ID) {
            visualPane.getChildren().remove(circleMap.get(ID));
            circleMap.remove(ID);
      }
}