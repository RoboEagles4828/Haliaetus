package org.usfirst.frc.team4828.robot;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Represents a pneumatic valve. Controls a solenoid.
 * 
 * @author Ben
 *
 */
public class PneumaticValve {
	private final Solenoid solenoid;

	/**
	 * Constructor for a PnuematicValve containing a single solenoid.
	 * 
	 * @param solenoidChannel
	 *            channel of the solenoid
	 */
	public PneumaticValve(int solenoidChannel) {
		solenoid = new Solenoid(solenoidChannel);
	}
	
	public void setState(boolean state){
		solenoid.set(state);
	}
	
	public void open(){
		solenoid.set(true);
	}
	
	public void close(){
		solenoid.set(false);
	}
	
	public void switchState(){
		if(getState()) solenoid.set(false);
		else solenoid.set(true);
	}
	
	public boolean getState(){
		return solenoid.get();
	}
}
