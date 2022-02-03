package frc.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Limelight {
    // Declare variables
    int[][] table;
    int dist;
    int rpm;
    int hoodAngle;
    int rotation;

    // Constructor
    public Limelight() {
        table = new int[15][4];
    }

    // Fills the array with each square's index
    // and prints it to a file
    public void testData() {
        for( int j = 0; j < table[0].length; j++ ) 
            for( int i = 0; i < table.length; i++ ) 
                table[i][j] = i + (j * table.length);
    }





    /*----------------------- File Handling -----------------------*/

    // Prints the data from the limelight datatable into Limetable.txt
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
                else if( table[i][j] >= 100 && table[i][j] < 1000 )
                    content = " " + table[i][j] + " |";
                else if( table[i][j] >= 1000 && table[i][j] < 10000 )
                    content = "" + table[i][j] + " |";
                else 
                    content = "" + table[i][j] + "|";

                fileWriter.write(content);
            }
            // Start the next row
            fileWriter.write("\n|");
        }
        // Close the filewriter when it is done
        fileWriter.close();
    }

    // BUG - data all has to be on one line to be properly imported
    // Input data from a file into the limelight datatable
    public void loadFromFile( String fileName )
    {
        try {
            // Open file and scanner
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            // Uses "/" and new lines as delimiters
            scan.useDelimiter("/|\\n"); 
            
            // Adds data into the table until there is no more data to add
            int i = 0;
            while( scan.hasNext() ) {
                try {
                    table[i][0] = scan.nextInt(); 
                    table[i][1] = scan.nextInt();
                    table[i][2] = scan.nextInt();
                    table[i][3] = scan.nextInt();
                    i++;
                }
                catch ( InputMismatchException e ) { break; }
            }
            // Close the scanner when it is done
            scan.close();
        }
        catch ( FileNotFoundException e ) { /*File does not exist*/ }
   }
}
