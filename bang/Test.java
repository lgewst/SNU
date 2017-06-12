import java.io.*;
import bang.Game;

public class Test {

	public static void main(String[] args) {
		Game game = new Game(Integer.parseInt(args[0]));
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
		}
		game.play();
	}
}
