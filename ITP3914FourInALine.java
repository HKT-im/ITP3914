import java.util.Scanner;
import java.util.Arrays;

public class ITP3914FourInALine {
    private final int rows = 6;
    private final int cols = 7;
    private int[][] grid = new int[rows][cols];
    private int currentPlayer = 1;
    private int[] stackHights = new int[cols]; // an array of the hights of the stack in grid
    private boolean winning = false;
    private Scanner scan = new Scanner(System.in);
    private int currentDisc; // User Input

    // a public application programming interface for the user to stat a match
    public void run() {
        do {
            drawGrid();
            placeDisc();
            // check if all elements in stackHights are greater than 5
            if (Arrays.stream(stackHights).allMatch(t -> t > 5)) {
                break;
            }
        } while (!winning);
        drawGrid();
        if (winning) {
            System.out.println("Player " + currentPlayer + " win this game!");
        } else {
            System.out.println("Draw Game!");
        }
    }

    // a method for printing the board
    private void drawGrid() {
        for (int i = rows - 1, topRow = rows - 1; i >= 0; --i, --topRow) {
            System.out.printf("%2d%2s", topRow, "|");
            for (int j = 0; j < cols; ++j) {
                System.out.printf("%2d", grid[i][j]);
            }
            System.out.println();
        }
        System.out.printf("%2s", " ");
        System.out.printf("%2s", "+");
        for (int i = 0; i < cols * 2; ++i) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = -2, startCol = -2; i < cols; ++i, ++startCol) {
            System.out.printf("%2s", i < 0 ? " " : startCol);
        }
        System.out.println();
    }

    // a method to place the Disc to the board
    private void placeDisc() {
        System.out.print("Player " + currentPlayer + " type a column <0-6> or 9 to quit current game: ");

        // get input from users and prevent invaild inputs
        try {
            currentDisc = scan.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            scan.nextLine();
            return;
        }

        // check for invaild inputs
        switch (getError(currentDisc)) {
            case InvalidNumber:
                System.out.println("Range of column should be 0 to 6!");
                return;
            case Full:
                System.out.println("Column " + currentDisc + " is full!");
                return;
            case Exit:
                System.out.println("Bye Bye!");
                System.exit(0);
            default:
                break;
        }
        // place the disc to the position player asked for
        grid[stackHights[currentDisc]++][currentDisc] = currentPlayer;

        if (winning = checkWinner()) {
            return;
        }

        // After a player placed a disc, change the current player to the other player
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }

    }

    // check for number of disc connected Vertically
    private boolean checkVertically() {
        int counter = 0;
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = stackHights[currentDisc] - 1; i > stackHights[currentDisc] - 5; --i, ++counter) {
            if (i < 0 || grid[i][currentDisc] != currentPlayer) {
                break;
            }
        }
        return counter == 4;
    }

    // check for number of disc connected Horizontally
    private boolean checkHorizontally() {
        int counter = 0;
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = currentDisc; i < cols; ++i, ++counter) {
            if (grid[stackHights[currentDisc] - 1][i] != currentPlayer) {
                break;
            }
        }
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = currentDisc - 1; i >= 0; --i, ++counter) {
            if (grid[stackHights[currentDisc] - 1][i] != currentPlayer) {
                break;
            }
        }
        return counter + 1 == 4;
    }

    // check for number of disc connected from Right Top to Left Bottom
    private boolean chekckRightTop2LeftBottom() {
        int counter = 0;
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = currentDisc + 1, j = stackHights[currentDisc]; i < cols && j < rows; ++i, ++j, ++counter) {
            if (grid[j][i] != currentPlayer) {
                break;
            }
        }
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = currentDisc - 1, j = stackHights[currentDisc] - 2; i >= 0 && j >= 0; --i, --j, ++counter) {
            if (grid[j][i] != currentPlayer) {
                break;
            }
        }
        return counter + 1 == 4;
    }

    // check for number of disc connected from Left Top to RightBottom
    private boolean checkLeftTop2RightBottom() {
        // increase the counter untill the pointer reachsthe other player's disc
        int counter = 0;
        for (int i = currentDisc + 1, j = stackHights[currentDisc] - 2; i < cols && j >= 0; ++i, --j, ++counter) {
            if (grid[j][i] != currentPlayer) {
                break;
            }
        }
        // increase the counter untill the pointer reachsthe other player's disc
        for (int i = currentDisc - 1, j = stackHights[currentDisc]; i >= 0 && j < rows; --i, ++j, ++counter) {
            if (grid[j][i] != currentPlayer) {
                break;
            }
        }
        return counter + 1 == 4;

    }

    // a method to check winner
    private boolean checkWinner() {
        return checkHorizontally() || checkVertically() || chekckRightTop2LeftBottom() || checkLeftTop2RightBottom();
    }

    // enum of status codes
    private enum StatCode {
        InvalidNumber, Full, Exit, Vaild
    }

    private StatCode getError(int input) {
        // When user input 9, exit the programme
        if (input == 9) {
            return StatCode.Exit;
        }
        // When user input a number not between 0 and 6, return InvalidNumber
        if (input < 0 || input > 6) {
            return StatCode.InvalidNumber;
        }
        // if the stack is full, return full
        if (stackHights[input] > 5) {
            return StatCode.Full;
        }
        // else
        return StatCode.Vaild;
    }

    @Override // destructor
    public void finalize() {
        scan.close();
    }

    public static void main(String[] args) {
        ITP3914FourInALine game = new ITP3914FourInALine();
        game.run();
    }
}
