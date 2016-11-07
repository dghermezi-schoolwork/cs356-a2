import java.util.ArrayList;
import java.util.List;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

// Singleton pattern
public class AdminControlPanel {

	JFrame frmAdmin;
	private JTree tree_1;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdmin = new JFrame();
		frmAdmin.setTitle("Mini Twittter - Admin");
		frmAdmin.setBounds(100, 100, 711, 474);
		frmAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdmin.getContentPane().setLayout(null);

		tree_1 = new JTree();
		DefaultTreeModel model = new DefaultTreeModel((DefaultMutableTreeNode) tree.get(0));
		tree_1.setModel(model);
		tree_1.setBounds(10, 11, 250, 413);
		frmAdmin.getContentPane().add(tree_1);
		
		txtUserId = new JTextField();
		txtUserId.setText("User ID");
		txtUserId.setBounds(270, 9, 204, 44);
		frmAdmin.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);
		
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.setBounds(484, 8, 201, 44);
		frmAdmin.getContentPane().add(btnNewButton);
		
		txtGroupId = new JTextField();
		txtGroupId.setText("Group ID");
		txtGroupId.setBounds(270, 64, 204, 44);
		frmAdmin.getContentPane().add(txtGroupId);
		txtGroupId.setColumns(10);
		
		btnAddGroup = new JButton("Add Group");
		btnAddGroup.setBounds(484, 63, 201, 44);
		frmAdmin.getContentPane().add(btnAddGroup);
		
//		DefaultTreeModel model1 = new DefaultTreeModel(new UserGroup("TEST"));
//		tree_1.setModel(model1);
//		tree_1.setBounds(10, 11, 250, 413);
//		frmAdmin.getContentPane().add(tree_1);
	}

	private static AdminControlPanel instance = null;
	private List<UserComponent> tree;

	private int userCount;
	private int groupCount;

	private final String[] POSITIVE_WORDS = { "good", "great", "excellent" };
	private JTextField txtUserId;
	private JTextField txtGroupId;
	private JButton btnAddGroup;

	private AdminControlPanel() {
		tree = new ArrayList<UserComponent>();
		tree.add(new UserGroup("Root"));
		userCount = 0;
		groupCount = 1;

		initialize();
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
		return userCount;
	}

	public int numberOfGroups() {
		return groupCount;
	}

	public int numberOfTweets() {
		int count = 0;
		for (UserComponent uc : tree) {
			if (uc instanceof User) {
				count += ((User) uc).getMessages().size();
			}
		}
		return count;
	}

	public int numberOfPositiveTweets() {
		int count = 0;
		String currentTweet;
		for (UserComponent uc : tree) {
			if (uc instanceof User) {
				for (Tweet tweet : ((User) uc).getMessages()) {
					currentTweet = tweet.getMessage().toLowerCase();
					if (currentTweet.contains(POSITIVE_WORDS[0]) || currentTweet.contains(POSITIVE_WORDS[1])
							|| currentTweet.contains(POSITIVE_WORDS[2])) {
						count++;
					}
				}
			}
		}
		return count;
	}

	public void addUser(String userID, UserGroup group) {
		for (UserComponent uc : tree) {
			if (uc.getID() == userID) {
				System.out.println("User already exists!");
				return;
			}
		}
		User newUser = new User(userID);
		tree.add(newUser);
		group.addToGroup(newUser);
		updateTree(newUser, group);
		userCount++;
	}

	public void addGroup(String groupID, UserGroup group) {

		for (UserComponent uc : tree) {
			if (uc.getID() == groupID) {
				System.out.println("Group already exists!");
				return;
			}
		}
		UserGroup newGroup = new UserGroup(groupID);
		tree.add(newGroup);
		group.addToGroup(newGroup);
		groupCount++;
		updateTree(newGroup, group);
	}
	
	public void updateTree(UserComponent child, UserGroup parent) {
		parent.add((DefaultMutableTreeNode) child);
		DefaultTreeModel model = new DefaultTreeModel((DefaultMutableTreeNode) tree.get(0));
		tree_1.setModel(model);
	}
}
