package org.usfirst.frc.team4828.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class representing the driving joystick. Uses an internal Joystick object,
 * but easier to use because it does not have all the extra function that using
 * an actual Joystick object would. Example of composition being better than
 * inheritance.
 * 
 * @author Ben
 */
public class DriveStick {
	private Joystick stick;

	/**
	 * Constructor of DriveStick that creates the private Joystick object.
	 * 
	 * @param port
	 *            USB port of the joystick
	 */
	public DriveStick(int port) {
		stick = new Joystick(port);
	}
	
	public boolean getFireLauncher(){
		return getTrigger();
	}
	
	public boolean getRetractLauncher(){
		return getRawButton(2);
	}
	
	public boolean getLiftLoader(){
		return getRawButton(11);
	}

	public boolean getLowerLoader(){
		return getRawButton(12);
	}
	
	public boolean getBallSensorOverride(){
		return getRawButton(7);
	}
	
	private boolean getRawButton(int button){
		return stick.getRawButton(button);
	}
	
	private boolean getTrigger(){
		return stick.getTrigger();
	}
	
	public double getThrottle(){
		return stick.getThrottle();
	}
	
	public double getMagnitude(){
		return stick.getMagnitude();
	}
	
	public double getDirection(){
		return stick.getDirectionDegrees();
	}
	
	public double getTwist(){
		return stick.getTwist();
	}
}
