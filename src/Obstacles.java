import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;

public class Obstacles {
	public EV3GyroSensor gyro;
	public NXTLightSensor light;
	public EV3ColorSensor color;
	public EV3UltrasonicSensor eyes;
	public Obstacles(EV3GyroSensor gyro, NXTLightSensor light, 
			EV3UltrasonicSensor eyes, EV3ColorSensor color) {
		this.gyro = gyro;
		this.light = light;
		this.color = color;
		this.eyes = eyes;
	}

	public void Bridge() {
		rotate(arm.)
	}

}
