import java.sql.*;

public class ResultService {


    private static String DB_USER = "user";
    private static String DB_PASSWORD = "password";

    private static String DB_HOST = "db";
    private static String DB_PORT = "3308";
    private static String DB_NAME = "calc_data2";

    // Load MariaDB driver
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getDatabaseHost() {
        String host = System.getenv("DB_HOST");

        if (host == null || host.isEmpty()) {
            // use Docker service name
            DB_HOST = "db";
            return;
        } else {
            DB_PORT = System.getenv("DB_PORT");
            DB_USER = System.getenv("DB_USER");
            DB_PASSWORD = System.getenv("DB_PASSWORD");
            DB_NAME = System.getenv("DB_NAME");
        }


    }

    private static String getDatabaseUrl() {  //port 3306 for localhost
        getDatabaseHost();

        return "jdbc:mariadb://" + DB_HOST + ":" + DB_PORT  + "/" + DB_NAME

                +"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public static void saveResult(double n1, double n2, double sum, double product, double subtract, double divide) {

        String dbUrl = getDatabaseUrl();
        System.out.println("URL:" +dbUrl);
        System.out.println("Envs:" +DB_USER + " " + DB_PASSWORD + " " + DB_HOST + " " + DB_PORT + " " + DB_NAME);

        try (Connection conn = DriverManager.getConnection(dbUrl, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create table if it doesn't exist
            String createTable = """
                CREATE TABLE IF NOT EXISTS calc_results (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    number1 DOUBLE NOT NULL,
                    number2 DOUBLE NOT NULL,
                    sum_result DOUBLE NOT NULL,
                    product_result DOUBLE NOT NULL,
                    subtract_result DOUBLE NOT NULL,
                    divide_result DOUBLE NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.executeUpdate(createTable);

            // Insert the result
            String insert = "INSERT INTO calc_results (number1, number2, sum_result, product_result,subtract_result,divide_result) VALUES (?, ?, ?, ?, ? ,?)";
            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setDouble(1, n1);
                ps.setDouble(2, n2);
                ps.setDouble(3, sum);
                ps.setDouble(4, product);
                ps.setDouble(5, subtract);
                ps.setDouble(6, divide);
                ps.executeUpdate();

            }

            System.out.println("✅ Result saved: " + n1 + ", " + n2 + " → Sum=" + sum + ", Product=" + product);

        } catch (SQLException e) {
            System.err.println("❌ Failed to save result to DB: " + dbUrl);
            e.printStackTrace();
        }
    }
}