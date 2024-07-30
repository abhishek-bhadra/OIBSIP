import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExaminationSystem {

    private static final int EXAM_DURATION = 60000; 
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean examInProgress = false;

    public static void main(String[] args) {
        initUsers();
        showLoginScreen();
    }

    private static void initUsers() {
        
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
    }

    private static void showLoginScreen() {
        System.out.println("Welcome to Online Examination System");
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
        System.out.println("1. Update Profile and Password");
        System.out.println("2. Start Exam");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                updateProfile();
                break;
            case 2:
                startExam();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                showMainMenu();
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password updated successfully.");
        showMainMenu();
    }

    private static void startExam() {
        System.out.println("Exam started. You have 1 minute.");
        examInProgress = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (examInProgress) {
                    autoSubmit();
                }
            }
        }, EXAM_DURATION);

        
        System.out.println("Question 1: What is the full form of RAM");
        System.out.println("1. Random Access Memory");
        System.out.println("2. Read Only Memory");
        System.out.println("3. Both");
        System.out.println("4. None");
        System.out.print("Enter your answer (1-4): ");
        String answer = scanner.nextLine();

       
        System.out.println("Your answer: " + answer);

        System.out.println("Submit and finish exam? (yes/no): ");
        String submit = scanner.nextLine();
        if (submit.equalsIgnoreCase("yes")) {
            autoSubmit();
        }
    }

    private static void autoSubmit() {
        examInProgress = false;
        System.out.println("Exam submitted automatically.");
        showMainMenu();
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
}