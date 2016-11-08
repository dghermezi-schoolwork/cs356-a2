import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.xml.internal.ws.api.Component;

// Part of composite pattern
// represents a group
public class UserGroup extends DefaultMutableTreeNode implements UserComponent {

	private String id;
	private List<UserComponent> members;

	public UserGroup(String id) {
		this.id = id;
		members = new ArrayList<UserComponent>();
	}

	public String getID() {
		return id;
	}

	public List<UserComponent> getMembers() {
		return members;
	}

	public void addToGroup(UserComponent uc) {
		members.add(uc);
	}

	public String toString() {
		return id;
	}

}
