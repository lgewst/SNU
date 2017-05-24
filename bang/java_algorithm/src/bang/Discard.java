package bang;

import java.util.ArrayList;
import bang.card.Card;

public class Discard {

	ArrayList<Card> discard = new ArrayList<Card>();
	
	public void add(Card card) {
		discard.add(card);
	}

	public Card peek() {
		return discard.get(discard.size() - 1);
	}

	public Card pull() {
		return discard.remove(discard.size() - 1);
	}
	
	public boolean isEmpty() {
		return discard.isEmpty();
	}
}
