package csc133;

import mechanicsBE.slTTTBoard;

import java.util.Scanner;

public class gameFE {
    private slTTTBoard myBoard;
    private Scanner scanner;

    public gameFE(slTTTBoard myBoard) {
        this.myBoard = myBoard;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        myBoard.printBoard();
        while (true) {
            System.out.print("\nWould you like to start? 'n' for machine to start \n 'y' if you want to start the game: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                playerStarts();
            } else if (input.equalsIgnoreCase("n")) {
                machineStarts();
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                startGame(); // Prompt again if the input is invalid
            }
            myBoard.resetBoard();
        }
    }

    private void playerStarts() {
        myBoard.play(); // Player starts and the game begins
    }

    private void machineStarts() {
        // Machine's first move is random
        myBoard.performRandomMove();
        myBoard.printBoard();
        myBoard.play(); // Proceed to the normal gameplay where player takes the next turn
    }
}
