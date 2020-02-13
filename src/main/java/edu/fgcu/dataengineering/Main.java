package edu.fgcu.dataengineering;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {
    // Literally just calls our parser right now (....and is called for tests)
    /*
     * Iteration 1 Below
     */
    CsvParser csvP = new CsvParser("src/Data/bookstore_report2.csv");
    csvP.printCsv();

    // Load the json
    /*
    1. Create instance of GSON
    2. Create a JsonReader object using FileReader
    3. Array of class instances of AuthorParser, assign data from our JsonReader
    4. foreach loop to check data
     */
    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    for (var element : authors) {
      System.out.println(element.getName());
    }

    /*
     * Iteration 2 Below
     */
    Connection conn = null;

    JsonParser jsonParser = new JsonParser();

    try {
      // Parsing contents of the authors file
      JsonObject jsonObject =
          (JsonObject) jsonParser.parse(new FileReader("src/Data/authors.json"));

      // gets the array
        JsonArray jsonArray = jsonObject.getAsJsonArray("authors");

      // connects to Database
      BookStoreDB.dbConnect();

      // make a new row in the Table
      PreparedStatement pstmt = conn.prepareStatement("Insert INTO author VALUES (?,?,?)");

      for (Object obj : jsonArray) {
        JsonObject rec = (JsonObject) obj;
        String authorName = rec.get("Author_name").toString();
        String authorEmail = rec.get("Author_email").toString();
        String authorURL = rec.get("Author_URL").toString();
        pstmt.setString(1, authorName);
        pstmt.setString(2, authorEmail);
        pstmt.setString(3, authorURL);
        pstmt.executeUpdate();
      }
      System.out.println("the DB has been read and copied to the Json File");
    } catch (FileNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
