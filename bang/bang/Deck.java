package bang;

import java.util.ArrayList;
import java.util.Collections;

import bang.card.Card;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	public Deck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	public Card pull() throws EmptyDeckException {
		if (deck.isEmpty())
			throw new EmptyDeckException();
		return deck.remove(deck.size() - 1);
	}
	
	public void suffle() {
		Collections.shuffle(deck);
	}
}
