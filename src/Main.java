import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Main {

	public static void main(String[] args) {
		/*detection det = new detection();
		Sounds sounds = new Sounds();
		int i= 0;
		while (i < 5000000) {
			System.out.println("TEST1");
			if (det.detect_Tower() == Color.BLUE) {
				sounds.geluid();
			}
			i  += 1;
		}
		Motor.D.resetTachoCount();*/
		LineFollower lf = new LineFollower(new EV3GyroSensor(SensorPort.S1),
				new NXTLightSensor(SensorPort.S2));
		lf.line_follower(new linetest());
	}

}
