import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class Assignment1 {
	public NXTLightSensor light;
	EV3GyroSensor gyro;
	public Assignment1(NXTLightSensor s, EV3GyroSensor gyro){
		this.light = s;
		this.gyro = gyro;
	}
	/**
	 * @param args
	 */
	public void main(String[] args) {
		// TODO Auto-generated method stub
		this.line_follower(new linetest());
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
		SampleProvider sp = gyro.getAngleMode();
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

	public void line_follower(lineBool lb){// geef nooit een lineBool mee maar een eigen class waarvan lineBool de superclass is.

		boolean stop = false;
		defMovement movement = new defMovement(gyro);
		while (!stop){
			int middle_position = Motor.D.getTachoCount();
			this.move_forward(false);
			this.find_line(true);
			Motor.A.stop(true);
			Motor.B.stop(true);
			movement.move_according_to_arm(middle_position);
			//this.move_forward(true);


			switch(lb.getF()){  //Als je lineBool aanpast moet je deze switch case ook aanpassen.
			case stop_following1:
				stop = lb.stop_following();
				break;
			case stop_following2:
				stop = lb.stop_following(0);
			default:
				stop = true;
				break;
			}
		}
		Motor.A.stop();
		Motor.B.stop();
	}


	private void move_forward(boolean stop) {
		int speed = 100; //TODO kloppen deze waarden?
		int delay = 0;
		//Motor.D.rotateTo( 90,true);
		NXTRegulatedMotor left = Motor.A; //TODO kloppen deze waarden?
		NXTRegulatedMotor right = Motor.B;
		right.setSpeed(speed);
		left.setSpeed(speed);
		right.forward();
		left.forward();
		if (stop){
			Delay.msDelay(delay);
			right.stop(true);
			left.stop(true);
		}
		//Motor.D.rotateTo(0,true);
	}


	private boolean find_line(boolean follow_right_line) {
		// TODO Auto-generated method stub
		return find_path(follow_right_line);
	}

	public boolean find_path (boolean follow_right_line) {
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		int minpos = middle_positon - 80;
		int maxpos = middle_positon + 80;
		double pad_ambient = 0.42; // waarde tussen 1 en 0
		int armspeed = 200;
		boolean klaar = false;
		arm.setSpeed(armspeed);
		SampleProvider sp = light.getRedMode();
		float[] sample = new float[sp.sampleSize()];
		if (follow_right_line){
			/*
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
			*/
			arm.rotateTo(maxpos);
			arm.backward();
			while (!(klaar|| arm.getTachoCount() <= minpos)){
				sp.fetchSample(sample, 0);
				System.out.println(sample[0]);
				if (sample[0] > pad_ambient) {
					arm.stop();
					klaar = true;
				}
			}
			arm.stop();
			if (arm.getTachoCount() <= minpos)
				arm.rotateTo(middle_positon);
			System.out.println("juhgfced");
		}else{
			/*arm.backward();// TODO forward of backward?
			while (!(klaar || arm.getTachoCount() <= minpos) ){
				sp.fetchSample(sample, 0);
				System.out.println(sample[0]);
				if (sample[0] > pad_ambient ) {
					arm.stop();
					klaar = true;
				}
			}
			if (!klaar)
				arm.forward();*/
			arm.rotateTo(minpos);
			arm.forward();
			while (!(klaar|| arm.getTachoCount() >= maxpos)){
				sp.fetchSample(sample, 0);
				System.out.println(sample[0]);
				if (sample[0] > pad_ambient) {
					arm.stop();
					klaar = true;
				}
			}
			arm.stop();
			if (arm.getTachoCount() <= minpos)
				arm.rotateTo(middle_positon);
			System.out.println("juhgfced");
		}
		return klaar;
	}
}
