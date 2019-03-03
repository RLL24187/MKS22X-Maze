import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Driver{
    public static void main(String[]args){
      if (args.length>0){
        String filename = args[0];
        try{
          Maze f;
          f = new Maze(filename);//true animates the maze.
          System.out.println(f);
          f.setAnimate(true);
          System.out.println("Solution: "+f.solve());
          System.out.println(f);
        }catch(FileNotFoundException e){
          System.out.println("Invalid filename: "+filename);
        }
      }
      else{
        System.out.println("Please type a file name");
      }
    }
  }
