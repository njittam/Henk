import lejos.hardware.Sound;


public class Sounds {


	public void playmusic (int[] freqs, int length) {
		for (int i = 0; i < freqs.length ; i++) {
			Sound.playNote(Sound.PIANO, freqs[i], length);
		}
	}
	public void geluid(){
		int A5 = 880;
		int B5 = 988;
		int D6 = 1175;
		int E6 = 1319;
		int CH6 = 1109;
		int FH6 = 1480;
		int A6 = 1760;
		int[] freqs1 = {A5, B5, D6, B5};
		playmusic(freqs1, 125);
		int[] freqs2 = {FH6, FH6};
		playmusic(freqs2, 375);
		int[] freqs3 = {E6};
		playmusic(freqs3, 750);
		int[] freqs4 = {A5, B5, CH6, A5};
		playmusic(freqs4, 125);
		int[] freqs5 = {E6, E6};
		playmusic(freqs5, 375);
		int[] freqs6 = {D6, CH6};
		playmusic(freqs6, 125);
		int[] freqs7 = {B5};
		playmusic(freqs7, 500);
		playmusic(freqs4, 125);
		int[] freqs8 = {D6};
		playmusic(freqs8, 500);
		int[] freqs9 = {E6, CH6, B5};
		playmusic(freqs9, 250);
		int[] freqs10 = {A5};
		playmusic(freqs10, 500);
		playmusic(freqs10, 250);
		int[] freqs11 = {E6};
		playmusic(freqs11, 500);
		int[] freqs12 = {D6};
		playmusic(freqs12, 1000);
		playmusic(freqs1, 125);
		playmusic(freqs2, 375);
		playmusic(freqs3, 750);
		playmusic(freqs4, 125);
		int[] freqs13 = {A6};
		playmusic(freqs13, 500);
		int[] freqs14 = {CH6, D6};
		playmusic(freqs14, 250);
		int[] freqs15 = {CH6};
		playmusic(freqs15, 125);
		playmusic(freqs7, 375);
		playmusic(freqs4, 125);
		playmusic(freqs8, 500);
		playmusic(freqs9, 250);
		playmusic(freqs10, 500);
		playmusic(freqs10, 250);
		int[] freqs16 = {E6, D6};
		playmusic(freqs16, 250);
		playmusic(freqs12, 1000);
	}
}
