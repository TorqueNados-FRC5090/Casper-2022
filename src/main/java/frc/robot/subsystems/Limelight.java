package frc.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Limelight {
    // Declare variables
    int[][] table;
    double dist;
    double power;
    double rpm;
    int hoodAngle;
    int rotation;

    // Constructor
    public Limelight() {
        table = new int[15][5];
    }

    // Fills the array with each square's index
    // and prints it to a file
    public void testData() {
        for( int j = 0; j < table[0].length; j++ ) 
            for( int i = 0; i < table.length; i++ ) 
                table[i][j] = i + (j * table.length);
    }

    public void printData() throws IOException {
        // Print everything to the Limetable file
        FileWriter fileWriter = new FileWriter("./src/main/java/frc/robot/subsystems/Limetable.txt");
        String content;

        fileWriter.write("|");

        // Prints the table
        for( int j = 0; j < table[0].length; j++ ) {
            for( int i = 0; i < table.length; i++ ) {
                // Logic that prints numbers differently based on digit count
                // ** Will have to be changed in the non-test version
                if( table[i][j] < 10 )
                    content = "  " + table[i][j] + "  |";
                else if( table[i][j] >= 10 && table[i][j] < 100 )
                    content = " " + table[i][j] + "  |";
                else 
                    content = " " + table[i][j] + " |";

                fileWriter.write(content);
            }
            // Start the next row
            fileWriter.write("\n|");
        }
        // Close the filewriter when it is done
        fileWriter.close();
    }
    public void loadFromFile( String fileName )
   {
      try 
      {
         File file = new File(fileName);
         Scanner scan = new Scanner(file);
         scan.useDelimiter("/|\\n"); // uses "/" and new lines as delimiters
         
         while ( scan.hasNext() )
         {
            String dataType = scan.next().toUpperCase();
            // When "PLAYER" is found in the file, create a player and try to insert it
            if (dataType.equals("PLAYER"))
            {
               id = scan.nextDouble(); 
               firstName = scan.next();
               lastName = scan.next();
               try { birthYear = scan.nextInt(); }
               catch ( InputMismatchException e ) { birthYear = -1; }
               country = scan.next();
               
               insertPlayer( id, firstName, lastName, birthYear, country );
            }
         }
            scan.close();
      }
      catch ( FileNotFoundException e ) { System.out.println( "File was not found." ); }
   }
}
