package bang;

import java.util.ArrayList;

import bang.card.Card;

public class HelpFunctions {
	public static Player getNextPlayer(Player currentPlayer, ArrayList<Player> players) {
		int index = players.indexOf(currentPlayer);
		if (index == players.size() - 1)
			index = 0;
		else
			index++;
		return players.get(index);
	}

	public static Card peekDeck(Deck deck, Discard discard) {
		Card card = null;
		while(true) {
			try {
				card = deck.pull();
				break;
			} catch (EmptyDeckException e) {
				while(!discard.isEmpty())
					deck.add(discard.remove());
				deck.suffle();
			}
		}
		return card;
	}

	public static int getDistance(ArrayList<Player> players, Player viewer, Player viewee, int num) {
		int distance = Math.abs(players.indexOf(viewer) - players.indexOf(viewee));
		distance = Math.min(distance, num - distance);
		
		if (viewee.getMounting().hasCard("Mustang"))
			distance++;
		if (viewer.getMounting().hasCard("Scope"))
			distance--;
		
		return distance;
	}
	
	public static ArrayList<Player> getPlayersWithinRange(ArrayList<Player> players, Player viewer, int range) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for (Player otherPlayer : getOthers(viewer, players)) {
			int distance = getDistance(players, viewer, otherPlayer, players.size());
			if (distance <= range)
				targets.add(otherPlayer);
		}
		return targets;
	}
	

	public static boolean deadJob(ArrayList<Player> players, String job) {
		for (Player player: players) {
		if (player.getJob().getJob().equals(job))
			return false;
		}
		return true;
	}
	
	public static String getWinner(ArrayList<Player> players) {
		if (deadJob(players, "Sheriff") && deadJob(players, "Outlaw") && deadJob(players, "deputy"))
			return "Renegade";
		else if(deadJob(players, "Sheriff"))
			return "Outlaw";
		return "Sheriff & Deputy";
	}
	
	public static boolean isGameover(ArrayList<Player> players) {
		return !deadJob(players, "Sheriff") || (deadJob(players, "Outlaw") && deadJob(players, "Renegade"));
	}
	
	public static void discardAll(Player damagee, Discard discard) {
		Hand hand = damagee.getHand();
		while (hand.size() > 0) {
			Card card = hand.remove(0);
			discard.add(card);
		}
		
		Mounting mounting = damagee.getMounting();
		while (mounting.size() > 0) {
			Card card = mounting.remove(0);
			discard.add(card);
		}
		
		if (mounting.hasGun()) {
			Card gun = mounting.getGun();
			discard.add(gun);
		}
	}
	
	public static void deathPlayer(Player damager, Player damagee, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		damagee.setHealth(0);
		players.remove(damagee);
		if (!isGameover(players)) {
			discardAll(damagee, discard);
			if (damager != null) {
				if (damager.getJob().getJob() == "Sheriff" && damagee.getJob().getJob() == "Deputy")
					discardAll(damager, discard);
				else if (damagee.getJob().getJob() == "Outlaw") {
					damager.getHand().add(HelpFunctions.peekDeck(deck, discard));
				}
			}
		} else {
			throw new EndofGameException();
		}
	}
	
	public static void damagePlayer(Player damager, Player damagee, int damage, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		int health = damagee.getHealth() - damage;
		if (health <= 0 && players.size() > 2) {
			while (health <= 0) {
				int index = -1;	 // TODO: from server
				
				if (index == -1)
					break;
				health++;
				Card card = damagee.getHand().remove(index);
				discard.add(card);
			}
		}
		if (health <= 0)
			deathPlayer(damager, damagee, players, deck, discard);
		else
			damagee.setHealth(health);
	}
	
	public static ArrayList<Player> getOthers(Player currentPlayer, ArrayList<Player> players) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for(Player player: players) {
			if (!player.equals(currentPlayer))
				targets.add(player);
		}
		return targets;
	}
}
