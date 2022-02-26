package frc.robot.wrappers;

import edu.wpi.first.wpilibj.DigitalInput;

// Wrapper class for limit switch
public class LimitSwitch {
    // Create a digital input for the switch
    private DigitalInput limSwitch;

    // Constructor
    public LimitSwitch(int port) {
        limSwitch = new DigitalInput(port);
    }

    // Returns true if the switch is being pressed
    public boolean isPressed() { return !limSwitch.get(); }
}