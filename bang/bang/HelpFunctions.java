package bang;

import java.util.ArrayList;

import bang.userinterface.UserInterface;
import bang.card.Card;

public class HelpFunctions {
	public Player getNextPlayer(Player currentPlayer, ArrayList<Player> players) {
		int index = players.indexOf(currentPlayer);
		if (index == players.size() - 1)
			index = 0;
		else
			index++;
		return players.get(index);
	}

	public Card peekDeck(Deck deck, Discard discard) {
		Card card = null;
		while(true) {
			try {
				card = deck.pull();
				break;
			} catch (EmptyDeckException e) {
				while(!discard.isEmpty())
					deck.add(discard.pull());
				deck.suffle();
			}
		}
		return card;
	}

	public int getDistance(ArrayList<Player> players, Player viewer, Player viewee) {
		int distance = Math.abs(players.indexOf(viewer) - players.indexOf(viewee));
		distance = Math.min(distance, players.size() - distance);

		if (viewee.getMounting().hasCard("Mustang"))
			distance++;
		if (viewer.getMounting().hasCard("Scope"))
			distance--;
		// TODO: character ability
		if (viewee.getCharacter().getName().equals("Paul Regret"))
			distance++;
		if (viewer.getCharacter().getName().equals("Rose Doolan"))
			distance--;

		return distance;
	}

	public ArrayList<Player> getPlayersWithinRange(ArrayList<Player> players, Player viewer, int range) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for (Player otherPlayer : getOthers(viewer, players)) {
			int distance = getDistance(players, viewer, otherPlayer);
			if (distance <= range)
				targets.add(otherPlayer);
		}
		return targets;
	}


	public boolean deadJob(ArrayList<Player> players, String job) {
		for (Player player: players) {
		if (player.getJob().getJob().equals(job))
			return false;
		}
		return true;
	}

	public String getWinner(ArrayList<Player> players) {
		if (deadJob(players, "Sheriff") && deadJob(players, "Outlaw") && deadJob(players, "deputy"))
			return "Renegade";
		else if(deadJob(players, "Sheriff"))
			return "Outlaw";
		return "Sheriff & Deputy";
	}

	public boolean isGameover(ArrayList<Player> players) {
		return !deadJob(players, "Sheriff") || (deadJob(players, "Outlaw") && deadJob(players, "Renegade"));
	}

	public void discardAll(Player player, Discard discard) {
		Hand hand = player.getHand();
		while (hand.size() > 0) {
			Card card = hand.remove(0);
			discard.add(card);
		}

		Mounting mounting = player.getMounting();
		while (mounting.size() > 0) {
			Card card = mounting.remove(0);
			discard.add(card);
		}

		if (mounting.hasGun()) {
			Card gun = mounting.getGun();
			discard.add(gun);
		}
	}
	
	public void addAll(Player damager, Player damagee) {
		Hand damager_hand = damager.getHand();
		Hand hand = damagee.getHand();
		while (hand.size() > 0) {
			Card card = hand.remove(0);
			damager_hand.add(card);
		}

		Mounting mounting = damagee.getMounting();
		while (mounting.size() > 0) {
			Card card = mounting.remove(0);
			damager_hand.add(card);
		}

		if (mounting.hasGun()) {
			Card gun = mounting.getGun();
			damager_hand.add(gun);
		}
	}

	public void deathPlayer(Player damager, Player damagee, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {
		damagee.setHealth(0);
		players.remove(damagee);
		
		userInterface.getWriteFunctions().writeDead(damagee, damager);
		if (!isGameover(players)) {
			//TODO: character ability
			if(damager.getCharacter().equals("Vulture Sam"))
				addAll(damager, damagee);
			else
				discardAll(damagee, discard);
			
			if (damager != null) {
				if (damager.getJob().getJob() == "Sheriff" && damagee.getJob().getJob() == "Deputy")
					discardAll(damager, discard);
				else if (damagee.getJob().getJob() == "Outlaw") {
					damager.getHand().add(peekDeck(deck, discard));
				}
			}
		} else {
			throw new EndofGameException();
		}
	}

	public void damagePlayer(Player damager, Player damagee, int damage, ArrayList<Player> players, Deck deck, Discard discard, UserInterface userInterface) throws EndofGameException {		
		int health = damagee.getHealth() - damage;
		
		// TODO: character ability
		if (damagee.getCharacter().getName().equals("Bart Cassidy"))
			damagee.getHand().add(this.peekDeck(deck, discard));
		if (damagee.getCharacter().getName().equals("El Gringo"))
			damagee.getHand().add(damager.getHand().removeRandom());
		
		if (health <= 0 && players.size() > 2) {	
			while (health <= 0 && damagee.getHand().hasBeer()) {
				int index = userInterface.respondBeer(damagee);

				if (index == -1)
					break;
				health++;
				userInterface.getWriteFunctions().writeAddLife(damagee, 1, health);
				Card card = damagee.getHand().remove(index);
				discard.add(card);
			}
		}
		if (health <= 0)
			deathPlayer(damager, damagee, players, deck, discard, userInterface);
		else {
			userInterface.getWriteFunctions().writeLostLife(damagee, damage, health);			
			damagee.setHealth(health);
		}
	}

	public ArrayList<Player> getOthers(Player currentPlayer, ArrayList<Player> players) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for(Player player: players) {
			if (!player.equals(currentPlayer))
				targets.add(player);
		}
		return targets;
	}
}
