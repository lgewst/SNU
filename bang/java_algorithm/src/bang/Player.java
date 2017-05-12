package bang;

public class Player {
	private Job job;
	private Character character;
	private Hand hand;
	private int maxHealth;
	private int health;
	private Mounting mounting;
	
	Player(Job job, Character character) {
		this.job = job;
		this.character = character;
		this.maxHealth = character.getStartingHealth() + (job.getJob().equals("Sheriff") ? 1: 0);
		this.health = this.maxHealth;
		this.hand = new Hand();
		this.mounting = new Mounting();
	}
	
	public Job getJob() {
		return job;
	}
	public void setJob(Job role) {
		this.job = role;
	}
	
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public Hand getHand() {
		return hand;
	}
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
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
	public void setMounting(Mounting mounting) {
		this.mounting = mounting;
	}	
}
