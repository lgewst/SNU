package bang.card;

public abstract class Card implements Playable{
	private String name;
	private String suit;
	private int value;


	public Card(String name, String suit, int value) {
		this.name = name;
		this.suit = suit;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}

	public String getImageName() {
		String s, v;
		switch(suit) {
			case "Diamond": s = "D"; break;
			case "Heart": s = "H"; break;
			case "Spade": s = "S"; break;
			case "Club": s = "C"; break;
		}
		switch(value) {
			case 13: v = "K"; break;
			case 12: v = "Q"; break;
			case 11: v = "J"; break;
			case 1: v = "A"; break;
			default: v = Integer.toString(value); break;
		}

		return name + "_" + suit + value;
	}
}
