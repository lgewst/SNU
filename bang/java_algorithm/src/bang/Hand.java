package bang;

import java.util.ArrayList;
import bang.card.Card;

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
}
