/**
 * 
 */
package sudoku_solver;


import java.io.*;

/**
 * @author conanz
 *
 * SudokuSolver class which takes in a text file as a parameter, reads it, and generates a matrix of values  for the algorithm to solve.
 * It also keeps track of the number of backtracking steps necessary to solve it and prints  the solved result.
 */
public class SudokuSolver
{
    /**Class Member Variables**/
    String inputFile;
    String text;
    
    int valueArray[];//Array to find actual integers in file
    int sudokuMatrix[][];//Actual board with values in a 9x9 matrix
    
    int backtracking;//value to count number of steps back needed to take
    
    /**CONSTRUCTOR**/
    public SudokuSolver(String inputFileParameter) throws IOException
    {
        //Assign Class Member Variables to Parameters
        inputFile = inputFileParameter;
                
        //Initiate Class Member Variables
        valueArray = new int[81];//81 values
        sudokuMatrix = new int[9][9];//9x9 matrix
        backtracking = 0;
        
        /**Read Input File**/
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        try 
        {
            //create object to read line
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            //keep reading line
            while (line != null) 
            {
                sb.append(line);
                line = br.readLine();
            }
            text = sb.toString();
        } 
        finally 
        {
            br.close();
        }
        

        //Function call to create matrix of values from text file
        fillArrayMatrix();
        
        //Print unsolved matrix and file name
        System.out.println("        " + inputFile);
        System.out.println("/-----UNSOLVED MATRIX-----/");
        printMatrix();
        
        solve(sudokuMatrix,0,-1);
        
        //Print solved matrix
        System.out.println();
        System.out.println("        " + inputFile);
        System.out.println("/------SOLVED MATRIX------/");
        printMatrix();
        
        //Number of backtracking steps
        System.out.println("# of backtracking steps: " + backtracking);

        
    }
    
    /**FUNCTION TO CHECK IF EVERY SPACE ON BOARD IS FILLED**/
    public boolean checkBoardFilled()
    {
        //scroll through board
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                //if there is any value that is 0 (blank)
                if(sudokuMatrix[i][j] == 0)
                {
                    //board not filled
                    return false;
                }
            }
        }
        
        //board filled with correct values
        return true;
    }
    
    /**RECURSIVE FUNCTION TO SOLVE INPUT FILE**/
    public boolean solve(int[][] sudoku, int horz, int vert)
    {
        /**BASE CASE: Every value in board filled**/
        while(!checkBoardFilled())
        {
            //look at next value (int vert in paramter NEEDS to be -1)
            vert++;
            
            //check to move to next row if reached end of the current row
            if(vert == 9)
            {
                //start at beginning of next column
                vert = 0;
                horz++;
            }
            
            //when current is blank
            if(sudoku[horz][vert] == 0 )
            {
                //try filling it with numbers 1-9
                for(int i = 1; i < 10; i++)
                {
                    //store current to go back to if necessary (for backtracking incorrect values)
                    int temp = sudoku[horz][vert];
                    
                    sudoku[horz][vert] = i;
                    
                    //test if it works
                    if(valid(vert,horz))
                    {
                        /**RECURSIVE CALL**/
                        //solve next number
                        if( solve(sudoku,horz,vert) )
                        {
                            return true;
                        }
                        else
                        {
                            backtracking++;
                            sudoku[horz][vert] = 0;
                        }
                    }
                    //revert back to original
                    else
                    {
                        sudoku[horz][vert] = temp;
                    } 
               }
               /**NO NUMBERS IN FOR LOOP WORKED: Backtracking to recursive call on line 133/137**/
               return false;
            }
        }
        //board filled
        return true;
    }
    
    /**FUNCTION TO CHECK IF VALUE AT CURRENT POSITIONS IS VALID**/
    public boolean valid(int horz, int vert)
    {
        for(int i = 0; i < 9; i ++)
        {
            //check to see if number isn't in row
            if(sudokuMatrix[vert][horz] == sudokuMatrix[i][horz] && i != vert)
            {
                return false;
            }
            
            //check to see if number isn't in column
            if(sudokuMatrix[vert][horz] == sudokuMatrix[vert][i] && i != horz)
            {
                return false;
            }
        }
        
        /**check to see if number isn't in 3x3 grid**/
        //top left grid
        if(vert <= 2 && horz <=2)
        {
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //top middle grid
        else if(vert > 2 && vert <= 5 && horz <= 2)
        {
            for(int i=3;i<6;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //top right grid
        else if(vert > 5 && horz <= 2)
        {
            for(int i=6;i<9;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //middle left grid
        else if(vert <=2 && horz<=5 && horz >2)
        {
            for(int i=0;i<3;i++)
            {
                for(int j=3;j<6;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //middle middle grid
        else if(vert > 2 && vert <= 5 && horz <=5 && horz > 2)
        {
            for(int i=3;i<6;i++)
            {
                for(int j=3;j<6;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //middle right grid
        else if(vert > 5 && horz<=5 && horz>2)
        {
            for(int i=6;i<9;i++)
            {
                for(int j=3;j<6;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //bottom left grid
        else if(vert <=2 && horz > 5 )
        {
            for(int i=0;i<3;i++)
            {
                for(int j=6;j<9;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //bottom middle grid
        else if(vert>2&& vert <=5 && horz>5)
        {
            for(int i=3;i<6;i++)
            {
                for(int j=6;j<9;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        //bottom right grid
        else
        {
            for(int i=6;i<9;i++)
            {
                for(int j=6;j<9;j++)
                {
                    if(sudokuMatrix[i][j] == sudokuMatrix[vert][horz] && i != vert && j != horz)
                    {
                         return false;
                    }
                }
            }
        }
        return true;
    }
    /**FUNCTION TO CREATE 9X9 MATRIX OF VALUES FROM TEXT FILE**/
    public void fillArrayMatrix()
    {
        /**Create Array of Valid Values from String**/
        //index count to fill in array of size 81
        int arrayCounter = 0;
        
        for(int i =0 ; i < text.length()  ; i++)
        {
            //if valid value...
            if( text.charAt(i) == '.' ||
                text.charAt(i) == '1' ||
                text.charAt(i) == '2' ||
                text.charAt(i) == '3' ||
                text.charAt(i) == '4' ||
                text.charAt(i) == '5' ||
                text.charAt(i) == '6' ||
                text.charAt(i) == '7' ||
                text.charAt(i) == '8' ||
                text.charAt(i) == '9'   )
            {
                //convert '.' into integer 0
                if(text.charAt(i) == '.')
                {
                    //set in array and increment
                    valueArray[arrayCounter] = 0;
                    arrayCounter++;
                }
                else
                {          
                    //convert character to integer, set in array, and increment
                    valueArray[arrayCounter] = Character.getNumericValue( text.charAt(i) );
                    arrayCounter++;
                }
            }
        }

        /**Fill in Matrix from Array of Valid Values**/
        //index count to use all elements in array
        int fillCounter = 0;
        
        for(int i = 0; i < 9; i ++)
        {
            for(int j = 0; j<9; j++)
            {
                sudokuMatrix[i][j] = valueArray[fillCounter];
                fillCounter++;
            }
        }
    }
    
    /**FUNCTION TO PRINT MATRIX**/
    public void printMatrix()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j <9; j++)
            {
                System.out.print(" " + sudokuMatrix[i][j] + " ");//print all j values in one row separated by a space
            }
            System.out.println();//break to a new line to show matrix/table of values
        }
    }
}
