import java.util.ArrayList;


public interface lineFolower {
	
	
	public ArrayList<float[]> sweep( int degrees, int degrees_sweep, boolean toleft, int speed);
	
	public int move_arm_to_position();
	public void follow_single_line();
	
	
	
}
