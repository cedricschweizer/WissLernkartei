package classes;
import java.sql.*;

public class Database {

    static String url = "jdbc:sqlite:"+System.getenv("homepath")+"/db.db";


    public static void connect() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(url);

            System.out.println("Connection succeded!");
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

    public static void createTable() {

        String sql = "Create table if not exists WLK (\n"
                + "id integer primary key autoincrement,\n"
                + "vorderseite nvarchar(200),\n"
                + "hinterseite nvarchar(200),\n)"
                + "bild nvarchar(200),\n"
                + "fach nvarchar(100),\n"
                + "kategorie nvarchar(200));";

        System.out.println("Tabelle erfolgreich erstellt!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String vds, String hs, String img, String fach, String kat) {

        String sql = "INSERT INTO WLK(vorderseite, hinterseite, bild, fach, kategorie)\n"
                + "VALUES ('"+vds+"', '"+hs+"','"+img+"','"+fach+"','"+kat+"');";

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void select(String sql) {

        try (Connection connection = connection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

            /*while (rs.next()){
                System.out.println(rs.getInt("id")+"\t"+
                                                        rs.getString("tescht"));
            }*/

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }

}
