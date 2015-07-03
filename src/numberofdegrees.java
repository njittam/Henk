import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;


public class numberofdegrees {
	public NXTLightSensor sensor;
	public numberofdegrees(NXTLightSensor s){
		this.sensor = s;
	}


	public boolean find_path () {
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		int minpos = middle_positon - 80;
		int maxpos = middle_positon + 80;
		double pad_ambient = 0.42; // waarde tussen 1 en 0
		int armspeed = 200;
		boolean klaar = false;
		arm.setSpeed(armspeed);
		SampleProvider sp = sensor.getRedMode();
		float[] sample = new float[sp.sampleSize()];
		arm.forward();// TODO forward of backward?
		while (!(klaar || arm.getTachoCount() >= maxpos) ){
			sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient ) {
				arm.stop();
				klaar = true;
			}
		}
		if (!klaar)
			arm.backward();
		while (!(klaar|| arm.getTachoCount() <= minpos)){
			sp.fetchSample(sample, 0);
			System.out.println(sample[0]);
			if (sample[0] > pad_ambient ) {
				arm.stop();
				klaar = true;
			}
		}
		arm.stop();
		if (arm.getTachoCount() <= minpos)
			arm.rotateTo(middle_positon);
		System.out.println("juhgfced");

		return false;
	}

}