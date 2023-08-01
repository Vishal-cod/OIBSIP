package com.company;
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();

            System.out.println("Welcome to the Guess the Number Game with computer");

            int minRange = 1;
            int maxRange = 100;
            int totalRounds = 3;
            int attemptsLimit = 5;
            int totalScore = 0;

            for (int round = 1; round <= totalRounds; round++) {
                int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
                System.out.println("\nRound " + round + " of " + totalRounds);
                System.out.println("I have picked a number between " + minRange + " and " + maxRange + ". Guess the number");

                int attempts = 0;
                boolean isGuessed = false;

                while (attempts < attemptsLimit) {
                    System.out.print("Enter your guess: ");
                    int userGuess = scanner.nextInt();
                    attempts++;

                    if (userGuess == targetNumber) {
                        System.out.println("Congratulations! You guessed the correct number.");
                        int roundScore = attemptsLimit - attempts + 1;
                        totalScore += roundScore;
                        System.out.println("Your score for this round: " + roundScore);
                        isGuessed = true;
                        break;
                    } else if (userGuess < targetNumber) {
                        System.out.println("Try a higher number.");
                    } else {
                        System.out.println("Try a lower number.");
                    }
                }

                if (!isGuessed) {
                    System.out.println("Out of attempts. The correct number was: " + targetNumber);
                }
            }

            System.out.println("\nGame Over!");
            System.out.println("Your total score: " + totalScore);

            scanner.close();
        }
    }

