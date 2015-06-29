import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.NXTLightSensor;


public class Main {

	public static  void main(String[] args) {
		Sounds sounds = new Sounds();
		if () {
			sounds.geluid();
		}
		Motor.D.resetTachoCount();
		LineFollower lf = new LineFollower(new EV3GyroSensor(SensorPort.S1),new NXTLightSensor(SensorPort.S2));
		lf.line_follower(new linetest());
	}
	
	
}
		