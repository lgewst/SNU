package bang.userinterface;

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;

import bang.Player;
import bang.Hand;
import bang.Mounting;
import bang.card.Card;
import bang.Game;

public class WriteFunctions{
  BufferedWriter out = new BufferedWriter(new FileWriter("java2js.txt"));

  public void writeCard(Card card) {
    out.write(card.getImageName());
    out.newLine();
  }

  public void writePlayer(Player player) {
    JSONObject json = new JSONObject();

    out.write(json.toString());
    out.newLine();
  }

  public void writeOtherPlyaer(Player player) {
    JSONObject json = new JSONObject();

    out.write(json.toString());
    out.newLine();
  }

  public void writeGame(Game game) {
    JSONObject json = new JSONObject();

    out.write(json.toString());
    out.newLine();
  }
}
