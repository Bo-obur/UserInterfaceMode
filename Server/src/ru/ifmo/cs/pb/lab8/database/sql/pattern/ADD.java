package ru.ifmo.cs.pb.lab8.database.sql.pattern;

public abstract class ADD {

      public static final String NEW_USER = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES(?, ?)";

      public static final String NEW_LAB  = "INSERT INTO LABORATORY(NAME, CREATION_DATE, MINIMAL_POINT, PER_QUALITY_MIN, TUNED_IN_WORKS, DIFFICULTY, THIS_USER) VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING ID";

      public static final String NEW_COOR = "INSERT INTO COORDINATES(X, Y, LAB_ID) VALUES(?, ?, ?)";

      public static final String NEW_DIS = "INSERT INTO DISCIPLINE(DIS_NAME, SELF_STUDY_HOURS, LABS_COUNT, LAB_ID) VALUES(?, ?, ?, ?)";
}