package ru.ifmo.cs.pb.lab8.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public final class TableFX {

      private LongProperty ID = new SimpleLongProperty();

      public long getID() { return ID.get(); }

      public LongProperty IDProperty() { return ID; }

      public void setID(long ID) { this.ID.set(ID); }


      private StringProperty name = new SimpleStringProperty();

      public String getName() { return name.get(); }

      public StringProperty nameProperty() { return name; }

      public void setName(String name) { this.name.set(name); }


      private LongProperty x = new SimpleLongProperty();

      public long getX() { return x.get(); }

      public LongProperty xProperty() { return x; }

      public void setX(long x) { this.x.set(x); }


      private IntegerProperty y = new SimpleIntegerProperty();

      public int getY() { return y.get(); }

      public IntegerProperty yProperty() { return y; }

      public void setY(int y) { this.y.set(y); }


      private ObjectProperty<LocalDate> creationDate = new SimpleObjectProperty<>();

      public LocalDate getCreationDate() { return creationDate.get(); }

      public ObjectProperty<LocalDate> creationDateProperty() { return creationDate; }

      public void setCreationDate(LocalDate creationDate) { this.creationDate.set(creationDate); }


      private FloatProperty minimalPoint = new SimpleFloatProperty();

      public float getMinimalPoint() { return minimalPoint.get(); }

      public FloatProperty minimalPointProperty() { return minimalPoint; }

      public void setMinimalPoint(float minimalPoint) { this.minimalPoint.set(minimalPoint); }


      private DoubleProperty personQualitiesMinimum = new SimpleDoubleProperty();

      public double getPersonQualitiesMinimum() { return personQualitiesMinimum.get(); }

      public DoubleProperty personQualitiesMinimumProperty() { return personQualitiesMinimum; }

      public void setPersonQualitiesMinimum(double personQualitiesMinimum) {
            this.personQualitiesMinimum.set(personQualitiesMinimum);
      }

      private IntegerProperty tunedInWorks = new SimpleIntegerProperty();

      public int getTunedInWorks() { return tunedInWorks.get(); }

      public IntegerProperty tunedInWorksProperty() { return tunedInWorks; }

      public void setTunedInWorks(int tunedInWorks) { this.tunedInWorks.set(tunedInWorks); }


      private StringProperty difficulty = new SimpleStringProperty();

      public String getDifficulty() { return difficulty.get(); }

      public StringProperty difficultyProperty() { return difficulty; }

      public void setDifficulty(String difficulty) { this.difficulty.set(difficulty); }


      private StringProperty discipline = new SimpleStringProperty();

      public String getDiscipline() { return discipline.get(); }

      public StringProperty disciplineProperty() { return discipline; }

      public void setDiscipline(String discipline) { this.discipline.set(discipline); }


      private IntegerProperty selfStudyHours = new SimpleIntegerProperty();

      public int getSelfStudyHours() { return selfStudyHours.get(); }

      public IntegerProperty selfStudyHoursProperty() { return selfStudyHours; }

      public void setSelfStudyHours(int selfStudyHours) { this.selfStudyHours.set(selfStudyHours); }


      private LongProperty labsCount = new SimpleLongProperty();

      public long getLabsCount() { return labsCount.get(); }

      public LongProperty labsCountProperty() { return labsCount; }

      public void setLabsCount(long labsCount) { this.labsCount.set(labsCount); }


      private StringProperty thisUser = new SimpleStringProperty();

      public String getThisUser() { return thisUser.get(); }

      public StringProperty thisUserProperty() { return thisUser; }

      public void setThisUser(String thisUser) { this.thisUser.set(thisUser); }
}
