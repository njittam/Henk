import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class defMovement {
	EV3GyroSensor s;

	public defMovement(EV3GyroSensor s) {
		this.s = s;
	}

	/*
	 * gebruikt rotate om naar de huidige positie van de arm te draaien. en ze
	 * de arme recht naar voren
	 */
	public void move_according_to_arm(int middle_position) {
		// TODO Auto-generated method stub
		NXTRegulatedMotor arm = Motor.D; // TODO is dit de arm?
		// arm.setSpeed(100);
		float current_torque = arm.getPosition();
		// arm.rotateTo(middle_position - 9,true);
		int degrees = (int) (middle_position - current_torque);
		rotate(Math.abs(degrees), degrees <= 0);// TODO check of de boolean goed
		// is.
		arm.rotateTo(middle_position);
	}

	/*
	 * gebruikt de gyroscopp om te draaien true als de robot met de clock mee
	 * draait false als tgenen de klok in
	 */
	public void rotate(int degree, boolean clockwise) {
		if (degree <= 0) {
			return;
		}
		SampleProvider sp = s.getAngleMode();
		float[] sample = new float[sp.sampleSize()]; // TODO werkt dit?
		int i = 0;
		sp.fetchSample(sample, 0); // TODO moet dit 0 zijn
		System.out.println(sample[0]);
		int start_degree = (int) sample[0]; // TODO klopt dat dit de
		// draaing is
		NXTRegulatedMotor left = Motor.B; // TODO zijn dit de goede motoren?
		NXTRegulatedMotor right = Motor.A;
		int speed = 200; // TODO check of speed goed is.
		int current_degree = start_degree;
		left.setSpeed(speed);
		right.setSpeed(speed);
		if (clockwise) {

			left.forward();
			right.backward();
		} else {
			right.forward();
			left.backward();
		}
		System.out.println(degree - Math.abs(current_degree - start_degree));
		System.out.println(degree + ":" + current_degree + ":" + start_degree);
		while (0.8*degree - Math.abs(current_degree - start_degree) > 0) {
			// System.out.println("waardes");
			System.out.println(degree
					- Math.abs(current_degree - start_degree));
			System.out.println(degree + ":" + current_degree + ":"
					+ start_degree);
			current_degree = (int) sample[0];
			sp.fetchSample(sample, 0);
		}
		right.stop(true);
		left.stop(true);
	}

	/*
	 * een algoritme dat de robot voorzichtig over een wiwap laat rijden
	 */
	public void beweeg_over_wip_wap() {
		// TODO Auto-generated method stub

	}
}
