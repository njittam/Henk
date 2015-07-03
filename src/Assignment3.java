import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;


public class Assignment3 {
	private static final double path = 0.42;
	private NXTLightSensor light;
	private EV3GyroSensor gyro;
	private EV3UltrasonicSensor dist_sens;
	boolean forward;
	private boolean stop = false;
	public Assignment3(NXTLightSensor s, EV3GyroSensor gyro, EV3UltrasonicSensor d){
		this.light = s;
		this.gyro = gyro;
		this.dist_sens = d;
	}
	public void set_Assignment3(NXTLightSensor s, EV3GyroSensor gyro, EV3UltrasonicSensor d){
		this.light = s;
		this.gyro = gyro;
		this.dist_sens = d;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Motor.D.resetTachoCount();
		Assignment3 as3 = new Assignment3(new NXTLightSensor(SensorPort.S2), new EV3GyroSensor(SensorPort.S1), new EV3UltrasonicSensor(SensorPort.S3));
		
	}
	
	public boolean search_pillar(){
		NXTRegulatedMotor arm = Motor.D;
		int middle_positon = 0;
		int minpos = middle_positon - 80;
		int maxpos = middle_positon + 80;
		double tower_dist = 0.42; // waarde tussen 1 en 0
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
