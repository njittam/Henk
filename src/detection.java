import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

/**
 * een class met detection fucties.
 * 
 * als je hier een functies uit wil aanroepen doe dan het volgende: ...
 * detection een_leue_naam = new detection(); ... een_leuke_naam.fucnctie1();
 * ... een_leuke_naam.functie2(); ...
 */
public class detection {
	enum Tower {
		None, Red, Blue
	};

	double dist_toren_detectie = 0.50; // de afstand tot een toren wil die
										// gevonden worden

	/**
	 * detecteerd of er een toren is en zo ja welke
	 * 
	 * aannamens: * de arm staat in het midden.
	 * 
	 * @return de gevonden toren
	 */
	public int detect_Tower() {
		EV3ColorSensor s = new EV3ColorSensor(SensorPort.S4); // TODO goede
																// poort?
		if (!s.isFloodlightOn()) {
			s.setFloodlight(true);
		}
		EV3UltrasonicSensor s2 = new EV3UltrasonicSensor(SensorPort.S3); // TODO
																			// goede
		// poort?
		SampleProvider dpir = s2.getDistanceMode();
		float[] sample = new float[dpir.sampleSize()];
		dpir.fetchSample(sample, 0);
		float afstand = sample[0];
		SampleProvider kleur = s.getRGBMode();
		if (afstand <= this.dist_toren_detectie) {
			s.setFloodlight(false);
			int sp = kleur.sampleSize();
			float[] colorSample = new float[sp];
			kleur.fetchSample(colorSample, 0);
			System.out.println("TEST");
			System.out.println(colorSample[0]);
			System.out.println(colorSample[1]);
			System.out.println(colorSample[2]);
			if (colorSample[0] > 0.02) // TODO kleuren aanpassen
			{
				System.out.println("TESTred");
				s.close();
				s2.close();
				return Color.RED;
			} else if (colorSample[2] >= 0.01 || colorSample[1] >= 0.01) // TODO
																			// kleuren
			{
				System.out.println("TESTblue");
				s.close();
				s2.close();
				return Color.BLUE;
			} else // TODO Kleuren aanpassen
			{
				System.out.println("TESTnull");
				s.close();
				s2.close();
				return 0; //
			}
		}
		s.close();
		s2.close();
		return 0;
	}

	/**
	 * detecteerd of er een wipwap is. aannamens: * de arm staat in het midden.
	 * 
	 * @return toren gevonden
	 */
	public boolean detect_wip_wap() {
		// TODO add functie
		return false;
	}
}