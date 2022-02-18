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
        table = new int[15][3];
    }

    // Fills the array with each square's index
    // and prints it to a file
    public void testData() {
        for( int j = 0; j < table[0].length; j++ ) 
            for( int i = 0; i < table.length; i++ ) 
                table[i][j] = i + (j * table.length);
    }


    // Finds the closest RPM that is needed to make a shot
    // from a given disatnce from limelight.
    public int getRPMFromDistance(int distance) {
        // These integer values are set to -1 so that it will be obvious
        // when a value outside of the array is called on
        int lower = -1;
        int higher = -1;
        int i;
        
        // We use this for loop to find the value closest to the target distance
        for( i = 0; i < table.length; i++) {
            if( table[i][0] > distance ) {
                lower = table[i][0];
                higher = table[i-1][0];
                break;
            }
        }
        // Returns RPM based on which distance is closest
        if (distance - lower >= higher - distance)
            return(table [i][1]);
        // Returns -1 if the distance is outside of the array
        else
            return(table [i - 1][1]);
        
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
