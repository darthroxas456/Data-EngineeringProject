package edu.fgcu.dataengineering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// just to check if these are found

public class BookStoreDB {
  //Declare Database URL
  static final String DB_URL = "jdbc:H2:C:\\Users\\owenr\\OneDrive - Florida Gulf Coast University\\Data-EngineeringProject\\res\\H2";

  //Declare Connection
  static Connection conn = null;

  public static void dbConnect() {
    try {
      //Create a connection to the database.
      conn = DriverManager.getConnection("jdbc:H2:C:\\Users\\owenr\\OneDrive - Florida Gulf Coast University\\Data-EngineeringProject\\res\\H2");

      System.out.println("Connection to H2 has been established.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}
