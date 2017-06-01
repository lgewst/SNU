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
  BufferedWriter out;

  public void writeCard(Card card) {
    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(card.getImageName());
      out.newLine();
      out.close();
    } catch(IOException e) {
    }
  }

  public void writePlayer(Player player, ArrayList<Player> otherPlayers) {
    JSONObject json = new JSONObject();
    JSONArray others = new JSONArray();
    for(Player other: otherPlayers)
      others.add(other.getCharacter().getName());

    json.put("otherPlayers", others);
    json.put("job", player.getJob().toJson());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toJSONArray());
    json.put("inHandCards", player.getHand().toJSONArray());

    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(json.toString());
      out.newLine();
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeOtherPlyaer(Player player) {
    JSONObject json = new JSONObject();

    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(json.toString());
      out.newLine();
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeGame(Game game) {
    JSONObject json = new JSONObject();

    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(json.toString());
      out.newLine();
      out.close();
    } catch(IOException e) {
    }
  }
}
