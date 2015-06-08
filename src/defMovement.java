import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class defMovement implements movement {

	@Override
	public void move_according_to_arm(int middle_position) {
		// TODO Auto-generated method stub
		NXTRegulatedMotor arm = Motor.D; //TODO is dit de arm?
		float current_torque = arm.getPosition();
		int degrees = (int) (middle_position - current_torque);
		rotate(Math.abs(degrees),degrees <= 0);// TODO check of de boolean goed is.
		arm.rotateTo(middle_position);
	}

	@Override
	public void rotate(int degrees, boolean clockwise) {
		if (degrees <= 0){
			return;
		}				

		EV3GyroSensor s = new EV3GyroSensor(SensorPort.S1);
		SampleProvider sp = s.getAngleMode();
		float[] sample = new float[sp.sampleSize()]; //TODO werkt dit?
		int i = 0;
		sp.fetchSample(sample, 0);  // TODO moet dit 0 zijn?
		//int sample_size = sp.sampleSize();
		int start_degree = (int) sample[0]; // TODO klopt dat dit de draaing is?
		NXTRegulatedMotor left = Motor.B; // TODO zijn dit de goede motoren?
		NXTRegulatedMotor right =  Motor.A;
		int speed = 100; // TODO check of speed goed is.
		int delay = 1000; // TODO check of waarde goed is.
		int current_degree = 0;
		while(degrees - Math.abs(current_degree  - start_degree) >= 0)
		{
			//LCD.drawChar(c, x, y);
			LCD.drawInt(i++, 2, 2);
			LCD.drawInt(start_degree, 3, 3);
			current_degree = (int) sample[0];
			sp.fetchSample(sample, 0);
			if (clockwise){
				left.setSpeed(speed);
				right.setSpeed(speed);
				left.forward();
				right.backward();
				//Delay.msDelay(delay); // TODO deze weghalen?
				//right.stop(true);
				//left.stop(true);

			}else {
				right.setSpeed(speed);
				left.setSpeed(speed);
				right.forward();
				left.backward();
				//Delay.msDelay(delay);
				//right.stop(true);
				//left.stop(true);
			}                                                                                                                                                                                                  
		}
		right.stop(true);
		left.stop(true);
		s.close();
		/*
				while(degrees - Math.abs(Math.abs(current_degree)  - start_degree) >= 0){
					if (clockwise){
						left.setSpeed(speed);
						right.setSpeed(speed);
						left.forward();
						right.backward();
						Delay.msDelay(delay); // TODO deze weghalen?
						right.stop();
						left.stop();
					} else {
							right.setSpeed(speed);
							left.setSpeed(speed);
							right.forward();
							left.backward();
							Delay.msDelay(delay);
							right.stop();
							left.stop();
					}
					//SampleProvider sp2 = s.getAngleMode();
					//sample_size = sp.sampleSize();
					System.out.println(sample_size);
					sp.fetchSample(sample, 0);
					current_degree = (int) sample[0];
					s.close();

				}*/
	}

	@Override
	public void beweeg_over_wip_wap() {
		// TODO Auto-generated method stub

	}
}
