import lejos.hardware.Button;


public class linetest extends lineBool {
	public linetest(){
		this(lineBool.functie.stop_following1);
	}
	
	public linetest(functie f) {
		super(f);
	}

	@Override
	public boolean stop_following() {
		// TODO Auto-generated method stub
		return Button.ESCAPE.isDown();
	}

	@Override
	public boolean stop_following(int i) {
		// TODO Auto-generated method stub
		return false;
	}

}
