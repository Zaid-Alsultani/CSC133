package mechanicsBE;

import java.util.Scanner;
import java.util.Random;

public class slTTTBoard {
    public char[][] board;
    private Scanner scanner;
    private Random random;
    private final static char PLAYER = 'O';
    public final static char MACHINE = 'X';

    // Game statuses
    public static final int GAME_QUIT = 0;
    public static final int GAME_PLAYER = 1;
    public static final int GAME_MACHINE = 2;
    public static final int GAME_DRAW = 3;

    private boolean firstMove = true;

    public slTTTBoard() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '.';
            }
        }
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2)
                    System.out.print("     ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public void resetBoard() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '.';
            }
        }
        firstMove = true;
    }

    public void play() {
        boolean playerTurn = true;
        while (true) {
            if (playerTurn) {
                if (!firstMove) {
                    printBoard();
                }
                firstMove = false;
                System.out.print(
                        "Enter row and col for your entry (only) space separated; type 'q' to quit\n Enter row col numbers (space separated) ");
                String input = scanner.nextLine();
                if (input.equals("q")) {
                    System.out.println("Sorry to see you go; come again!");
                    System.exit(0);
                }

                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    System.out.println("Invalid input. Please enter row and col separated by a space.");
                    continue;
                }

                int row, col;
                try {
                    row = Integer.parseInt(parts[0]);
                    col = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric values for row and col.");
                    continue;
                }

                if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("Invalid input. Row and col must be between 0 and 2.");
                    continue;
                }

                if (board[row][col] != '.') {
                    System.out.println("The cell is already marked. Try again.");
                    continue;
                }

                board[row][col] = PLAYER;
                playerTurn = false;
            } else {
                // Machine move (random for the first move, greedy algorithm afterward)
                int[] move = findBestMove();
                if (move != null) {
                    board[move[0]][move[1]] = MACHINE;
                    playerTurn = true;
                }
            }

            // Check if someone won or if it's a draw
            int gameStatus = checkGameStatus();
            if (gameStatus != -1) {
                printBoard();
            }
            switch (gameStatus) {
                case GAME_QUIT:
                    System.out.println("Sorry to see you go; come again!");
                    break;
                case GAME_PLAYER:
                    System.out.println("Congratulations! you have won!");
                    break;
                case GAME_MACHINE:
                    System.out.println("Sorry, you did not win; try again!");
                    break;
                case GAME_DRAW:
                    System.out.println("Hey, you almost beat me, let's try again!");
                    break;
                default:
                    break;
            }
            if (gameStatus != -1) {
                break;
            }
        }
    }

    public void performRandomMove() {
        int row = random.nextInt(3);
        int col = random.nextInt(3);
        while (board[row][col] != '.') {
            row = random.nextInt(3);
            col = random.nextInt(3);
        }
        board[row][col] = MACHINE;
    }

    // Greedy algorithm to determine the machine's next move
    private int[] findBestMove() {
        // First move is random if the board is empty

        // Greedy algorithm: try to win or block the player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    // Try to win
                    board[i][j] = MACHINE;
                    if (checkWinner(MACHINE)) {
                        return new int[] { i, j };
                    }
                    board[i][j] = '.';

                    // Try to block the player
                    board[i][j] = PLAYER;
                    if (checkWinner(PLAYER)) {
                        board[i][j] = '.';
                        return new int[] { i, j };
                    }
                    board[i][j] = '.';
                }
            }
        }

        // If no winning/blocking move, pick the first available cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    return new int[] { i, j };
                }
            }
        }
        return null; // Board is full, this should never happen here
    }

    // Check if there's a winner or a draw
    private int checkGameStatus() {
        if (checkWinner(PLAYER)) {
            return GAME_PLAYER;
        }
        if (checkWinner(MACHINE)) {
            return GAME_MACHINE;
        }
        if (isBoardFull()) {
            return GAME_DRAW;
        }
        return -1; // Continue playing
    }

    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
}