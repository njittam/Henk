import java.util.ArrayList;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class LineFollower{
	
	/**
	 * linefolower
	 * 
	 * @param lb dit is een class waarvan lb de superclass is.
	 */
	public void line_follower(lineBool lb){// geef nooit een lineBool mee maar een eigen class waarvan lineBool de superclass is.
		
		boolean stop = false;
		defMovement movement = new defMovement();
		while (!stop){
			int middle_position = Motor.D.getTachoCount();
			this.find_line();
			movement.move_according_to_arm(middle_position);
			this.move_forward(true);
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
		int delay = 500;
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


	private int find_line() {
		// TODO Auto-generated method stub
		numberofdegrees jelmercode = new numberofdegrees();
		return jelmercode.find_path();
		
	}
}
	/*
	private int linethickness;
	private int line_color;
	private int surrounding_color;
	EV3ColorSensor colorSensor;
	NXTRegulatedMotor right;
	NXTRegulatedMotor left;
	NXTRegulatedMotor arm;
	public LineFollower(EV3ColorSensor cs, boolean onLine, NXTRegulatedMotor l, NXTRegulatedMotor r, NXTRegulatedMotor
			a){
		this.colorSensor = cs;
		this.right = r;
		this.left = l;
		this.arm = a;
		if (onLine){
			//	measure_line_color();
			Motor.A.rotate(90);
		}else{
			//	measure_surrounding_color();
		}
	}
	/*	
	void measure_line_color(){
		SampleProvider rgbProvider = colorSensor.getRGBMode();
		line_color = colorSensor.getColorID();
	}
	void measure_surrounding_color(){
		SampleProvider rgbProvider = colorSensor.getRGBMode();
		surrounding_color = colorSensor.getColorID();
	}

	void scanline () {

	}
	enum colors {kleur1,kleur2,kleur3,kleur4};
	static int threshold = 0;
	public ArrayList<colors> convert_colors(ArrayList<float[]> list){
		int verschillendkleurtje = 1;
		ArrayList<colors> kleurenlist = new ArrayList<colors> ();
		ArrayList<int[]> temp;
		for (int i = 0; i < list.size() ; i++)
		{
			tijdelijk.add(e);
		}
		if (kleurenlist.size() == list.size())
			return kleurenlist;
		else return null;
	}

	public static ArrayList<float[]> sweep( int degrees, int degrees_sweep, boolean toleft, int speed){

		EV3ColorSensor sc = new EV3ColorSensor(SensorPort.S4);
		SensorMode m = sc.getRGBMode();
		Motor.D.setSpeed(speed);
		float[] sample = new float[m.sampleSize()];
		ArrayList<float[]> samples = new ArrayList<float[]> ();
		for (int i = 0; i < degrees;i+=degrees_sweep){
			m.fetchSample(sample, 0);
			samples.add(sample.clone());
			if (toleft)
				Motor.D.rotate(-degrees_sweep);
			else
				Motor.D.rotate(degrees_sweep);	


		}

		return samples;

	}
	 

	public LineFollower() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<float[]> sweep(int degrees, int degrees_sweep, boolean toleft,
			int speed) {
		NXTLightSensor sc = new NXTLightSensor(SensorPort.S4);
		SensorMode m = sc.getRedMode();
		Motor.D.setSpeed(speed);
		float[] sample = new float[m.sampleSize()];
		ArrayList<float[]> samples = new ArrayList<float[]> ();
		for (int i = 0; i < degrees;i+=degrees_sweep){
			m.fetchSample(sample, 0);
			samples.add(sample.clone());
			if (toleft)
				Motor.D.rotate(-degrees_sweep);
			else
				Motor.D.rotate(degrees_sweep);	


		}

		return samples;
	}

	public int move_arm_to_position() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void follow_single_line() {
		// TODO Auto-generated method stub

	}
}
/*/