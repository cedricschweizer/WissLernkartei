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
                + "kategorie nvarchar(200),\n"
                + "stack nvarchar(10),\n"
                + "time timestamp \n"
                + ");";

        System.out.println("Table WLK successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableF() {

        String sql = "Create table if not exists fach ( \n"
                + "id integer primary key autoincrement,\n"
                + "fach nvarchar(200)\n"
                + ");";

        System.out.println("Table fach successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableK() {

        String sql = "Create table if not exists kat ( \n"
                + "id integer primary key autoincrement,\n"
                + "kategorie nvarchar(200)\n"
                + ");";

        System.out.println("Table kat successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableTmp() {

        String sql = "Create table if not exists tmp ( \n"
                + "id integer primary key autoincrement,\n"
                + "tmpVS nvarchar(200),\n"
                + "tmpRS nvarchar(200),\n"
                + "tmpPath nvarchar(200)"
                + ");";

        System.out.println("Table tmp successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createSuperSafetyTabulettteee() {

        String sql = "Create table if not exists superSafetyTabulettteee ( \n"
                + "id integer primary key autoincrement,\n"
                + "password nvarchar(1000000)"
                + ");";

        System.out.println("Table tmp successfully created!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertSST(String password) {

        String sql = "INSERT INTO superSafetyTabulettteee(password)\n"
                + "VALUES ('"+password+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String vds, String hs, String img, String fach, String kat, String schtäck, Timestamp time) {

        String sql = "INSERT INTO WLK(vorderseite, hinterseite, bild, fach, kategorie, stack, time)\n"
                + "VALUES ('"+vds+"', '"+hs+"','"+img+"','"+fach+"','"+kat+"','"+schtäck+"', '"+time+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertF(String fach) {

        String sql = "INSERT INTO fach(fach)\n"
                + "VALUES ('"+fach+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertK(String kat) {

        String sql = "INSERT INTO kat(kategorie)\n"
                + "VALUES ('"+kat+"');";

        System.out.println("Successfully inserted data in database!");

        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCards(String update) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.execute(update);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Card cawdu) {
        String sql = "UPDATE WLK set stack = "+cawdu.getStack()+" where vorderseite like '"+cawdu.getKey()+"' and " +
                "hinterseite like '"+cawdu.getVal()+"' and fach like '"+cawdu.getFach()+"' and kategorie like '"+cawdu.getKategorie()+"';";

        System.out.println("Successfully updated kkard with se paranormalmeter "+cawdu.getKey()+cawdu.getVal()+cawdu.getFach()+cawdu.getKategorie());

        try (Connection connection = DriverManager.getConnection(url);
            Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void deleteDatabase() {
        ResultSet rs = select("Select * from WLK");
        System.out.println(rs.toString());
        String delDB = "DETACH DATABASE WissLK;";
        try (Connection connection = connection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(delDB);
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
