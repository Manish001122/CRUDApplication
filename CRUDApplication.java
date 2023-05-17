import java.sql.*;

public class CRUDApplication {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create a new record
            createRecord(connection, "John Doe", 25);

            // Read records
            readRecords(connection);

            // Update a record
            updateRecord(connection, 1, "Jane Smith", 30);

            // Read records again to see the changes
            readRecords(connection);

            // Delete a record
            deleteRecord(connection, 2);

            // Read records again to see the changes
            readRecords(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Create a new record
    private static void createRecord(Connection connection, String name, int age) throws SQLException {
        String query = "INSERT INTO users (name, age) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            System.out.println("Record created successfully");
        }
    }

    // Read all records
    private static void readRecords(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        }
    }

    // Update a record
    private static void updateRecord(Connection connection, int id, String name, int age) throws SQLException {
        String query = "UPDATE users SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setInt(3, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record updated successfully");
            } else {
                System.out.println("Record not found");
            }
        }
    }

    // Delete a record
    private static void deleteRecord(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully");
            } else {
                System.out.println("Record not found");
            }
        }
    }
}
