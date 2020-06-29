package ru.ifmo.cs.pb.lab8.object;

import java.io.Serializable;
import java.time.LocalDate;


public class Laboratory implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 2222222222222222222L;

      private Long ID;

      public Long getID() { return ID; }

      public void setID(Long ID) {
            this.ID = ID;
      }


      private String name;

      public String getName() { return name; }

      public void setName(String name) {
            this.name = name;
      }


      private Coordinates coordinates;

      public Coordinates getCoordinates() { return coordinates; }

      public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
      }


      private LocalDate creationDate;

      public LocalDate getCreationDate() { return creationDate; }

      public void setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
      }


      private Float minimalPoint;

      public Float getMinimalPoint() { return minimalPoint; }

      public void setMinimalPoint(Float minimalPoint) {
            this.minimalPoint = minimalPoint;
      }


      private Double perQualityMin;

      public Double getPerQualityMin() { return perQualityMin; }

      public void setPerQualityMin(Double perQualityMin) {
            this.perQualityMin = perQualityMin;
      }


      private Integer tunedInWorks;

      public Integer getTunedInWorks() { return tunedInWorks; }

      public void setTunedInWorks(Integer tunedInWorks) {
            this.tunedInWorks = tunedInWorks;
      }


      private Difficulty difficulty;

      public Difficulty getDifficulty() { return difficulty; }

      public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
      }


      private Discipline discipline;

      public Discipline getDiscipline() { return discipline; }

      public void setDiscipline(Discipline discipline) {
            this.discipline = discipline;
      }


      private String thisUser;

      public String getThisUser() { return thisUser; }

      public void setThisUser(String thisUser) {
            this.thisUser = thisUser;
      }

      public Laboratory() {
            this.creationDate = LocalDate.now();
      }
}
