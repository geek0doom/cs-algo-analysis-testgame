package tictactoe;

import java.util.*;

public class robot {

    public static int getBestMoveMinimax(int[] board) {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = 2; // bot move
                int score = minimax(board, 0, false);
                board[i] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    public static int getBestMoveGreedy(int[] board, int player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = player;
                if (checkWinner(board) == player) {
                    board[i] = 0;
                    return i; // take win
                }
                board[i] = 0;
            }
        }
        int opponent = (player == 1) ? 2 : 1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = opponent;
                if (checkWinner(board) == opponent) {
                    board[i] = 0;
                    return i; // block
                }
                board[i] = 0;
            }
        }
        return getBestMoveRandom(board); // fallback
    }

    public static int getBestMoveRandom(int[] board) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) moves.add(i);
        }
        Collections.shuffle(moves);
        return moves.get(0);
    }

    public static int minimax(int[] board, int depth, boolean isMaximizing) {
        int result = checkWinner(board);
        if (result == 1) return -10 + depth; // player win
        if (result == 2) return 10 - depth;  // bot win
        if (isBoardFull(board)) return 0;    // draw

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 2;
                    int score = minimax(board, depth + 1, false);
                    board[i] = 0;
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1;
                    int score = minimax(board, depth + 1, true);
                    board[i] = 0;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public static int checkWinner(int[] board) {
        int[][] winCombos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        for (int[] combo : winCombos) {
            if (board[combo[0]] != 0 &&
                board[combo[0]] == board[combo[1]] &&
                board[combo[1]] == board[combo[2]]) {
                return board[combo[0]];
            }
        }
        return 0;
    }

    public static boolean isBoardFull(int[] board) {
        for (int cell : board) {
            if (cell == 0) return false;
        }
        return true;
    }
} 
