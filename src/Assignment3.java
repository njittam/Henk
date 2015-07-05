import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class Assignment3 {
	public NXTLightSensor light;
	EV3GyroSensor gyro;
	public boolean stop;
	public double tower_dist = 0.30;
	double pad_ambient = 0.42;
	private EV3UltrasonicSensor dist_sens;
	boolean go_to_middle = false;
	public int some_degree;
	private float path;
	int middle_position = 0;
	int minpos = middle_position - 80;
	int maxpos = middle_position + 80;
	NXTRegulatedMotor left = Motor.B; // TODO zijn dit de goede motoren?
	NXTRegulatedMotor right = Motor.A;
	NXTRegulatedMotor arm = Motor.D;
	enum Tower {
		None, Red, Blue
	};
	public Assignment3(NXTLightSensor s, EV3GyroSensor gyro, EV3UltrasonicSensor d){
		this.light = s;
		this.gyro = gyro;
		this.dist_sens = d;
		this.middle_position = arm.getTachoCount();
	}
	public void set_Assignment3(NXTLightSensor s, EV3GyroSensor gyro, EV3UltrasonicSensor d){
		this.light = s;
		this.gyro = gyro;
		this.dist_sens = d;
		this.middle_position = arm.getTachoCount();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Motor.D.resetTachoCount();
		Assignment3 as3 = new Assignment3(new NXTLightSensor(SensorPort.S2), new EV3GyroSensor(SensorPort.S1), new EV3UltrasonicSensor(SensorPort.S3));
		boolean as_complete = false;//TODO deze wordt nog nooit op true gezet
		while (!as_complete){
			as3.search_for_pillars();
			as3.move_to_pillar();
			Tower t = as3.detect_tower();
			as3.behave_according_to_tower(t);
		}
	}
	private void behave_according_to_tower(Tower t) {
		// TODO Auto-generated method stub
		
	}
	private Tower detect_tower() {
		// TODO Auto-generated method stub
		return null;
	}
	public void move_to_pillar(){
		SampleProvider sp = this.dist_sens.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		float lowest_distance = sample[0];
		double lowest_angle = arm.getTachoCount();
		while (lowest_distance == 0){
			search_for_pillars();
			sp.fetchSample(sample, 0);
			lowest_distance = sample[0];
			lowest_angle = arm.getTachoCount();
		}
		while(lowest_distance > 0.05){
			this.move_according_to_arm(middle_position);
			this.move_forward(true);
			arm.rotateTo(minpos);
			arm.setSpeed(200);
			arm.forward();
			double lowest_angle_old = lowest_angle;
			while(arm.getTachoCount()< maxpos){
				sp.fetchSample(sample, 0);
				if (sample[0] < lowest_distance&& sample[0]!=0){
					lowest_distance = sample[0];
					lowest_angle = arm.getTachoCount();
				}
			}
			if (lowest_angle_old != lowest_angle){
				arm.rotateTo((int) lowest_angle);
			}
			else{
				while (lowest_distance == 0){
					search_for_pillars();
					sp.fetchSample(sample, 0);
					lowest_distance = sample[0];
					lowest_angle = arm.getTachoCount();
				}
			}
		}
		this.move_according_to_arm(middle_position);
	}

	public void search_for_pillars(){
		int speed = 200;
		boolean stop = false;
		int max_degrees = 50;
		int degrees = (int) (Math.random() * 2 * max_degrees - max_degrees);
		left.setSpeed(speed);
		right.setSpeed(speed);
		left.forward();
		right.forward();
		while (!stop){
			stop = this.search_pillar();
			if(this.stop){
				this.stop = false;
				left.stop(true);
				right.stop(true);
				this.rotate(180 - degrees, true);
				left.setSpeed(speed);
				right.setSpeed(speed);
				left.forward();
				right.forward();
			}
		}
		left.stop(true);
		right.stop(true);
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
		float current_torque = arm.getPosition();
		int degrees = (int) (middle_position - current_torque);
		rotate(Math.abs(degrees), degrees < 0);// TODO check of de boolean goed
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
		while (0.8*degree - Math.abs(current_degree - start_degree) > 0) {
			System.out.println(degree + ":" + current_degree + ":"
					+ start_degree);
			current_degree = (int) sample[0];
			if (this.go_to_middle){
				if (some_degree == 0){
					some_degree=current_degree % 360;
					if (some_degree == 0){
						some_degree = 1;
					}
					if (some_degree < -1)
						some_degree = 360-Math.abs(some_degree);
				}else{
					int currentdegree2 = current_degree % 360;
					if (currentdegree2 < 0)
						currentdegree2 = 360 - Math.abs(currentdegree2);
					if (Math.abs(currentdegree2 - some_degree) < 10)
						this.stop = true;
				}

			}
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
			move_according_to_arm(middle_position);
		}
		Motor.A.stop(true);
		Motor.B.stop(true);
		return i;
	}


	private void move_forward(boolean stop) {
		int speed = 100; //TODO kloppen deze waarden?
		int delay = 0;
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
	}


	private boolean find_line(boolean follow_right_line) {
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
		float tower_dist = (float) .2;
		if (follow_right_line){
			arm.rotateTo(maxpos);
			arm.backward();
			while (!(klaar|| arm.getTachoCount() <= minpos)){
				i++;
				sp.fetchSample(sample, 0);
				sp_dist.fetchSample(sample_dist, 0);
				System.out.println("distance:"+sample_dist[0]);
				if (sample_dist[0]<tower_dist && sample_dist[0] != 0)
					this.stop = true;
				if (sample[0] > pad_ambient) {
					arm.stop();
					klaar = true;
				}
			}
			arm.stop();
			if (arm.getTachoCount() <= minpos)
				arm.rotateTo(middle_positon);
		}else{
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

	public boolean search_pillar(){
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;

		double tower_dist = 0.20; // waarde tussen 1 en 0
		int armspeed = 200;
		boolean klaar = false;
		arm.setSpeed(armspeed);
		int i = 0;
		SampleProvider sp = light.getRedMode();
		SampleProvider sp_dist = this.dist_sens.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		float[] sample_dist = new float[sp_dist.sampleSize()];
		arm.rotateTo(maxpos);
		arm.backward();
		while (!(klaar|| arm.getTachoCount() <= minpos)){
			i++;
			sp.fetchSample(sample, 0);
			sp_dist.fetchSample(sample_dist, 0);
			System.out.println("distance:"+sample_dist[0]);
			if (sample[0]<this.path)
				this.stop  = true;
			if (sample_dist[0] > tower_dist&& sample_dist[0] != 0) {
				arm.stop();
				klaar = true;
			}
		}
		arm.stop();
		if (arm.getTachoCount() <= minpos)
			arm.rotateTo(middle_positon);
		return klaar;
	}
}
