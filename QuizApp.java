import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class to represent user profiles
class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public void updateProfile(String newProfile) {
        this.profile = newProfile;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

// Quiz class to represent MCQ questions
class Quiz {
    private Map<String, String> questions;

    public Quiz() {
        questions = new HashMap<>();
        // Add some sample questions
        questions.put("1", "What is the capital of France?");
        questions.put("2", "Which planet is known as the Red Planet?");
        // Add corresponding correct answers
        questions.put("1_answer", "Paris");
        questions.put("2_answer", "Mars");
    }

    public String getQuestion(String questionNumber) {
        return questions.get(questionNumber);
    }

    public boolean isAnswerCorrect(String questionNumber, String userAnswer) {
        String correctAnswer = questions.get(questionNumber + "_answer");
        return correctAnswer != null && correctAnswer.equalsIgnoreCase(userAnswer);
    }

    // Additional method to get all questions
    public Map<String, String> getQuestions() {
        return questions;
    }
}

// Main application class
public class QuizApp {
    private Map<String, User> users;
    private Quiz quiz;

    public QuizApp() {
        users = new HashMap<>();
        quiz = new Quiz();
        // Add a sample user
        users.put("Qwerty", new User("Qwerty", "password123", "Student"));
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Login");
        System.out.println("2. Create a new profile");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                performLogin();
                break;
            case 2:
                createNewProfile();
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }
    }
    private void performLogin() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter username: "); 
        String username = scanner.nextLine();
    
        // Check if the user exists
        if (users.containsKey(username)) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            User user = users.get(username);
    
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                // Additional logic for handling logged-in user
                handleLoggedInUser(user);
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } else {
            System.out.println("Login failed. User not found.");
        }
    }

    private void createNewProfile() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a new username: ");
        String newUsername = scanner.nextLine();
        // Check if the username already exists
        if (users.containsKey(newUsername)) {
            System.out.println("Username already exists. Choose a different username.");
            return;
        }

        System.out.print("Enter a password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Enter a profile: ");
        String newProfile = scanner.nextLine();

        // Create and add the new user to the users map
        User newUser = new User(newUsername, newPassword, newProfile);
        users.put(newUsername, newUser);

        System.out.println("New profile created successfully!");
    }

    private void handleLoggedInUser(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Quiz");
            System.out.println("4. Logout");

            int choice = 0;

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter new profile: ");
                    String newProfile = scanner.next();
                    user.updateProfile(newProfile);
                    System.out.println("Profile updated successfully.");
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.next();
                    user.updatePassword(newPassword);
                    System.out.println("Password updated successfully.");
                    break;
                case 3:
                    startQuiz(user);
                    break;
                case 4:
                    System.out.println("Logout successful.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void startQuiz(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting Quiz...");

        for (String questionNumber : quiz.getQuestions().keySet()) {
            System.out.println("Question " + questionNumber + ": " + quiz.getQuestion(questionNumber));
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();

            if (quiz.isAnswerCorrect(questionNumber, userAnswer)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer is: " + quiz.getQuestions().get(questionNumber + "_answer"));
            }
        }

        System.out.println("Quiz completed!");
    }

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.login();
    }
}
