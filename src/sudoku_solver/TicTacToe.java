/**
 * 
 */
package sudoku_solver;

import java.io.IOException;

/**
 * @author adamb
 *
 * NOTE:
 * Player = -1;
 * Computer = 1;
 * None = 0;
 */
public class TicTacToe
{
    /**Class Member Variables**/
    //Tic Tac Toe board
    int [][] board;
    
    /**CONSTRUCTOR**/
    TicTacToe() 
    {
        //Initialize board of size 3x3
        board = new int[3][3];
    }
    
    /**FUNCTION TO PRINT BOARD**/
    public void printBoard() 
    {
        //Scroll through board
        for (int i=0; i<3; i++) 
        {
            for (int j=0; j<3; j++) 
            {
                //Computer
                if (board[i][j] == 1) System.out.print("X ");
                //Player
                if (board[i][j] == -1) System.out.print("O ");
                //Nothing
                if (board[i][j] == 0) System.out.print(". ");
            }
            System.out.println(" ");
        }
    }
    
    /**FUNCTION TO PLAYER INPUT MOVE IN CONSOLE**/
    public void readMove() 
    {
        int i=-1, j=-1;
        
        //Fills in value for i and j with values in console for [row][column]
        try
        {
            int ch = System.in.read();
            while(!Character.isDigit(ch)) ch = System.in.read();
                i = Character.getNumericValue(ch);
            ch = System.in.read();
            while(!Character.isDigit(ch)) ch = System.in.read();
                j = Character.getNumericValue(ch);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        
        //fill board with value
        board[i][j] = -1;
    }
    
    /**FUNCTION TO CHECK FOR WIN**/
    public int score() 
    {
        //scroll through board
        for (int i=0; i<3; i++) 
        {
            //Computer wins
            if (board[i][0] == 1 && board[i][1] == 1 && board[i][2] == 1) return 1;//row
            if (board[0][i] == 1 && board[1][i] == 1 && board[2][i] == 1) return 1;//column
            
            //Player wins
            if (board[i][0] == -1 && board[i][1] == -1 && board[i][2] == -1) return -1;//row
            if (board[0][i] == -1 && board[1][i] == -1 && board[2][i] == -1) return -1;//column
        }
        //Computer wins diagonal
        if (board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) return 1;//top left to bottom right
        if (board[2][0] == 1 && board[1][1] == 1 && board[0][2] == 1) return 1;//bottom left to top right   
        
        //Player wins diagonal
        if (board[0][0] == -1 && board[1][1] == -1 && board[2][2] == -1) return -1;//top left to bottom right
        if (board[2][0] == -1 && board[1][1] == -1 && board[0][2] == -1) return -1;//bottom left to top right 
        
        //draw or no one has won (yet)
        return 0;
    }
    
    /**FUNCTION TO STOP ALGORITHM AFTER SOMEONE HAS ONE**/
    public boolean gameOver() 
    {
        //player or computer won
        if (score() != 0) return true;
        
        //scroll through board to see if there are any 0's (blanks)
        for (int i=0; i<3; i++) 
        {
            for (int j=0; j<3; j++) 
            {
                if (board[i][j] == 0) return false;
            }
        }
        
        //game is over if no 0's are found
        return true;
    }
    
    /**RECURSIVE FUNCTION TO FIND SOLUTION TO CURRENT BOARD**/
    public int evalBoard(int turn) 
    {
        /**BASE CASE: All spaced filled**/
        //check if game is over and return score if it is
        if (gameOver()) return score();
        
        //variables to stop function
        boolean isWin = false, isDraw = false;
        
        //scroll through board
        for (int i=0; i<3; i++) 
        {
            for (int j=0; j<3; j++) 
            {
                //Nothing in space (0)
                if (board[i][j] == 0)
                {
                    //fill space
                    board[i][j] = turn;
                    
//                    printBoard();
//                    System.out.println("--------------------------------------");
                    
                    /**RECURSIVE CALL**/
                    int result = evalBoard(-turn);
                    board[i][j] = 0;
                    
//                    printBoard();
                    
                    //see if computer won or it's a draw
                    if (result == turn) isWin = true;
                    if (result == 0) isDraw = true;
                }
            }
        }
        
        //results
        if (isWin) return turn;//return who won (-1 or 1)
        if (isDraw) return 0;//draw
        
        return -turn;//other player's turn
    }

    /**FUNCTION FOR COMPUTER TO PLAY**/
    public void chooseMove()
    {
        int wi = -1, wj = -1, di = -1, dj = -1;
        
        //scrolls through board
        for (int i=0; i<3; i++) 
        {
            for (int j=0; j<3; j++) 
            {
                //finds 0 (blank)
                if (board[i][j] == 0) 
                {
                    //place move (X)
                    board[i][j] = 1;
                    int result = evalBoard(-1);
                    
                    board[i][j] = 0;
                    if (result == 1) 
                    {
                        wi = i; wj = j;
                    }
                    if (result == 0) 
                    {
                        di = i; dj = j;
                    }
                }
            }
        }
        if (wi != -1) 
        {
            board[wi][wj] = 1;
        } 
        else if (di != -1) {
            board[di][dj] = 1;
        } 
        //Never executes if algorithm works
        else 
        {
            System.out.println("I give up");
            System.exit(0);
        }
    }
    
    /**COMPUTER VS. COMPUTER**/
    public void chooseMove2()
    {
        //variables to store winning/draw solutions
        int wi = -1, wj = -1, di = -1, dj = -1;
        
        //scrolls through board
        for (int i=0; i<3; i++) 
        {
            for (int j=0; j<3; j++) 
            {
                //finds 0 (blank)
                if (board[i][j] == 0) 
                {
                    //place move (X)
                    board[i][j] = -1;
                    int result = evalBoard(1);
                    
                    board[i][j] = 0;
                    
                    //win for computer
                    if (result == -1) 
                    {
                        //store
                        wi = i; wj = j;
                    }
                    //draw
                    if (result == 0) 
                    {
                        //store
                        di = i; dj = j;
                    }
                }
            }
        }
        if (wi != -1) 
        {
            //place winning solution
            board[wi][wj] = -1;
        } 
        else if (di != -1) 
        {
            board[di][dj] = -1;
        } 
        //Never executes if algorithm works
        else 
        {
            System.out.println("I give up");
            System.exit(0);
        }
    }
    
    /**MAIN FUNCTION TO CALL TIC TAC TOE**/
    public static void main(String args[]) 
    {
        //create class
        TicTacToe t = new TicTacToe();
        
        //loop to keep game playing until someone wins
        while (!t.gameOver() )
        {
            //computer
            t.printBoard();
            System.out.println("--------------------------------------");
            
            t.chooseMove();
            //Computer wins
            if (t.score() == 1) 
            {
                System.out.println("1 win");
            }
            if (t.score() == -1) 
            {
                System.out.println("2 win");
            }
            //No one wins
            else if (t.gameOver()) 
            {
                System.out.println("Draw");
            } 
            //let player move
            else 
            {
                t.printBoard();
                System.out.println("--------------------------------------");
                
                t.chooseMove2();//COMPUTER VS. COMPUTER
//                t.readMove();//COMPUTER VS. PLAYER
            }
        }
   }      
}
