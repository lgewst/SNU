package bang.userinterface;

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import bang.Player;
import bang.card.Card;
import bang.Game;
import bang.HelpFunctions;
import bang.Job;

public class WriteFunctions {
	BufferedWriter out;
	Game game;
	private HelpFunctions HelpFunctions = new HelpFunctions();

	public WriteFunctions(Game game) {
		this.game = game;
	}

	private JSONObject makePlayerInfo(Player player) {
		ArrayList<Player> players = game.getPlayers();
		ArrayList<Player> players_Info = game.getPlayers_Info();
		ArrayList<Player> otherPlayers = HelpFunctions.getOthers(player, players_Info);

		JSONObject json = new JSONObject();
		JSONArray others = new JSONArray();
		for (Player other : otherPlayers)
			others.add(other.getCharacter().getName());

		json.put("otherPlayers", others);
		json.put("job", player.getJob().toJsonKnown());
		json.put("character", player.getCharacter().toJson());
		json.put("curLife", player.getHealth());
		json.put("maxLife", player.getMaxHealth());
		json.put("mountedCards", player.getMounting().toJSONObject());
		json.put("dead", !players.contains(player));
		json.put("inHandCards", player.getHand().toJSONArray());

		return json;
	}

	public void writeGameScreen() {
		JSONObject writer = new JSONObject();
		JSONObject connect = new JSONObject();
		JSONArray json = new JSONArray();
		JSONObject tmp;

		ArrayList<Player> players = game.getPlayers();
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for(Player player: players_Info) {
			tmp = new JSONObject();

			if (players.contains(player))
				tmp.put("job", player.getJob().toJsonUnknown());
			else
				tmp.put("job", player.getJob().toJsonKnown());

			tmp.put("character", player.getCharacter().toJson());
			tmp.put("curLife", player.getHealth());
			tmp.put("maxLife", player.getMaxHealth());
			tmp.put("mounted", player.getMounting().toJSONArray());
			tmp.put("dead", !players.contains(player));
			tmp.put("inHand", player.getHand().size());
			tmp.put("turn", player.equals(game.getCurrentPlayer()));

			json.add(tmp);
		}

		connect.put("total", json);
		writer.put("type", "gameScreen");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_0.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writePlayer(int index) {
		ArrayList<Player> players_Info = game.getPlayers_Info();
		Player player = players_Info.get(index - 1);
		JSONObject writer = new JSONObject();

		writer.put("type", "playerInfo");
		writer.put("data", makePlayerInfo(player).toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
		writeGameScreen();
	}

	public void writePlayer(Player player) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();

		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}
		JSONObject writer = new JSONObject();
		writer.put("type", "playerInfo");
		writer.put("data", makePlayerInfo(player).toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
		writeGameScreen();
	}

	public void writeOtherPlyaer(int index, int write_index) {
		Player player = game.getPlayers_Info().get(index - 1);
		ArrayList<Player> players = game.getPlayers();
		JSONObject writer = new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject hand = new JSONObject();

		if (players.contains(player))
			json.put("job", player.getJob().toJsonUnknown());
		else
			json.put("job", player.getJob().toJsonKnown());

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
		} catch (IOException e) {
		}
	}

	public void writeAskPlay(Player player, ArrayList<Player> others) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeAskDiscard(Player player) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}

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
		} catch (IOException e) {
		}
	}

	public void writeRespondeBang(Player player, Player attacker, String card, int num, boolean t) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeRespondeMiss(Player player, Player attacker, String card, int num, boolean t) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeRespondeBeer(Player player) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeAskTarget(Player player, ArrayList<Player> others) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}

		JSONObject writer = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray targets = new JSONArray();

		for (Player target : others)
			targets.add(target.getCharacter().getName());
		json.put("targetList", targets);

		writer.put("type", "askTarget");
		writer.put("data", json.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writeAskTargetCard(Player player, Player target) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeLostLife(Player player, int damage, int health, Player user) {

		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}

		JSONObject json = new JSONObject();
		JSONObject connect = new JSONObject();

		json.put("much", damage);
		json.put("remain", health);

		connect.put("who", player.getCharacter().getName());
		connect.put("much", damage);
		connect.put("by", user.getCharacter().getName());

		JSONObject writer = new JSONObject();
		writer.put("type", "loseLife");
		writer.put("data", json.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}

		writer = new JSONObject();
		writer.put("type", "loseLife");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writeAddLife(Player player, int healing, int health, Player user) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}

		JSONObject json = new JSONObject();
		JSONObject connect = new JSONObject();

		json.put("much", healing);
		json.put("remain", health);

		connect.put("much", healing);
		connect.put("who", "all players");
		connect.put("by", user.getCharacter().getName());

		JSONObject writer = new JSONObject();
		writer.put("type", "addLife");
		writer.put("data", json.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}

		writer = new JSONObject();
		writer.put("type", "gainLife");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writeLoseCard(Player target, Player attacker, Card card) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
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
		} catch (IOException e) {
		}
	}

	public void writeDead(Player player, Player attacker) {
		int write_index = 0;
		ArrayList<Player> players_Info = game.getPlayers_Info();
		for (int i = 0; i < players_Info.size(); i++) {
			if (players_Info.get(i) == player) {
				write_index = i + 1;
				break;
			}
		}

		JSONObject json = new JSONObject();
		JSONObject connect = new JSONObject();

		if (attacker != null) {
			json.put("who", attacker.getCharacter().getName());
			connect.put("by", attacker.getCharacter().getName());
		} else {
			connect.put("by", "Dynamite");
			json.put("who", "Dynamite");
		}

		connect.put("job", player.getJob().getJob());
		connect.put("who", player.getCharacter().getName());

		JSONObject writer = new JSONObject();
		writer.put("type", "dead");
		writer.put("data", json.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(write_index) + ".txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}

		writer = new JSONObject();
		writer.put("type", "dead");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writePersonalAction(Player currentPlayer, Player targetPlayer, String card_name) {
		JSONObject connect = new JSONObject();

		connect.put("from", currentPlayer.getCharacter().getName());
		connect.put("to", targetPlayer.getCharacter().getName());
		connect.put("used", card_name);

		JSONObject writer = new JSONObject();
		writer.put("type", "personalAction");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writePublicAction(Player currentPlayer, String card_name) {
		JSONObject connect = new JSONObject();

		connect.put("who", currentPlayer.getCharacter().getName());
		connect.put("used", card_name);

		JSONObject writer = new JSONObject();
		writer.put("type", "publicAction");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}
	}

	public void writeGameover(String winner) {
		JSONObject connect = new JSONObject();

		if (winner.equals("Sheriff"))
			connect.put("winner", "Sheriff & Deputy");
		else
			connect.put("winner", winner);
		connect.put("condition", new Job(winner).getGoal());

		JSONObject writer = new JSONObject();
		writer.put("type", "gameover");
		writer.put("data", connect.toString());
		try {
			out = new BufferedWriter(new FileWriter("text/java2js_-1.txt"));
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
		}

		for(int i = 0; i < game.getPlayers_Info().size(); i++) {
			JSONObject json = new JSONObject();
			Player player = game.getPlayers_Info().get(i);

			json.put("amI", player.getCharacter().getName().equals(winner) ||
					(player.getCharacter().getName().equals("Deputy") && winner.equals("Sheriff")));
			if (winner.equals("Sheriff"))
				json.put("winner", "Sheriff & Deputy");
			else
				json.put("winner", winner);

			writer = new JSONObject();
			writer.put("type", "gameover");
			writer.put("data", json.toString());
			try {
				out = new BufferedWriter(new FileWriter("text/java2js_" + Integer.toString(i + 1) + ".txt"));
				out.write(writer.toString());
				out.close();
			} catch (IOException e) {
			}
		}
	}
}
