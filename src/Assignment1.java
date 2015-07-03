import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class Assignment1 {
	public NXTLightSensor light;
	EV3GyroSensor gyro;
	public boolean stop;
	public double tower_dist = 0.30;
	double pad_ambient = 0.42;
	private EV3UltrasonicSensor dist_sens;
	public Assignment1(NXTLightSensor s, EV3GyroSensor gyro, EV3UltrasonicSensor d){
		this.light = s;
		this.gyro = gyro;
		this.dist_sens = d;
	}
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Assignment1 as1 = new Assignment1(new NXTLightSensor(SensorPort.S2), new EV3GyroSensor(SensorPort.S1), new EV3UltrasonicSensor(SensorPort.S3));
		int min_follow = 5;
		int follow = 0;
		Sounds s = new Sounds();
		s.setDaemon(true);	
		Sounds s2 = new Sounds();
		s2.setDaemon(true);
		Sounds s3 = new Sounds();
		s3.setDaemon(true);	
		//s.start();
		as1.move_to_line();
		while (follow < min_follow)
			follow = as1.line_follower(false);
		s.start();
		follow = 0;
		while (follow < min_follow)
			follow = as1.line_follower(false);
		s2.start();
		follow = 0;
		while (follow < min_follow)
			follow = as1.line_follower(true);
		s3.start();

	}
	public void move_to_line(){
		int speed = 100;
		SampleProvider sp = light.getRedMode();
		float[] sample = new float[sp.sampleSize()];
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		Motor.A.forward();
		Motor.B.forward();
		sp.fetchSample(sample, 0);
		while(sample[0]< pad_ambient){
			sp.fetchSample(sample, 0);
		}
		Motor.A.stop(true);
		Motor.B.stop(true);
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
		//System.out.println(sample[0]);
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
		//System.out.println(degree - Math.abs(current_degree - start_degree));
		//System.out.println(degree + ":" + current_degree + ":" + start_degree);
		while (0.8*degree - Math.abs(current_degree - start_degree) > 0) {
			// System.out.println("waardes");
			//System.out.println(degree - Math.abs(current_degree - start_degree));
			System.out.println(degree + ":" + current_degree + ":"
					+ start_degree);
			current_degree = (int) sample[0];
			sp.fetchSample(sample, 0);
		}
		right.stop(true);
		left.stop(true);
	}

	public int line_follower(boolean follow_right_line){// geef nooit een lineBool mee maar een eigen class waarvan lineBool de superclass is.

		stop = false;
		int i =0;
		defMovement movement = new defMovement(gyro);
		while (!stop){
			i++;
			int middle_position = Motor.D.getTachoCount();
			this.move_forward(false);
			this.find_line(follow_right_line);
			Motor.A.stop(true);
			Motor.B.stop(true);
			movement.move_according_to_arm(middle_position);
			//this.move_forward(true);

			//
			//			switch(lb.getF()){  //Als je lineBool aanpast moet je deze switch case ook aanpassen.
			//			case stop_following1:
			//				stop = lb.stop_following();
			//				break;
			//			case stop_following2:
			//				stop = lb.stop_following(0);
			//			default:
			//				stop = true;
			//				break;
			//			}
		}
		Motor.A.stop(true);
		Motor.B.stop(true);
		return i;
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
		int i = 0;
		SampleProvider sp = light.getRedMode();
		SampleProvider sp_dist = this.dist_sens.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		float[] sample_dist = new float[sp_dist.sampleSize()];
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
				i++;
				sp.fetchSample(sample, 0);
				sp_dist.fetchSample(sample_dist, 0);
				System.out.println("distance:"+sample_dist[0]);
				if (sample_dist[0]<tower_dist && sample_dist[0] != 0)
					this.stop = true;
				//System.out.println(sample[0]);
				if (sample[0] > pad_ambient) {
					arm.stop();
					klaar = true;
				}
			}
			arm.stop();
			if (arm.getTachoCount() <= minpos)
				arm.rotateTo(middle_positon);
			//System.out.println("juhgfced");
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
				i++;
				sp.fetchSample(sample, 0);
				sp_dist.fetchSample(sample_dist, 0);
				System.out.println("distance:"+sample_dist[0]);
				if (sample_dist[0]<tower_dist&& sample_dist[0] != 0)
					this.stop = true;
				//System.out.println(sample[0]);
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
