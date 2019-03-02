import java.util.*;
import java.io.*;
public class Maze{

    private char[][]maze;
    private boolean animate;//false by default
    private int solvedAts; //number of @s in solution

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        animate = false;
        File f = new File(filename);
        Scanner s1 = new Scanner(f); //find the rows and cols to instantiate maze
        Scanner s2 = new Scanner(f); //put everything in maze
        int rows = 0; //dimensions of maze
        int cols = 0;
        int countE = 0; //for chacking if there is exactly 1E and 1S
        int countS = 0;
        solvedAts = -1;
        while (s1.hasNextLine()){ //find dimensions of maze
          rows++;
          String s = s1.nextLine();
          cols = s.length();
        }
        maze = new char[rows][cols];
        int row = 0;
        while (s2.hasNextLine()){ //stores chars into maze
          String temp =s2.nextLine();
          for (int i = 0; i<temp.length(); i++){
            if (temp.charAt(i)=='E'){
              countE++;
            }
            else if (temp.charAt(i)=='S'){
              countS++;
            }
            maze[row][i] = temp.charAt(i);
          }
          row++;
        }
        if (countE!=1 || countS!=1){ //invalid maze
          throw new IllegalStateException();
        }
    }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }





   /*Return the string that represents the maze.
     It should look like the text file with some characters replaced.
    */
    public String toString(){
      String s = "";
      for (int i = 0; i < maze.length; i++){
        for (int j = 0; j < maze[0].length; j++){
          s+=maze[i][j];
        }
        s+="\n";
      }
      return s;
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
            //find the location of the S.
          int r = 0;
          int c = 0;
          //coors of S
          for (int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[0].length; j++){
              if (maze[i][j]=='S'){
                r = i; //store the coors
                c = j;
                maze[i][j]=' '; //erase the s
                i = maze.length;
                j = maze[0].length;
              }
            }
          }
            //and start solving at the location of the s.
            //return solve(???,???);
        return solve(r, c, 0);
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int ats){ //you can add more parameters since this is private
      //System.out.println("ats: "+ats); //debug
      //automatic animation! You are welcome.
      if(animate){
          clearTerminal();
          System.out.println(this);
          wait(20);
      }
      //base case: solved
      if (maze[row][col]=='E'){
        setAnimate(false); //it's been solved, no more need to animate
        //System.out.println("Solved!!!!!!!!!!!!!");
        //System.out.println("Ats solved: "+ats);
        solvedAts = ats;
        return ats; //return number of @s
      }
      //System.out.println("animate: "+animate);
      if (animate){
        if (canMoveUp(row,col) && solvedAts<0){
          maze[row][col]='@';
          solve(row - 1, col, ats+1);
        }

        if (canMoveDown(row,col) && solvedAts<0){
          maze[row][col]='@';
          solve(row + 1, col, ats+1);
        }

        if (canMoveLeft(row,col) && solvedAts<0){
          maze[row][col]='@';
          solve(row, col - 1, ats+1);
        }

        if (canMoveRight(row,col) && solvedAts<0){
          maze[row][col]='@';
          solve(row, col + 1, ats+1);
        }
        //if these don't work, backtrack
        if (solvedAts < 0){
          maze[row][col]='.';
          ats--;
        }
        //COMPLETE SOLVE
      }
      //System.out.println(ats);
      return solvedAts; //no solution
    }

    private boolean canMoveUp(int r, int c){
      return maze[r-1][c]==' '||maze[r-1][c]=='E';
    }

    private boolean canMoveDown(int r, int c){
      return maze[r+1][c]==' '||maze[r+1][c]=='E';
    }
    private boolean canMoveLeft(int r, int c){
      return maze[r][c-1]==' '||maze[r][c-1]=='E';
    }
    private boolean canMoveRight(int r, int c){
      return maze[r][c+1]==' '||maze[r][c+1]=='E';
    }
}
