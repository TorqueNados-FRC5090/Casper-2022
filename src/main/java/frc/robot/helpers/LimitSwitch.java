package frc.robot.helpers;

import edu.wpi.first.wpilibj.DigitalInput;

// Wrapper class for limit switch
public class LimitSwitch extends DigitalInput{
    // Create a digital input for the switch
    private DigitalInput limSwitch;

    public LimitSwitch(int port) {
        super(port);
        limSwitch = new DigitalInput(port);
    }

    // Returns true if the switch is being pressed
    public boolean isPressed() { return !limSwitch.get(); }
}
