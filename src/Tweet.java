
public class Tweet {

	private String message;
	private User owner;

	public Tweet(String message, User owner) {
		this.message = message;
		this.owner = owner;
	}

	public String getMessage() {
		return message;
	}
	
	public User getOwner() {
		return owner;
	}
	
}
