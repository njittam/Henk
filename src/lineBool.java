/**
 * zet nooit iet in deze class. of maak deze class nooit direct aan.
 * 
 * als je iets in dezde class wil gebruiken doe dan het volgende:
 * 
 * maak een nieuwe class aan met als superclass lineBool ipv java.lang.Object.
 * 
 * er staat nu extends lineBool achter je nieuwe class.
 * daarna roep je in elke constructor van je nieuwe class
 * super.(functie); aan
 * 
 * 
 * als je meer argumenten wil toevoegen aan stop_following()
 * maak hier dan een nieuwe abstracte funcie aan met de juiste aantal argumenten.
 * en 
 * 
 * 
 */
public abstract class lineBool {
	public enum functie {stop_following1}
	private functie f;
	
	public lineBool(functie f){
		this.setF(f);
	}
	/**
	 * dit geld voor alle functies.
	 * aannamens:
	 * 		* de arm staat in het midden bij aanroepen
	 * 		* de arm staat in het midden bij returnen
	 * @return true als linefollower moet stoppen.
	 */
	public abstract boolean stop_following(); //stop_following1
	
	
	
	
	
	public functie getF() {
		return f;
	}
	
	private void setF(functie f) {
		this.f = f;
	}
}
