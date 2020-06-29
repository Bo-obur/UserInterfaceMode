package ru.ifmo.cs.pb.lab8.object;

import java.io.Serializable;

public class Discipline implements Serializable {

      /* Serial version Unique Identifier */
      private static final long serialVersionUID = 2222222222200000000L;

      private String disName;

      public String getDisName() {
            return disName;
      }

      public void setDisName(String disName) {
            this.disName = disName;
      }

      private Integer selfStudyHours;

      public Integer getSelfStudyHours() {
            return selfStudyHours;
      }

      public void setSelfStudyHours(Integer selfStudyHours) {
            this.selfStudyHours = selfStudyHours;
      }

      private Long labsCount;

      public Long getLabsCount() {
            return labsCount;
      }

      public void setLabsCount(Long labsCount) {
            this.labsCount = labsCount;
      }
}
