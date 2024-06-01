package pl.pzwebdev.listconverter;

import pl.pzwebdev.listconverter.database.entity.Branch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnect {

    private static final String DATABASE_URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            if (conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS branches (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " name text NOT NULL,\n"
                + " number text NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Table 'branches' created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertData(String name, String number) {
        String sql = "INSERT INTO branches(name, number) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static Branch findById(int id) {
        String sql = "SELECT * FROM branches WHERE id = ?";
        Branch branch = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("number")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return branch;
    }

    public static List<Branch> findAll() {
        String sql = "SELECT * FROM branches";
        List<Branch> branches = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("number")
                );
                branches.add(branch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return branches;
    }
}
