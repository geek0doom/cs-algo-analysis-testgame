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

    public static int getBestMoveAlphaBeta(int[] board) {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = 2;
                int score = alphaBeta(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                board[i] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    public static int alphaBeta(int[] board, int depth, int alpha, int beta, boolean isMaximizing) {
        int result = checkWinner(board);
        if (result == 1) return -10 + depth;
        if (result == 2) return 10 - depth;
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 2;
                    int eval = alphaBeta(board, depth + 1, alpha, beta, false);
                    board[i] = 0;
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1;
                    int eval = alphaBeta(board, depth + 1, alpha, beta, true);
                    board[i] = 0;
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }

    public static int getBestMoveMCTS(int[] board, int player, int simulations) {
        Random random = new Random();
        int bestMove = -1;
        int maxWins = Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                int wins = 0;
                for (int j = 0; j < simulations; j++) {
                    int[] copy = board.clone();
                    copy[i] = player;
                    int winner = simulateRandomPlayout(copy, switchPlayer(player));
                    if (winner == player) wins++;
                }
                if (wins > maxWins) {
                    maxWins = wins;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    public static int simulateRandomPlayout(int[] board, int currentPlayer) {
        int winner = checkWinner(board);
        while (winner == 0 && !isBoardFull(board)) {
            List<Integer> moves = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) moves.add(i);
            }
            Collections.shuffle(moves);
            board[moves.get(0)] = currentPlayer;
            currentPlayer = switchPlayer(currentPlayer);
            winner = checkWinner(board);
        }
        return winner;
    }

    public static int switchPlayer(int player) {
        return (player == 1) ? 2 : 1;
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
        return getBestMoveRandom(board);
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
        if (result == 1) return -10 + depth;
        if (result == 2) return 10 - depth;
        if (isBoardFull(board)) return 0;

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
