package bang;

import java.util.ArrayList;
import bang.card.Card;
import org.json.simple.*;

public class Hand {
	private ArrayList<Card> hand = new ArrayList<Card>();

	public void add(Card card) {
		hand.add(card);
	}

	public Card remove(Card card) {
		hand.remove(card);
		return card;
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
			json_card.put("able", card.canPlay(player, otherPlayers));
			temp.add(json_card);
		}
		return temp;
	}

//	public JSONArray toJSONArray() {
//		JSONArray temp = new JSONArray();
//		for(Card card: hand) {
//			JSONObject json_card = new JSONObject();
//			json_card.put("image", card.getImageName());
//			json_card.put("activated", false);
//			temp.add(json_card);
//		}
//		return temp;
//	}
	
	public JSONArray toJSONArray() {
		JSONArray temp = new JSONArray();
		for(Card card: hand)
			temp.add(card.getImageName());
		return temp;
	}

	public boolean hasBang() {
		for(Card card: hand) {
			if (card.getName().equals("Bang"))
				return true;
		}
		return false;
	}

	public boolean hasMiss() {
		for(Card card: hand) {
			if (card.getName().equals("Missed"))
				return true;
		}
		return false;
	}

	public boolean hasBeer() {
		for(Card card: hand) {
			if (card.getName().equals("Beer"))
				return true;
		}
		return false;
	}

	public int getBang() {
		for(int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getName().equals("Bang"))
				return i;
		}
		return -2;
	}

	public int getMiss() {
		for(int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getName().equals("Missed"))
				return i;
		}
		return -2;
	}

	public int getBeer() {
		for(int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getName().equals("Beer"))
				return i;
		}
		return -2;
	}
}
