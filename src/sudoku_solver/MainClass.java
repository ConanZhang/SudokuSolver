/**
 * 
 */
package sudoku_solver;

import java.io.IOException;

/**
 * @author conanz
 *
 * Main class to initiate SudokuSolver and pass in a text file that is a Sudoku puzzle.
 * 
 * Use command line argument to pass in a text file from the workspace.
 * Example: puzzle1.txt
 */
public class MainClass
{
    public static void main(String args[]) throws IOException
    {
        SudokuSolver solve = new SudokuSolver(args[0]);
        //print solve so we are "using" it, ugh; causes warning/error otherwise
        System.out.println("Printing " + solve + " (solve) because Eclipse is annoying" + "(In main class)");
    }
}
