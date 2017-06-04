package bang.userinterface;

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import bang.Player;
import bang.Game;

public class WriteFunctions{
  BufferedWriter out;
  Game game;

  public WriteFunctions(Game game) {
	  this.game = game;
  }

  public void writePlayer(Player player, ArrayList<Player> otherPlayers) {
    JSONObject json = new JSONObject();
    JSONArray others = new JSONArray();
    for(Player other: otherPlayers)
      others.add(other.getCharacter().getName());

    json.put("otherPlayers", others);
    json.put("job", player.getJob().toJsonKnown());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toJSONArray());
    json.put("inHandCards", player.getHand().toJSONArray());

    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(json.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeOtherPlyaer(int index) {
	Player player = game.getPlayers().get(index);
    JSONObject json = new JSONObject();

    json.put("job", player.getJob().toJsonUnknown());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toJSONArray());
    json.put("inHandCards", player.getHand().size());

    try {
      out = new BufferedWriter(new FileWriter("java2js.txt"));
      out.write(json.toString());
      out.close();
    } catch(IOException e) {
    }
  }
}
