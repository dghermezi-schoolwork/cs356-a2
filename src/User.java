import java.util.List;

// Part of the composite pattern, as well as observer pattern
public class User implements UserComponent {
	private static String id;
	private static List<User> followers;
	private static List<User> followings;
	private static List<String> messages;


	public String getID() {
		return id;
	}

	public void follow(User user) {
		followings.add(user);
	}

	public void tweet(String message) {
		messages.add(message);
	}
	
}
