
public interface movement {
	/* gebruikt rotate om naar de huidige positie van de arm te draaien. 
	 * en ze de arme recht naar voren*/
	public void move_according_to_arm(int old_position);
	/* gebruikt de gyroscopp om te draaien*/
	public void rotate(int degrees);
	/* een algoritme dat de robot voorzichtig 
	 * over een wiwap laat rijden*/
	public void beweeg_over_wip_wap();
}
