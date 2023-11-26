package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Package declaration and imports

public class DatabaseActivityLogger {
    private static Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abchome_3", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // Assume you have a valid database connection

    // Methods for logging database activities
    public static void logInsertActivity(String tableName, int recordId) {
        logActivity("Insert", tableName, recordId);
    }
    public static void logUpdateActivity(String tableName, int recordId) {
        logActivity("Update", tableName, recordId);
    }
    public static void logRemoveActivity(String tableName, int recordId) {
        logActivity("Remove", tableName, recordId);
    }

    private static void logActivity(String activityType, String tableName, int recordId) {
        String sql = "INSERT INTO DatabaseActivityLog (activityType, tableName, recordID, dateCreated, lastUpdated) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, activityType);
            statement.setString(2, tableName);
            statement.setInt(3, recordId);
            statement.setObject(4, LocalDateTime.now());
            statement.setObject(5, LocalDateTime.now());

            statement.executeUpdate();
            System.out.println("Product \""+tableName+activityType+"\" activity insert to the DatabaseActivityLogger successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }


}
