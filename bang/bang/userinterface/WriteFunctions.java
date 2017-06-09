package bang.userinterface;

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import bang.Player;
import bang.Game;
import bang.HelpFunctions;

public class WriteFunctions{
  BufferedWriter out;
  BufferedWriter toDebug;
  Game game;
	private HelpFunctions HelpFunctions = new HelpFunctions();

  public WriteFunctions(Game game) {
	  this.game = game;
  }

  public void writePlayer(int index) {
    ArrayList<Player> players = game.getPlayers();
    Player player = game.getPlayers_Info().get(index - 1);
    ArrayList<Player> otherPlayers = HelpFunctions.getOthers(player, players);

    JSONObject writer = new JSONObject();
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
    if (index == game.getCurrentPlayer_index())
    	json.put("inHandCards", player.getHand().toJSONArray(player, otherPlayers));
    else
    	json.put("inHandCards", player.getHand().toJSONArray());

    writer.put("type", "playerInfo");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeOtherPlyaer(int index, int write_index) {
	Player player = game.getPlayers_Info().get(index-1);
    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();
    JSONObject hand = new JSONObject();

    json.put("job", player.getJob().toJsonUnknown());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toJSONArray());
    hand.put("image", "../cards/playing card(back).jpg");
    hand.put("num", player.getHand().size());
    json.put("inHandCards", hand);

    writer.put("type", "otherPlayerInfo");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

//  public void writeAskPlay(int write_index) {
//	Player player = game.getPlayers_Info().get(write_index-1);
//    JSONObject writer = new JSONObject();
//    JSONObject json = new JSONObject();
//
//    json.put("cardList", player.getHand().toJSONArray());
//
//
//    writer.put("type", "askPlay");
//    writer.put("data", json.toString());
//    try {
//      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
//      out.write(writer.toString());
//      out.close();
//    } catch(IOException e) {
//    }
//  }
}
