package bang;

import java.util.ArrayList;
import bang.card.Card;
import org.json.simple.*;

public class Hand {
	private ArrayList<Card> hand = new ArrayList<Card>();

	public void add(Card card) {
		hand.add(card);
	}

	public Card remove(int index) {
		return hand.remove(index);
	}

	public Card peek(int index) {
		return hand.get(index);
	}

	public int size() {
		return hand.size();
	}

	public Card removeRandom() {
		int index = (int)(Math.random() * hand.size());
		return hand.remove(index);
	}

	public JSONArray toJSONArray(Player player, ArrayList<Player> otherPlayers) {
		JSONArray temp = new JSONArray();
		for(Card card: hand) {
			JSONObject json_card = new JSONObject();
			json_card.put("image", card.getImageName());
			json_card.put("activated", card.canPlay(player, otherPlayers));
			temp.add(json_card);
		}
		return temp;
	}

	public JSONArray toJSONArray() {
		JSONArray temp = new JSONArray();
		for(Card card: hand) {
			JSONObject json_card = new JSONObject();
			json_card.put("image", card.getImageName());
			json_card.put("activated", false);
			temp.add(json_card);
		}
		return temp;
	}
}
