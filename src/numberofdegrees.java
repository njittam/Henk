import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class numberofdegrees {

	

	public int find_path () {
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		int minpos = middle_positon - 90;
		int maxpos = middle_positon + 90;
		double pad_ambient = 0.40; // waarde tussen 1 en 0
		int armspeed = 100;
		arm.rotateTo(minpos);
		boolean klaar = false;
		arm.setSpeed(armspeed);
		arm.forward();// TODO forward of backward?	
		NXTLightSensor sensor = new NXTLightSensor(SensorPort.S2);
		SampleProvider sp = sensor.getRedMode();
		float[] sample = new float[sp.sampleSize()];
		
		while (!klaar){
			sensor.setFloodlight(true);
	        sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient || arm.getTachoCount() >= maxpos ) {
				klaar = true;
			}
		}
		if (arm.getTachoCount() >= maxpos)
			arm.rotateTo(middle_positon);
		arm.stop();
		sensor.close();
		return 0;
	}	
}