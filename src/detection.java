import java.awt.Color;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class detection 
{
	enum Tower {None, Red, Blue};
	
	
	public Tower detect_Tower() 
	{
		EV3ColorSensor s = new EV3ColorSensor(SensorPort.S2); //TODO goede poort?
		if (!s.isFloodlightOn())
		{
			s.setFloodlight(true);
		}
		EV3IRSensor s2 = new EV3IRSensor(SensorPort.S3); // TODO goede poort?
		SampleProvider dpir = s2.getDistanceMode();
		float[] sample = new float[dpir.sampleSize()];
        dpir.fetchSample(sample, 0);
        float afstand = sample[1];
		if (afstand <= 0.1 ) 
		{
			int kleur = s.getColorID();
			s.setFloodlight(false);
			if (kleur == Color.RED)
			{
				return Tower.Red;
			}
			else if (kleur == Color.BLUE)
			{
				return Tower.Blue;
			}
			else
			{
				return Tower.None;
			}
		}
		return Tower.None;
	}
	
	
	
	public boolean detect_wip_wap()
	{
		//TODO add functie
		return false; 
	}
}