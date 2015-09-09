package org.usfirst.frc.team4828.robot;

/**
 * Class designed to hold static final values (port #s). Cannot be instantiated.
 * 
 * @author Ben
 *
 */
public class Ports {
	// Override default constructor for noninstaniabibity
	private Ports() {
		throw new IllegalStateException("Ports cannot be initialized");
	}

	// talon ports
	public static final int TALON_LEFTBACK = 1;
	public static final int TALON_RIGHTBACK = 2;
	public static final int TALON_LEFTFRONT = 3;
	public static final int TALON_RIGHTFRONT = 4;
	public static final int TALON_WINCH = 5;
	
	//relay port
	public static final int RELAY_SPIKE = 2; //powers compressor
	
	//digital input ports
	public static final int DI_NASON = 13;
	public static final int DI_BALLSENSOR = 1;
	public static final int DI_LIMIT_WINCHSTOP = 6;
	
	//joystick usb ports
	public static final int JOYSTICK_DRIVESTICK = 1;
	
	//pneumatic valve ports
	public static final int PNEUMATIC_LOADEROUT = 1;
	public static final int PNEUMATIC_LOADERIN = 2;
	public static final int PNEUMATIC_LAUNCHERIN = 7;
	public static final int PNEUMATIC_LAUNCHEROUT = 8;
}
