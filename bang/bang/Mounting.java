package bang;

import java.util.ArrayList;
import bang.card.Card;
import org.json.simple.*;

public class Mounting {
	private Card gun = null;
	private ArrayList<Card> mounting = new ArrayList<Card>();

	public boolean hasGun() {
		return gun != null;
	}

	public void setGun(Card gun) {
		this.gun = gun;
	}

	public Card getGun() {
		return gun;
	}

	public Card removeGun() {
		Card temp = gun;
		gun = null;
		return temp;
	}

	public void add(Card card) {
		mounting.add(card);
	}

	public Card remove(int index) {
		return mounting.remove(index);
	}

	public Card peek(int index) {
		return mounting.get(index);
	}

	public boolean hasCard(String card_name) {
		for(Card card: mounting) {
			if(card.getName().equals(card_name))
				return true;
		}
		return false;
	}

	public int find(String name) {
		for (int i = 0; i < mounting.size(); i++) {
			if (mounting.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}

	public int size() {
		return mounting.size();
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		
		JSONArray weapon = new JSONArray();
		if(gun != null)
			weapon.add(gun.getImageName());
		json.put("weapon", weapon);
		
		JSONArray cond = new JSONArray();
		for(Card card: mounting)
			cond.add(card.getImageName());
		json.put("condition", cond);
		
		return json;
	}

	public JSONArray toJSONArray() {
		JSONArray json = new JSONArray();
		
		if(gun != null)
			json.add(gun.getImageName());
		
		for(Card card: mounting)
			json.add(card.getImageName());
		
		return json;
	}
}
