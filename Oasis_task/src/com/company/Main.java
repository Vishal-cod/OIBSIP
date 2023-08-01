package com.company;
import java.util.*;

class User1 {
    private String username;
    private String password;

    public User1(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class MCQ {
    private String question;
    private List<String> options;
    private int correctOption;

    public MCQ(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

class Question {
    private List<MCQ> mcqs;

    public Question() {
        mcqs = new ArrayList<>();
        // Add some sample MCQs for the exam
        mcqs.add(new MCQ("What is the capital of France?", Arrays.asList("A. Paris", "B. London", "C. Berlin", "D. Rome"), 1));
        mcqs.add(new MCQ("What is the largest mammal?", Arrays.asList("A. Elephant", "B. Giraffe", "C. Blue Whale", "D. Rhinoceros"), 3));
        mcqs.add(new MCQ("What is the chemical symbol for water?", Arrays.asList("A. H2O", "B. CO2", "C. O2", "D. CH4"), 0));
    }

    public List<MCQ> getMCQs() {
        return mcqs;
    }
}

class Timer extends Thread {
    private int seconds;

    public Timer(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            for (int i = seconds; i > 0; i--) {
                System.out.println("Time remaining: " + i + " seconds");
                Thread.sleep(1000);
            }
            System.out.println("Time's up! Auto-submitting your answers.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class OnlineExamSystem {
    private User1 currentUser;
    private Question question;
    private Scanner scanner;
    private Timer timer;

    public OnlineExamSystem() {
        scanner = new Scanner(System.in);
        question = new Question();
    }

    public void login(String username, String password) {
        // Simulate user authentication (you can modify this part based on your requirement)
        if (username.equals("user123") && password.equals("password123")) {
            currentUser = new User1(username, password);
            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
        } else {
            System.out.println("Login failed! Invalid credentials.");
        }
    }

    public void updateProfileAndPassword() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser = new User1(newUsername, newPassword);
        System.out.println("Profile and password updated successfully!");
    }

    public void selectAnswers() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        List<MCQ> mcqs = question.getMCQs();
        for (int i = 0; i < mcqs.size(); i++) {
            MCQ mcq = mcqs.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + mcq.getQuestion());
            for (String option : mcq.getOptions()) {
                System.out.println(option);
            }
            System.out.print("Enter the option number (0-4): ");
            int selectedOption = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (selectedOption >= 0 && selectedOption <= 3) {
                if (selectedOption == mcq.getCorrectOption()) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct option is " + mcq.getCorrectOption());
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public void startTimer(int seconds) {
        timer = new Timer(seconds);
        timer.start();
    }

    public void closeSession() {
        if (timer != null) {
            timer.interrupt();
            timer = null;
        }
        currentUser = null;
        System.out.println("Session closed. Logout successful.");
    }
}

public class Main {
    public static void main(String[] args) {
        OnlineExamSystem examSystem = new OnlineExamSystem();

        // Login
        examSystem.login("user123", "password123");

        // Update Profile and Password
        examSystem.updateProfileAndPassword();

        // Selecting answers for MCQs
        examSystem.selectAnswers();

        // Timer and auto-submit (30 seconds in this example)
        examSystem.startTimer(10);

        // Wait for the timer to finish or manually submit the answers
        // For the sake of simplicity, let's just wait for a few seconds in this example
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close session and Logout
        examSystem.closeSession();
    }
}
