import java.io.*;
import java.util.*;

class Question {
    String questionText;
    String[] options;
    int correctOption;

    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

class Quiz {
    LinkedList<Question> questions;
    int score;

    public Quiz() {
        questions = new LinkedList<>();
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int getNumQuestions() {
        return questions.size();
    }

    public int getScore() {
        return score;
    }

    public void takeQuiz(String userName) {
        Scanner sc = new Scanner(System.in);
        score = 0;

        System.out.println("Welcome, " + userName + "! Let's start the quiz.\n");

        for (Question question : questions) {
            System.out.println(question.questionText);
            for (int i = 0; i < question.options.length; i++) {
                System.out.println((i + 1) + ". " + question.options[i]);
            }
            do {
                System.out.print("Enter your choice: ");
                int userChoice = sc.nextInt();
                try {
                    if ((userChoice == question.correctOption + 1) && (userChoice >= 1 && userChoice <= 4)) {
                        System.out.println("Correct!\n");
                        score++;
                        break;
                    } else if ((userChoice != question.correctOption + 1) && (userChoice >= 1 && userChoice <= 4)) {
                        System.out.println("Incorrect!\n");
                        break;
                    } else {
                        System.out.println("Enter a valid option");
                    }
                } catch (Exception e) {
                    System.err.println("Invalid input. Please enter a number.\n");
                    sc.nextLine();
                }
            } while (true);

        }

        System.out.println("Quiz completed, " + userName + ". Your score: " + score + " out of " + questions.size());
    }
}

class QuizApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numPlayers = sc.nextInt();
        sc.nextLine(); // Clear the newline character

        Quiz quiz = new Quiz();

        // Sample questions (same as before)
        Question q1 = new Question("What is the capital of Italy?",
                new String[] { "Rome", "Madrid", "Paris", "Berlin" }, 0);
        Question q2 = new Question("Which planet is known as the Blue Planet?",
                new String[] { "Mars", "Venus", "Earth", "Jupiter" }, 2);
        Question q3 = new Question("What is the largest mammal?",
                new String[] { "Elephant", "Blue Whale", "Giraffe", "Lion" }, 1);
        Question q4 = new Question("Which gas do plants use for photosynthesis?",
                new String[] { "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen" }, 1);
        Question q5 = new Question("Which famous scientist developed the theory of relativity?",
                new String[] { "Isaac Newton", "Albert Einstein", "Nikola Tesla", "Galileo Galilei" }, 1);

        quiz.addQuestion(q1);
        quiz.addQuestion(q2);
        quiz.addQuestion(q3);
        quiz.addQuestion(q4);
        quiz.addQuestion(q5);

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerScores = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = sc.nextLine();
            playerNames.add(playerName);

            quiz.takeQuiz(playerName);
            System.out.println();

            playerScores.add(quiz.getScore());
        }

        // Store player names and scores in a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("quiz_scores.txt"))) {
            for (int i = 0; i < numPlayers; i++) {
                writer.write(playerNames.get(i) + "," + playerScores.get(i) + "\n");
            }
            System.out.println("Quiz scores stored in 'quiz_scores.txt' file.");
        } catch (Exception e) {
            System.out.println("Error while storing quiz scores: " + e.getMessage());
        }

        // Display contents of the text file
        try (BufferedReader reader = new BufferedReader(new FileReader("quiz_scores.txt"))) {
            System.out.println("\nQuiz Results:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(parts[0] + " scored " + parts[1] + " out of " + quiz.getNumQuestions());
            }
        } catch (Exception e) {
            System.out.println("Error while reading quiz scores: " + e.getMessage());
        }
    }
}