import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class numberofdegrees {
	public NXTLightSensor sensor;
	public SampleProvider sp;
	public numberofdegrees(NXTLightSensor s){
		this.sensor = s;
		sp = sensor.getRedMode();
	}
	

	public boolean find_path () {
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		int minpos = middle_positon - 70;
		int maxpos = middle_positon + 70;
		double pad_ambient = 0.40; // waarde tussen 1 en 0
		int armspeed = 250;
		
		boolean klaar = false;
		
		arm.setSpeed(-armspeed);
		arm.forward();// TODO forward of backward?	
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		if (sample[0] > pad_ambient) {
			arm.stop(true);
			sensor.close();
			return false;
		}/*
		while (arm.getTachoCount() >= minpos && !klaar){
			sensor.setFloodlight(true);
	        sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient) {
				klaar = true;
			}
		}
		arm.stop(true); v b*/
		
		arm.setSpeed(armspeed);
		arm.rotateTo(minpos);   
		while (!klaar){
			sensor.setFloodlight(true);
	        sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient || arm.getTachoCount() <= maxpos ) {
				klaar = true;
			}
		}
		if (!klaar)
			arm.rotateTo(middle_positon);
		arm.stop(true);
		return klaar;
	}	
}