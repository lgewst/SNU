package bang;

public class Player {
	private Job job;
	private Character character;
	private Hand hand;
	private int maxHealth;
	private int health;
	private Mounting mounting;
	private boolean canBang;
	
	Player(Job job, Character character) {
		this.job = job;
		this.character = character;
		this.maxHealth = character.getMaxHealth() + (job.getJob().equals("Sheriff") ? 1: 0);
		this.health = this.maxHealth;
		this.hand = new Hand();
		this.mounting = new Mounting();
		this.setCanBang(false);
	}
	
	public Job getJob() {
		return job;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public Mounting getMounting() {
		return mounting;
	}

	public boolean isCanBang() {
		return canBang;
	}

	public void setCanBang(boolean canBang) {
		this.canBang = canBang;
	}	
}
