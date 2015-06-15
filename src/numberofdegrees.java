import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;


public class numberofdegrees {

	

	public int find_path () {
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		NXTLightSensor sensor = new NXTLightSensor(SensorPort.S2);
		int minpos = middle_positon - 90;
		int maxpos = middle_positon + 90;
		double pad_ambient = 0.1; // waarde tussen 1 en 0
		int armspeed = 100;
		arm.rotateTo(minpos);
		//sensor.setFloodlight(true);
		boolean klaar = false;
		arm.setSpeed(armspeed);
		SampleProvider sp = sensor.getRedMode();
		float[] sample = new float[sp.sampleSize()];
		arm.forward();// TODO forward of backward?
		while (!klaar){
			//sensor.setFloodlight(true);
	        sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient || arm.getTachoCount() >= maxpos ) {
				arm.stop();
				klaar = true;
			}
		}
		arm.stop();
		if (arm.getTachoCount() >= maxpos)
			arm.rotateTo(middle_positon);
		sensor.close();
		return 0;
	}	
}