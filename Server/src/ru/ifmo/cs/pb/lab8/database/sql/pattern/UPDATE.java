package ru.ifmo.cs.pb.lab8.database.sql.pattern;

public abstract class UPDATE {

      public static final String LABORATORY = "UPDATE LABORATORY SET NAME=?, MINIMAL_POINT=?, PER_QUALITY_MIN=?, TUNED_IN_WORKS=?, DIFFICULTY=? WHERE ID=?";

      public static final String COORDINATES = "UPDATE COORDINATES SET X=?, Y=? WHERE LAB_ID=?";

      public static final String DISCIPLINE = "UPDATE DISCIPLINE SET DIS_NAME=?, SELF_STUDY_HOURS=?, LABS_COUNT=? WHERE LAB_ID=?";
}
