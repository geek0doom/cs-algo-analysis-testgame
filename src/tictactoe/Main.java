package tictactoe;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 3 && args[0].equalsIgnoreCase("-s")) {
            String algo1 = args[1];
            String algo2 = args[2];
            int games = (args.length >= 4) ? Integer.parseInt(args[3]) : 100;
            runSimulations(algo1, algo2, games);
            return;
        }
        else if (args.length >= 1 && (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("-help"))) {
            System.out.printf("To use this program: -s <algo1> <algo2> <games to run>%n"
                    + "Algorithms include: 'minimax', 'alphabeta', 'greedy', 'random', 'mcts'%n"
                    + "Example: java -cp out tictactoe.Main -s mcts alphabeta 100%n");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Select mode: 1 = Manual Play, 2 = Auto Simulations: ");
        int mode = scanner.nextInt();

        if (mode == 1) {
            int[] board = new int[9];
            int winner = 0;
            System.out.println("Tic Tac Toe - You (X) vs Bot (O)");

            for (int turn = 0; turn < 9 && winner == 0; turn++) {
                printBoard(board);

                if (turn % 2 == 0) { // Player's turn
                    int move;
                    while (true) {
                        System.out.print("Enter your move (1-9): ");
                        move = scanner.nextInt() - 1;
                        if (move < 0 || move > 8 || board[move] != 0) {
                            System.out.println("Invalid move. Try again.");
                        } else break;
                    }
                    board[move] = 1;
                } else { // Bot's turn
                    System.out.println("Bot is thinking...");
                    int bestMove = robot.getBestMoveMinimax(board);
                    board[bestMove] = 2;
                }

                winner = robot.checkWinner(board);
            }

            printBoard(board);
            if (winner == 1) System.out.println("You win!");
            else if (winner == 2) System.out.println("Bot wins!");
            else System.out.println("It's a draw!");
        }
        else if (mode == 2) {
            System.out.print("Choose Player 1 AI (minimax/alphabeta/greedy/random/mcts): ");
            String algo1 = scanner.next();
            System.out.print("Choose Player 2 AI (minimax/alphabeta/greedy/random/mcts): ");
            String algo2 = scanner.next();

            int games = 100;
            runSimulations(algo1, algo2, games);
        }
    }

    public static void runSimulations(String algo1, String algo2, int games) {
        int p1Wins = 0, p2Wins = 0, draws = 0;

        long start = System.nanoTime();
        for (int i = 0; i < games; i++) {
            int winner = playAutomatedGame(algo1, algo2);
            if (winner == 1) p1Wins++;
            else if (winner == 2) p2Wins++;
            else draws++;
        }
        long end = System.nanoTime();
        double durationMs = (end - start) / 1e6;
        double avgTimePerGame = durationMs / games;

        System.out.printf("After %d games:%n", games);
        System.out.printf("Player 1 (%s) wins: %d%n", algo1, p1Wins);
        System.out.printf("Player 2 (%s) wins: %d%n", algo2, p2Wins);
        System.out.printf("Draws: %d%n", draws);
        System.out.printf("Total simulation time: %.3f ms%n", durationMs);
        System.out.printf("Average time per game: %.3f ms%n", avgTimePerGame);
    }

    public static void printBoard(int[] board) {
        for (int i = 0; i < 9; i++) {
            char symbol = switch (board[i]) {
                case 1 -> 'X';
                case 2 -> 'O';
                default -> (char) ('1' + i);
            };
            System.out.print(" " + symbol + " ");
            if (i % 3 != 2) System.out.print("|");
            else if (i != 8) System.out.println("\n-----------");
        }
        System.out.println();
    }

    public static int playAutomatedGame(String algo1, String algo2) {
        int[] board = new int[9];
        int winner = 0;

        for (int turn = 0; turn < 9 && winner == 0; turn++) {
            int currentPlayer = (turn % 2 == 0) ? 1 : 2;
            int move;

            String currentAlgo = (currentPlayer == 1) ? algo1 : algo2;

            switch (currentAlgo.toLowerCase()) {
                case "minimax" -> move = robot.getBestMoveMinimax(board);
                case "alphabeta" -> move = robot.getBestMoveAlphaBeta(board);
                case "greedy" -> move = robot.getBestMoveGreedy(board, currentPlayer);
                case "mcts" -> move = robot.getBestMoveMCTS(board, currentPlayer, 50);
                case "random" -> move = robot.getBestMoveRandom(board);
                default -> throw new IllegalArgumentException("Unknown algorithm: " + currentAlgo);
            }

            board[move] = currentPlayer;
            winner = robot.checkWinner(board);
        }

        return winner;
    }
}