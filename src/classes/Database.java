package classes;
import java.sql.*;

public class Database {

    String url = "jdbc:sqlite:"+System.getenv("homepath")+"/WissLK.db";


    public void connect() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(url);

            System.out.println("Connection succeded! | Created database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void createTable() {

        String sql = "Create table if not exists WLK ( \n"
                + "id integer primary key autoincrement,\n"
                + "vorderseite nvarchar(200),\n"
                + "hinterseite nvarchar(200),\n"
                + "bild nvarchar(200),\n"
                + "fach nvarchar(100),\n"
                + "kategorie nvarchar(200)"
                + ");";

        System.out.println("Table WLK successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableFK() {

        String sql = "Create table if not exists FK ( \n"
                + "id integer primary key autoincrement,\n"
                + "fach nvarchar(200),\n"
                + "kategorie nvarchar(200)\n"
                + ");";

        System.out.println("Table FK successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String vds, String hs, String img, String fach, String kat) {

        String sql = "INSERT INTO WLK(vorderseite, hinterseite, bild, fach, kategorie)\n"
                + "VALUES ('"+vds+"', '"+hs+"','"+img+"','"+fach+"','"+kat+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFK(String fach, String kat) {

        String sql = "INSERT INTO FK(fach, kategorie)\n"
                + "VALUES ('"+fach+"', '"+kat+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDatabase() {
        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute("drop database if exists WissLK;");
            System.out.println("Successfully dropped database!");
        } catch (SQLException e) {
            System.out.println("Es ist ein Fehler aufgetreten! Bitte kontaktieren Sie Ihre Eltern!");
            System.out.println(e.getMessage());
        }
    }

    private Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public ResultSet select(String sql) {
        ResultSet rs;
        try {
            Connection connection = connection();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
           System.out.println(e.getMessage());
           rs=null;
        }
        return rs;
    }

}
