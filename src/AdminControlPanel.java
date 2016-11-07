import java.util.ArrayList;
import java.util.List;

// Singleton pattern
public class AdminControlPanel {

	private static AdminControlPanel instance = null;
	private List<UserComponent> tree;

	private AdminControlPanel() {
		tree = new ArrayList<UserComponent>();
		tree.add(new UserGroup("Root"));
	}

	public static AdminControlPanel getInstance() {
		if (instance == null) {
			synchronized (AdminControlPanel.class) {
				if (instance == null) {
					instance = new AdminControlPanel();
				}
			}
		}
		return instance;
	}

	public int numberOfUsers() {
		return 1;
	}

	public int numberOfGroups() {
		return 1;
	}

	public int numberOfTweets() {
		return 1;
	}

	public int positiveTweets() {
		return 1;
	}

	public void addUser(String userID, String groupID) {
		
	}

	public void addGroup(String id, String groupID) {

	}

}
