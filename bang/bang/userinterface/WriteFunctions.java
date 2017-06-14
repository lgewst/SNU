package bang.userinterface;

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import bang.Player;
import bang.card.Card;
import bang.Game;
import bang.HelpFunctions;

public class WriteFunctions{
  BufferedWriter out;
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
    json.put("mountedCards", player.getMounting().toJSONObject());
	json.put("dead", !players.contains(player));
	
    if (index == game.getCurrentPlayer_index() + 1)
    	json.put("inHandCards", player.getHand().toJSONArray());
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

  public void writePlayer(Player player) {
	    ArrayList<Player> players = game.getPlayers();
	    ArrayList<Player> otherPlayers = HelpFunctions.getOthers(player, players);

		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for(int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}
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
	    json.put("mountedCards", player.getMounting().toJSONObject());
    	json.put("inHandCards", player.getHand().toJSONArray());
    	json.put("dead", !players.contains(player));

	    writer.put("type", "playerInfo");
	    writer.put("data", json.toString());
	    try {
	      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
	      out.write(writer.toString());
	      out.close();
	    } catch(IOException e) {
	    }
	  }

  public void writeOtherPlyaer(int index, int write_index) {
	Player player = game.getPlayers_Info().get(index-1);
	ArrayList<Player> players = game.getPlayers();
    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();
    JSONObject hand = new JSONObject();

    json.put("job", player.getJob().toJsonUnknown());
    json.put("character", player.getCharacter().toJson());
    json.put("curLife", player.getHealth());
    json.put("maxLife", player.getMaxHealth());
    json.put("mountedCards", player.getMounting().toJSONObject());
    hand.put("image", "../cards/playing card(back).jpg");
    hand.put("num", player.getHand().size());
    json.put("inHandCards", hand);
	json.put("dead", !players.contains(player));

    writer.put("type", "otherPlayerInfo");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeAskPlay(Player player, ArrayList<Player> others) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();

    json.put("cardList", player.getHand().toJSONArray(player, others));

    writer.put("type", "askPlay");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeAskDiscard(int write_index) {
	Player player = game.getPlayers_Info().get(write_index-1);
    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();

    json.put("cardList", player.getHand().toJSONArray());
    json.put("limit", player.getHealth());

    writer.put("type", "askDiscard");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeRespondeBang(Player player, Player attacker, String card, int num, boolean t) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();

    json.put("who", attacker.getCharacter().getName());
    json.put("what", card);
    json.put("num", num);
    json.put("target", t);

    writer.put("type", "respondBang");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeRespondeMiss(Player player, Player attacker, String card, int num, boolean t) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();

    json.put("who", attacker.getCharacter().getName());
    json.put("what", card);
    json.put("num", num);
    json.put("target", t);

    writer.put("type", "respondMiss");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeRespondeBeer(Player player) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();

    writer.put("type", "respondBeer");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeAskTarget(Player player, ArrayList<Player> others) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();
    JSONArray targets = new JSONArray();

    for(Player target: others)
    	targets.add(target.getCharacter().getName());
    json.put("targetList", targets);

    writer.put("type", "askTarget");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeAskTargetCard(Player player, Player target) {
	int write_index = 0;
	ArrayList<Player> players_Info = game.getPlayers_Info();
	for(int i = 0; i < players_Info.size(); i++) {
		if (players_Info.get(i) == player) {
			write_index = i + 1;
			break;
		}
	}

    JSONObject writer = new JSONObject();
    JSONObject json = new JSONObject();
    JSONObject cardList = target.getMounting().toJSONObject();
    JSONObject inHand = new JSONObject();

    inHand.put("image", "../cards/playing card(back).jpg");
    inHand.put("num", target.getHand().size());

    cardList.put("inHand", inHand);

    json.put("targetCardList", cardList);
    json.put("who", target.getCharacter().getName());

    writer.put("type", "askTargetCard");
    writer.put("data", json.toString());
    try {
      out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
      out.write(writer.toString());
      out.close();
    } catch(IOException e) {
    }
  }

  public void writeLostLife(Player player, int damage, int health) {

	  int write_index = 0;
	  ArrayList<Player> players_Info = game.getPlayers_Info();
	  for(int i = 0; i < players_Info.size(); i++) {
		  if (players_Info.get(i) == player) {
			  write_index = i + 1;
			  break;
		  }
	  }

	  JSONObject writer = new JSONObject();
	  JSONObject json = new JSONObject();

	  json.put("much", damage);
	  json.put("remain", health);

	  writer.put("type", "loseLife");
	  writer.put("data", json.toString());
	  try {
	    out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
	    out.write(writer.toString());
	    out.close();
	  } catch(IOException e) {
	  }
  }

  public void writeAddLife(Player player, int damage, int health) {
	  int write_index = 0;
	  ArrayList<Player> players_Info = game.getPlayers_Info();
	  for(int i = 0; i < players_Info.size(); i++) {
		  if (players_Info.get(i) == player) {
			  write_index = i + 1;
			  break;
		  }
	  }

	  JSONObject writer = new JSONObject();
	  JSONObject json = new JSONObject();

	  json.put("much", damage);
	  json.put("remain", health);

	  writer.put("type", "addLife");
	  writer.put("data", json.toString());
	  try {
	    out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
	    out.write(writer.toString());
	    out.close();
	  } catch(IOException e) {
	  }
  }

  public void writeLoseCard(Player target, Player attacker, Card card) {
	  int write_index = 0;
	  ArrayList<Player> players_Info = game.getPlayers_Info();
	  for(int i = 0; i < players_Info.size(); i++) {
		  if (players_Info.get(i) == target) {
			  write_index = i + 1;
			  break;
		  }
	  }

	  JSONObject writer = new JSONObject();
	  JSONObject json = new JSONObject();

	  json.put("who", attacker.getCharacter().getName());
	  json.put("card", card.getName());

	  writer.put("type", "loseCard");
	  writer.put("data", json.toString());
	  try {
	    out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
	    out.write(writer.toString());
	    out.close();
	  } catch(IOException e) {
	  }
  }

  public void writeDead(Player player, Player attacker) {
	  int write_index = 0;
	  ArrayList<Player> players_Info = game.getPlayers_Info();
	  for(int i = 0; i < players_Info.size(); i++) {
		  if (players_Info.get(i) == player) {
			  write_index = i + 1;
			  break;
		  }
	  }

	  JSONObject writer = new JSONObject();
	  JSONObject json = new JSONObject();

	  if (attacker != null)
		  json.put("who", attacker.getCharacter().getName());
	  else
		  json.put("who", "Dynamite");

	  writer.put("type", "dead");
	  writer.put("data", json.toString());
	  try {
	    out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
	    out.write(writer.toString());
	    out.close();
	  } catch(IOException e) {
	  }
  }
}
