import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;


public class Main {

	public static  void main(String[] args) {
		// TODO Auto-generated method stub
		// some changes
		//EV3ColorSensor sc = new EV3ColorSensor(SensorPort.S4);
		//SensorMode m =sc.getRGBMode();
		//LCD.drawString( Integer.toString(m.sampleSize()), 1, 1);
		//float[] sample = new float[m.sampleSize()];
		//m.fetchSample(sample, 0);
		Motor.D.resetTachoCount();
		Motor.D.rotateTo(50);
		movement m = new defMovement();
		m.move_according_to_arm(0);
		Delay.msDelay(5000);
		Motor.D.rotateTo(-50);
		m.move_according_to_arm(0);
		/*
		lineFolower newf = new LineFollower();
		newf.sweep(180,10,false, 2000);
		Delay.msDelay(5000);
	*/
	}
	
	
}
