
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ReservationSystem {

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Load Oracle Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Connect to Oracle DB
            con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "newpassword"
            );

            int choice;

            do {
                System.out.println("\n===== Reservation System =====");
                System.out.println("1. View Trains");
                System.out.println("2. Insert Train");
                System.out.println("3. Delete Train");
                System.out.println("4. View Buses");
                System.out.println("5. Insert Bus");
                System.out.println("6. Delete Bus");
                System.out.println("7. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1: viewTrains(); break;
                    case 2: insertTrain(); break;
                    case 3: deleteTrain(); break;
                    case 4: viewBuses(); break;
                    case 5: insertBus(); break;
                    case 6: deleteBus(); break;
                    case 7: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid Choice!");
                }

            } while (choice != 7);

            con.close();
            sc.close();

        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // ================= TRAIN METHODS =================

    static void viewTrains() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM trains");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "Train ID: " + rs.getInt(1) +
                    ", Name: " + rs.getString(2) +
                    ", Source: " + rs.getString(3) +
                    ", Destination: " + rs.getString(4)
                );
            }

            if (!found) {
                System.out.println("No Train Records Found!");
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error fetching trains: " + e.getMessage());
        }
    }

    static void insertTrain() {
        try {
            System.out.print("Enter Train ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Train Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Source: ");
            String source = sc.nextLine();

            System.out.print("Enter Destination: ");
            String dest = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO trains VALUES (?, ?, ?, ?)");

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, source);
            ps.setString(4, dest);

            ps.executeUpdate();
            System.out.println("Train Inserted Successfully!");

            ps.close();

        } catch (Exception e) {
            System.out.println("Error inserting train: " + e.getMessage());
        }
    }

    static void deleteTrain() {
        try {
            System.out.print("Enter Train ID to delete: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM trains WHERE train_id=?");

            ps.setInt(1, id);   // ✅ FIXED ERROR

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Train Deleted Successfully!");
            else
                System.out.println("Train ID Not Found!");

            ps.close();

        } catch (Exception e) {
            System.out.println("Error deleting train: " + e.getMessage());
        }
    }

    // ================= BUS METHODS =================

    static void viewBuses() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM buses");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "Bus ID: " + rs.getInt(1) +
                    ", Name: " + rs.getString(2) +
                    ", Source: " + rs.getString(3) +
                    ", Destination: " + rs.getString(4)
                );
            }

            if (!found) {
                System.out.println("No Bus Records Found!");
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error fetching buses: " + e.getMessage());
        }
    }

    static void insertBus() {
        try {
            System.out.print("Enter Bus ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Bus Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Source: ");
            String source = sc.nextLine();

            System.out.print("Enter Destination: ");
            String dest = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO buses VALUES (?, ?, ?, ?)");

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, source);
            ps.setString(4, dest);

            ps.executeUpdate();
            System.out.println("Bus Inserted Successfully!");

            ps.close();

        } catch (Exception e) {
            System.out.println("Error inserting bus: " + e.getMessage());
        }
    }

    static void deleteBus() {
        try {
            System.out.print("Enter Bus ID to delete: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM buses WHERE bus_id=?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Bus Deleted Successfully!");
            else
                System.out.println("Bus ID Not Found!");

            ps.close();

        } catch (Exception e) {
            System.out.println("Error deleting bus: " + e.getMessage());
        }
    }
}
