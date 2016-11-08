
// This is a Tweet object. Contains the message as well as the owner
// Could later add more stats, like time of tweet.
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
	
	public String toString() {
		return owner.getID() + ": " + message;
	}
	
}
