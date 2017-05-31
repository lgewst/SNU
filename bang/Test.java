import java.io.*;
import bang.Game;

public class Test {

	public static void main(String[] args) {
//		Game game = new Game(Integer.parseInt(args[0]));
		try {
      ////////////////////////////////////////////////////////////////
      BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
      BufferedReader in = new BufferedReader(new FileReader("in.txt"));
      String s;

			s = in.readLine();
      out.write(s); out.newLine();
			s = args[0];
      out.write(s); out.newLine();
	//		out.write("PLAY" + args[0]); out.newLine();

			in.close();
      out.close();
      ////////////////////////////////////////////////////////////////
    } catch (IOException e) {
        System.err.println(e); // 에러가 있다면 메시지 출력
        System.exit(1);
    }
		Game game = new Game(Integer.parseInt(args[0]));
		game.play();
	}
}
