

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

/**
 * een class met detection fucties.
 * 
 * als je hier een functies uit wil aanroepen doe dan het volgende:
 * ...
 * detection een_leue_naam = new detection();
 * ...
 * een_leuke_naam.fucnctie1();
 * ...
 * een_leuke_naam.functie2();
 * ...
 */
public class detection 
{
	enum Tower {None, Red, Blue};
	double dist_toren_detectie = 0.1; //de afstand tot een toren wil die gevonden worden
	
	/**
	 * detecteerd of er een toren is en zo ja welke
	 * 
	 * aannamens: 
	 * 		* de arm staat in het midden.
	 * @return de gevonden toren
	 */
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
        
		if (afstand <= this.dist_toren_detectie ) 
		{
			int kleur = s.getColorID();
			s.setFloodlight(false);
			if (kleur == Color.RED) //TODO kleuren aanpassen
			{
				return Tower.Red;
			}
			else if (kleur == Color.BLUE) // TODO kleuren aanpassen
			{
				return Tower.Blue;
			}
			else // TODO Kleuren aanpassen
			{
				return Tower.None;
			}
		}
		return Tower.None;
	}
	
	
	
	/**
	 * detecteerd of er een  wipwap is.
	 * aannamens:
	 * 		* de arm staat in het midden.
	 * @return toren gevonden
	 */
	public boolean detect_wip_wap()
	{
		//TODO add functie
		return false; 
	}
}