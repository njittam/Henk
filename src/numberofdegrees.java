import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;


public class numberofdegrees {
	NXTRegulatedMotor arm = Motor.D;
	NXTLightSensor sensor = new NXTLightSensor(SensorPort.S2);
	int middle_positon = 0;
	

	public int find_path () {
		int minpos = middle_positon - 90;
		int maxpos = middle_positon + 90;
		arm.rotateTo(minpos);
		sensor.setFloodlight(true);
		boolean klaar = false;
		arm.setSpeed(10);
		arm.forward();// TODO forward of backward?
		while (!klaar){
			SampleProvider sp = sensor.getAmbientMode();
			if (sp.sampleSize() > 80) {
				klaar = true;
				return sp.sampleSize();
			}
			arm.rotate(30);
			
		}
		arm.stop();
		return 0;
	}	
}