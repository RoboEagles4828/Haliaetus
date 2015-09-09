package org.usfirst.frc.team4828.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * Updated code for Haliaeetus, the 2nd year robot of FRC team 4828 RoboEagles.<br>
 * Extends IterativeRobot instead of RobotBase (as RobotBase was removed from
 * wpilib's libraries in the 2015 season).<br>
 * Several quality of life changes to make debugging and update code easier.
 * Note: several functions (fire, retract, loaderUp, loaderDown) have had their
 * name changed for greater readability. A new class DriveStick is used intead
 * of Joystick for greater readability and easier maintenance. A new class Ports
 * is used to old port numbers, also for easier maintenance and to make changing
 * port numbers much easier.
 * 
 * @author Ben
 */
public class Haliaeetus extends IterativeRobot {
	// Variables
	private Talon talon_leftfront, talon_leftback, talon_rightfront,
			talon_rightback, talon_winch;
	private Relay relay_spike;
	private DigitalInput di_nason, di_ballsensor, di_limit_winchstop;
	private DriveStick joystick_drive;
	private PneumaticValve valve_loaderOut, valve_loaderIn, valve_launcherOut,
			valve_launcherIn;
	private RobotDrive drive;

	@Override
	public void robotInit() {
		System.out.println("debug: robotInit() called");
		talon_leftfront = new Talon(Ports.TALON_LEFTFRONT);
		talon_leftback = new Talon(Ports.TALON_LEFTBACK);
		talon_rightfront = new Talon(Ports.TALON_RIGHTFRONT);
		talon_rightback = new Talon(Ports.TALON_RIGHTBACK);
		talon_winch = new Talon(Ports.TALON_WINCH);

		relay_spike = new Relay(Ports.RELAY_SPIKE);

		di_nason = new DigitalInput(Ports.DI_NASON);
		di_ballsensor = new DigitalInput(Ports.DI_BALLSENSOR);
		di_limit_winchstop = new DigitalInput(Ports.DI_LIMIT_WINCHSTOP);

		joystick_drive = new DriveStick(Ports.JOYSTICK_DRIVESTICK);

		valve_loaderIn = new PneumaticValve(Ports.PNEUMATIC_LOADERIN);
		valve_loaderOut = new PneumaticValve(Ports.PNEUMATIC_LOADEROUT);
		valve_launcherIn = new PneumaticValve(Ports.PNEUMATIC_LAUNCHERIN);
		valve_launcherOut = new PneumaticValve(Ports.PNEUMATIC_LAUNCHEROUT);

		drive = new RobotDrive(talon_leftfront, talon_leftback,
				talon_rightfront, talon_rightback);
	}

	@Override
	public void disabledInit() {
		System.out.println("debug: disabledInit() called");
	}

	@Override
	public void autonomousInit() {
		System.out.println("debug: autonomousInit() called");
	}

	@Override
	public void autonomousPeriodic() {
		System.out.println("debug: autonomousPeriodic() called");
	}

	@Override
	public void teleopInit() {
		System.out.println("debug: teleopInit() called");
	}

	public boolean ballSensorReading;

	/**
	 * Teleop code goes here! This function loops endlessly during the match
	 * (hence periodic) sothere is no need for a loop inside of it.
	 */
	@Override
	public void teleopPeriodic() {
		System.out.println("debug: teleopPeriodic() called");

		// Check and managed the compressor
		checkCompressor();

		// Drive the robot according to the joystick
		drive.mecanumDrive_Polar(joystick_drive.getMagnitude(),
				joystick_drive.getDirection(), joystick_drive.getTwist());

		// check the ball sensor
		if (joystick_drive.getBallSensorOverride())
			ballSensorReading = false; // manually overriding sensor
		else
			ballSensorReading= di_ballsensor.get();
		
		//handle the joystick inputs for lifting/lowering loader
		if(joystick_drive.getLiftLoader() && ballSensorReading)
			liftLoader();
		else if(joystick_drive.getLowerLoader())
			lowerLoader();
		
		//handle the joystick inputs for firing/retracting launcher
		if(joystick_drive.getFireLauncher())
			fireLauncher();
		else if(joystick_drive.getRetractLauncher() && !di_limit_winchstop.get())
			retractLauncher();
		else
			freezeLauncher();
		
			Timer.delay(0.2); //slight delay to prevent robot from computing too quickly
	}

	@Override
	public void testInit() {
		System.out.println("debug: testInit() called");
	}

	@Override
	public void testPeriodic() {
		System.out.println("debug: testPeriodic() called");
	}

	/**
	 * Releases (fires) the launcher
	 */
	public void fireLauncher() {
		System.out.println("deug: fireLauncher() called");
		valve_launcherIn.open();
		valve_launcherOut.close();
	}

	/**
	 * Pulls back the launcher
	 */
	public void retractLauncher() {
		System.out.println("debug: retractLauncher() called");
		valve_launcherOut.open();
		valve_launcherIn.close();
		talon_winch.set(1);
	}

	/**
	 * Freezes the launcher's movement. Importantly, stops the winch.
	 */
	public void freezeLauncher() {
		System.out.println("debug: freezeLauncher() called");
		talon_winch.set(0);
	}

	/**
	 * Raises the loader.
	 */
	public void liftLoader() {
		System.out.println("debug: liftLoader() called");
		valve_loaderIn.close();
		valve_loaderOut.open();
	}

	/**
	 * Lowers the loader.
	 */
	public void lowerLoader() {
		System.out.println("debug: lowerLoader() called");
		valve_loaderIn.open();
		valve_loaderOut.close();
	}

	/**
	 * Call frequently in code. <br>
	 * Turns on compressor if air pressure falls below a useful level and turns
	 * off compressor if above a safe level. This is done using readings from
	 * the nason.
	 */
	public void checkCompressor(){
		if(!di_nason.get())
			relay_spike.set(Relay.Value.kForward);
		else
			relay_spike.set(Relay.Value.kOff);
	}
}
