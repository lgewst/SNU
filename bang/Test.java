import java.io.*;
import bang.Game;

public class Test {

	public static void main(String[] args) {
		Game game = new Game(4);
		try {
      ////////////////////////////////////////////////////////////////
      BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
      String s = "출력 파일에 저장될 이런 저런 문자열입니다.";

      out.write(s); out.newLine();
      out.write(s); out.newLine();

      out.close();
			while(true)
			{

			}
      ////////////////////////////////////////////////////////////////
    } catch (IOException e) {
        System.err.println(e); // 에러가 있다면 메시지 출력
        System.exit(1);
    }
		game.play();
	}
}
