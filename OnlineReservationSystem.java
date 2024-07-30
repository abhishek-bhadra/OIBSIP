import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservationSystem {

    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Reservation> reservations = new HashMap<>();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initUsers();
        showLoginScreen();
    }

    private static void initUsers() {
        // Adding some demo users
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
    }

    private static void showLoginScreen() {
        System.out.println("Welcome to Online Reservation System");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            currentUser = users.get(username);
            showMainMenu();
        } else {
            System.out.println("Invalid username or password. Try again.");
            showLoginScreen();
        }
    }

    private static boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    private static void showMainMenu() {
        System.out.println("1. Make a Reservation");
        System.out.println("2. Cancel a Reservation");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                makeReservation();
                break;
            case 2:
                cancelReservation();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                showMainMenu();
        }
    }

    private static void makeReservation() {
        System.out.println("Make a Reservation");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Train Name: ");
        String trainName = scanner.nextLine();
        System.out.print("Enter Class Type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter Date of Journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter From (Place): ");
        String from = scanner.nextLine();
        System.out.print("Enter To (Destination): ");
        String to = scanner.nextLine();

        String pnr = generatePNR();
        Reservation reservation = new Reservation(pnr, name, trainNumber, trainName, classType, dateOfJourney, from, to);
        reservations.put(pnr, reservation);

        System.out.println("Reservation made successfully. Your PNR: " + pnr);
        showMainMenu();
    }

    private static void cancelReservation() {
        System.out.print("Enter PNR Number: ");
        String pnr = scanner.nextLine();

        Reservation reservation = reservations.get(pnr);
        if (reservation != null) {
            System.out.println("Reservation found: " + reservation);
            System.out.print("Confirm cancellation? (yes/no): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                reservations.remove(pnr);
                System.out.println("Reservation cancelled successfully.");
            }
        } else {
            System.out.println("No reservation found with PNR: " + pnr);
        }
        showMainMenu();
    }

    private static String generatePNR() {
        return "PNR" + (reservations.size() + 1);
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
        showLoginScreen();
    }

    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class Reservation {
        private String pnr;
        private String name;
        private String trainNumber;
        private String trainName;
        private String classType;
        private String dateOfJourney;
        private String from;
        private String to;

        public Reservation(String pnr, String name, String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
            this.pnr = pnr;
            this.name = name;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Reservation{" +
                    "PNR='" + pnr + '\'' +
                    ", Name='" + name + '\'' +
                    ", Train Number='" + trainNumber + '\'' +
                    ", Train Name='" + trainName + '\'' +
                    ", Class Type='" + classType + '\'' +
                    ", Date of Journey='" + dateOfJourney + '\'' +
                    ", From='" + from + '\'' +
                    ", To='" + to + '\'' +
                    '}';
        }
    }
}
