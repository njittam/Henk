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
		double pad_ambient = 0.15; // waarde tussen 1 en 0
		int armspeed = 10;
		
		arm.rotateTo(minpos);
		sensor.setFloodlight(false);
		boolean klaar = false;
		arm.setSpeed(armspeed);
		SampleProvider sp = sensor.getAmbientMode();
		float[] sample = new float[sp.sampleSize()];
		arm.forward();// TODO forward of backward?
		while (!klaar){
			sensor.setFloodlight(false);
	        sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient || arm.getTachoCount() >= maxpos ) {
				klaar = true;
			}
		}
		arm.stop();
		return 0;
	}	
}