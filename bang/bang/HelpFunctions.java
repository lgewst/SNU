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

	public void deathPlayer(Player damager, Player damagee, ArrayList<Player> players, Deck deck, Discard discard) throws EndofGameException {
		damagee.setHealth(0);
		players.remove(damagee);
		if (!isGameover(players)) {
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
		if (health <= 0 && players.size() > 2) {
			while (health <= 0 && damagee.getHand().hasBeer()) {
				int index = userInterface.respondBeer(damagee);	 // TODO: ask beer

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

	public ArrayList<Player> getOthers(Player currentPlayer, ArrayList<Player> players) {
		ArrayList<Player> targets = new ArrayList<Player>();
		for(Player player: players) {
			if (!player.equals(currentPlayer))
				targets.add(player);
		}
		return targets;
	}
}
