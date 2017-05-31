package bang.card;

import java.util.ArrayList;

public class BangDeck {
	public static ArrayList<Card> makeDeck() {
		ArrayList<Card> cards = new ArrayList<Card>();
		//Blue cards-permanent
		cards.add(new Barrel("Barrel", "Spade", 12));
		cards.add(new Barrel("Barrel", "Spade", 13));
		cards.add(new Dynamite("Dynamite", "Heart", 2));
		cards.add(new Jail("Jail", "Spade", 10));
		cards.add(new Jail("Jail", "Spade", 11));
		cards.add(new Jail("Jail", "Heart", 4));
		cards.add(new Mustang("Mustang", "Heart", 8));
		cards.add(new Mustang("Mustang", "Heart", 9));
		cards.add(new Scope("Scope", "Spade", 1));
		//Blue cards-Gun
		cards.add(new Gun("Remington", "Club", 13));
		cards.add(new Gun("Rev carbine", "Club", 1));
		cards.add(new Gun("Schofield", "Spade", 13));
		cards.add(new Gun("Schofield", "Club", 11));
		cards.add(new Gun("Schofield", "Club", 12));
		cards.add(new Gun("Volcanic", "Spade", 10));
		cards.add(new Gun("Volcanic", "Club", 10));
		cards.add(new Gun("Winchester", "Spade", 8));
		//Brown cards
		cards.add(new Bang("Bang", "Spade", 1));
		cards.add(new Bang("Bang", "Club", 2));
		cards.add(new Bang("Bang", "Club", 3));
		cards.add(new Bang("Bang", "Club", 4));
		cards.add(new Bang("Bang", "Club", 5));
		cards.add(new Bang("Bang", "Club", 6));
		cards.add(new Bang("Bang", "Club", 7));
		cards.add(new Bang("Bang", "Club", 8));
		cards.add(new Bang("Bang", "Club", 9));
		cards.add(new Bang("Bang", "Heart", 12));
		cards.add(new Bang("Bang", "Heart", 13));
		cards.add(new Bang("Bang", "Heart", 1));
		cards.add(new Bang("Bang", "Diamond", 1));
		cards.add(new Bang("Bang", "Diamond", 2));
		cards.add(new Bang("Bang", "Diamond", 3));
		cards.add(new Bang("Bang", "Diamond", 4));
		cards.add(new Bang("Bang", "Diamond", 5));
		cards.add(new Bang("Bang", "Diamond", 6));
		cards.add(new Bang("Bang", "Diamond", 7));
		cards.add(new Bang("Bang", "Diamond", 8));
		cards.add(new Bang("Bang", "Diamond", 9));
		cards.add(new Bang("Bang", "Diamond", 10));
		cards.add(new Bang("Bang", "Diamond", 11));
		cards.add(new Bang("Bang", "Diamond", 12));
		cards.add(new Bang("Bang", "Diamond", 13));

		cards.add(new Beer("Beer", "Heart", 6));
		cards.add(new Beer("Beer", "Heart", 7));
		cards.add(new Beer("Beer", "Heart", 8));
		cards.add(new Beer("Beer", "Heart", 9));
		cards.add(new Beer("Beer", "Heart", 10));
		cards.add(new Beer("Beer", "Heart", 11));

		cards.add(new Catbalou("Catbalou", "Diamond", 9));
		cards.add(new Catbalou("Catbalou", "Diamond", 10));
		cards.add(new Catbalou("Catbalou", "Diamond", 11));
		cards.add(new Catbalou("Catbalou", "Heart", 13));

		cards.add(new Duel("Duel", "Club", 8));
		cards.add(new Duel("Duel", "Diamond", 12));
		cards.add(new Duel("Duel", "Spade", 11));

		cards.add(new Gatling("Gatling", "Heart", 10));

		cards.add(new Generalstore("Generalstore", "Club", 9));
		cards.add(new Generalstore("Generalstore", "Spade", 12));

		cards.add(new Indians("Indians", "Diamond", 13));
		cards.add(new Indians("Indians", "Diamond", 1));

		cards.add(new Missed("Missed", "Club", 1));
		cards.add(new Missed("Missed", "Club", 10));
		cards.add(new Missed("Missed", "Club", 11));
		cards.add(new Missed("Missed", "Club", 12));
		cards.add(new Missed("Missed", "Club", 13));
		cards.add(new Missed("Missed", "Spade", 2));
		cards.add(new Missed("Missed", "Spade", 3));
		cards.add(new Missed("Missed", "Spade", 4));
		cards.add(new Missed("Missed", "Spade", 5));
		cards.add(new Missed("Missed", "Spade", 6));
		cards.add(new Missed("Missed", "Spade", 7));
		cards.add(new Missed("Missed", "Spade", 8));

		cards.add(new Panic("Panic", "Diamond", 8));
		cards.add(new Panic("Panic", "Heart", 11));
		cards.add(new Panic("Panic", "Heart", 12));
		cards.add(new Panic("Panic", "Heart", 1));

		cards.add(new Sallon("Sallon", "Heart", 5));

		cards.add(new Stagecoach("Stagecoach", "Spade", 9));
		cards.add(new Stagecoach("Stagecoach", "Spade", 9));

		cards.add(new Wellsfargo("Wellsfargo", "Heart", 3));
		return cards;
	}
}
