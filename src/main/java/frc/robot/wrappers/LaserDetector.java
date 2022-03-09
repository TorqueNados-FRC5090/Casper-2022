package frc.robot.wrappers;

import edu.wpi.first.wpilibj.DigitalInput;

public class LaserDetector {
    // Create a digital input for the switch
    private DigitalInput laser;

    // Constructor
    public LaserDetector(int port) {
        laser = new DigitalInput(port);
    }

    // Returns true if the switch is being pressed
    public boolean isBlocked() { return !laser.get(); }
    public boolean isOpen() { return laser.get(); }
}
