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
    try {
      out.write(card.getImageName());
      out.newLine();
    } catch(IOException e) {
    }
  }

  public void writePlayer(Player player, ArrayList<Player> otherPlayers) {
    JSONObject json = new JSONObject();
    String s = "[";
    for(Player other: otherPlayers)
      s += "\"" + other.getCharacter().getName() + "\", ";

    if (s.equals("["))
      s = "[]";
    else
      s = s.substring(0, s.length() - 2) + "]";

    json.put("otherPlayers", s);
    json.put("job", player.getJob().toJson());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toArray());
    json.put("inHandCards", player.getHand().toArray());

    try {
      out.write(json.toString());
      out.newLine();
    } catch(IOException e) {
    }
  }

  public void writeOtherPlyaer(Player player) {
    JSONObject json = new JSONObject();

    try {
      out.write(json.toString());
      out.newLine();
    } catch(IOException e) {
    }
  }

  public void writeGame(Game game) {
    JSONObject json = new JSONObject();

    try {
      out.write(json.toString());
      out.newLine();
    } catch(IOException e) {
    }
  }
}
