import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static Connection db = null;

    public static void main(String[] args) {
        openDatabase("Coursework database.db");
// code to get data from, write to the database etc goes here!
        closeDatabase();

        openDatabase("Coursework database.db");

        try {

            PreparedStatement ps = db.prepareStatement("SELECT UserID, UserName, UserProgress FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int UserID = results.getInt(1);
                String UserName = results.getString(2);
                int UserProgress = results.getInt(3);
                System.out.println(UserID + " " + UserName + " " + UserProgress);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

        closeDatabase();


    }

    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

}