import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class numberofdegrees {
	public NXTLightSensor sensor1;
	//public SampleProvider sp;
	public numberofdegrees(NXTLightSensor s){
		this.sensor1 = s;
		//sp = sensor.getRedMode();
		this.sensor1.close();
	}
	

	public boolean find_path () {
		/*NXTRegulatedMotor arm = Motor.D;
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
		/*
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
		return klaar;*/
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		NXTLightSensor sensor = new NXTLightSensor(SensorPort.S2);
		int minpos = middle_positon - 90;
		int maxpos = middle_positon + 90;
		double pad_ambient = 0.45; // waarde tussen 1 en 0
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
		System.out.println("hallo dit is lijn 87");
		System.out.println(arm.getTachoCount());
		System.out.println(klaar);
		arm.stop();
		if (arm.getTachoCount() >= maxpos)
			arm.rotateTo(middle_positon);
		System.out.println("juhgfced");
		sensor.close();
		return false;
	}
	
}