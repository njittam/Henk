import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


public class LineFollower {
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
			measure_line_color();
			Motor.A.rotate(90);
		}else{
			measure_surrounding_color();
		}
	}
	
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
}
