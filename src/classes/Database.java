package classes;
import java.sql.*;

public class Database {

    public static void main(String[] args) {connect(); createTable(); insert(); select();}

        public static void connect() {
            Connection connection = null;
            try {
                String url = "jdbc:sqlite:C:/Users/Developer/Desktop/tescht.db";

                connection = DriverManager.getConnection(url);

                System.out.println("sdklajfapidshfihdspifdsio BRAVO!!!");
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

            String url = "jdbc:sqlite:C:/Users/Developer/Desktop/tescht.db";

            String sql = "Create table if not exists testtable (\n"
                    + "id integer Primary Key,\n"
                    + "tescht nvarchar(20)\n"
                    + ");";

            try (Connection connection = DriverManager.getConnection(url);
                 Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public static void insert() {

        String url = "jdbc:sqlite:C:/Users/Developer/Desktop/tescht.db";

        String sql = "INSERT INTO testtable(tescht)\n"
                + "VALUES ('tescht');";

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connection() {
        String url = "jdbc:sqlite:C:/Users/Developer/Desktop/tescht.db";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void select() {

        String sql = "SELECT * FROM  testtable;";

        try (Connection connection = connection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                System.out.println(rs.getInt("id")+"\t"+
                                                        rs.getString("tescht"));
            }

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }

    }
