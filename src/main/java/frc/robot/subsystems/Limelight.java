package frc.robot.subsystems;

import java.io.FileWriter;
import java.io.IOException;

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
        table = new int[64][8];
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
}
