
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
		return false;
	}

}
