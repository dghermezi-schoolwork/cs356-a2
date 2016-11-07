import java.util.ArrayList;
import java.util.List;

public class UserGroup implements UserComponent {
	private String id;
	private List<UserComponent> members = new ArrayList<UserComponent>();

	public UserGroup(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void add(UserComponent uc) {
		members.add(uc);
	}
}
