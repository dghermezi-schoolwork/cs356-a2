import java.util.ArrayList;
import java.util.List;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

// Singleton pattern
// NOTE: Used WindowBuilder to generate some of the Swing code
public class AdminControlPanel {

	// Swing fields
	JFrame frmAdmin;
	private JTree jtree;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	private JButton btnAddGroup;
	private JButton btnOpenUserView;
	private JButton btnShowPositivePercentage;
	private JButton btnShowMessagesTotal;
	private JButton btnShowUserTotal;
	private JButton btnShowGroupTotal;

	// instance. part of the singleton pattern
	private static AdminControlPanel instance = null;

	// i do not use a JTree for this because it's easier to access items this
	// way
	// also, I did the GUI last...
	private List<UserComponent> tree;

	// these are used to keep track of # of users and groups
	private int userCount;
	private int groupCount;

	// the list of positive words
	private final String[] POSITIVE_WORDS = { "good", "great", "excellent" };

	// Private constructor. part of singleton pattern.
	private AdminControlPanel() {
		tree = new ArrayList<UserComponent>();
		tree.add(new UserGroup("Root")); // adds the Root group
		userCount = 0;
		groupCount = 0; // Root is not counted in the group count.

		initialize();
	}

	// gets the instance. part of singleton pattern
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdmin = new JFrame();
		frmAdmin.setTitle("Mini Twittter - Admin");
		frmAdmin.setBounds(100, 100, 711, 474);
		frmAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdmin.getContentPane().setLayout(null);

		// create the JTree and add the root node
		jtree = new JTree();
		DefaultTreeModel model = new DefaultTreeModel((DefaultMutableTreeNode) tree.get(0));
		jtree.setModel(model);
		jtree.setBounds(10, 11, 250, 413);
		frmAdmin.getContentPane().add(jtree);

		// This is the User ID text field for adding users.
		txtUserId = new JTextField();
		txtUserId.setText("User ID");
		txtUserId.setBounds(270, 9, 204, 44);
		frmAdmin.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);

		// This is the "Add User" button
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(484, 8, 201, 44);
		frmAdmin.getContentPane().add(btnAddUser);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add the user with id entered in user id field
				addUser(txtUserId.getText());
			}
		});

		// This is the Group ID text field for adding groups.
		txtGroupId = new JTextField();
		txtGroupId.setText("Group ID");
		txtGroupId.setBounds(270, 64, 204, 44);
		frmAdmin.getContentPane().add(txtGroupId);
		txtGroupId.setColumns(10);

		// This is the "Add Group" button
		btnAddGroup = new JButton("Add Group");
		btnAddGroup.setBounds(484, 63, 201, 44);
		frmAdmin.getContentPane().add(btnAddGroup);
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add the group with id entered in group id field
				addGroup(txtGroupId.getText());
			}
		});

		// This is the button that opens the user view
		btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.setBounds(270, 119, 415, 44);
		frmAdmin.getContentPane().add(btnOpenUserView);
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openUserView();

			}

		});

		// This button shows the number of positive tweets.
		btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.setBounds(484, 380, 201, 44);
		frmAdmin.getContentPane().add(btnShowPositivePercentage);
		btnShowPositivePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberOfPositiveTweets();

			}
		});

		// This button shows the total number of tweets
		btnShowMessagesTotal = new JButton("Show Messages Total");
		btnShowMessagesTotal.setBounds(270, 380, 201, 44);
		frmAdmin.getContentPane().add(btnShowMessagesTotal);
		btnShowMessagesTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberOfTweets();
			}
		});

		// This button shows the total number of users.
		btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.setBounds(270, 325, 201, 44);
		frmAdmin.getContentPane().add(btnShowUserTotal);
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberOfUsers();

			}
		});

		// This button shows the total number of groups.
		btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.setBounds(481, 325, 201, 44);
		frmAdmin.getContentPane().add(btnShowGroupTotal);
		btnShowGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberOfGroups();
			}
		});
	}

	// This expands the nodes in the JTree so the admin can clearly see the
	// hierarchy.
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public int numberOfUsers() {
		JOptionPane.showMessageDialog(frmAdmin, "Total users: " + userCount);
		return userCount;
	}

	public int numberOfGroups() {
		JOptionPane.showMessageDialog(frmAdmin, "Total groups: " + groupCount);
		return groupCount;
	}

	public int numberOfTweets() {
		int count = 0;

		// iterates through each UserComponent in the arraylist
		for (UserComponent uc : tree) {
			// if the component is a user, then count how many messages they've
			// tweeted
			if (uc instanceof User) {
				count += ((User) uc).getMessages().size();
			}
		}
		JOptionPane.showMessageDialog(frmAdmin, "Total messages: " + count);
		return count;
	}

	public int numberOfPositiveTweets() {
		int count = 0;
		String currentTweet;

		// same as above, but checks against the positive words array
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

		JOptionPane.showMessageDialog(frmAdmin, "Positive messages: " + count);
		return count;
	}

	// This is for use with the GUI, It adds a new user to the tree
	public void addUser(String userID) {

		// This variable is the selected node
		DefaultMutableTreeNode selection = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();

		// We can only add users to groups, so if a user is a selected we will
		// just add to the same group.
		if (selection instanceof User) {
			selection = (DefaultMutableTreeNode) selection.getParent();
		} else if (!(selection instanceof UserGroup)) {
			// otherwise if it's not a group, then it might be null or something
			// weird happened, just add to root.
			selection = (DefaultMutableTreeNode) tree.get(0);
		}

		// now that we know which group to add the user to, we add the new user
		// to the selected group
		addUser(userID, (UserGroup) selection);
	}

	// given a user ID and a group, make a new user in that group.
	public void addUser(String userID, UserGroup group) {

		// Make sure the user doesn't already exist
		for (UserComponent uc : tree) {
			if (uc.getID().equals(userID)) {
				JOptionPane.showMessageDialog(frmAdmin, "User already exists!");
				System.out.println("User already exists!");
				return;
			}
		}

		// Then create the new user, add it to the arraylist, JTree, and the
		// group
		User newUser = new User(userID);
		tree.add(newUser);
		group.addToGroup(newUser);
		updateTree(newUser, group);
		userCount++;
	}

	
	
	// these next two methods are the same as the previous two, but for creating groups instead of users.
	 
	public void addGroup(String groupID) {
		DefaultMutableTreeNode selection = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
		if (selection instanceof User) {
			selection = (DefaultMutableTreeNode) selection.getParent();
		} else if (!(selection instanceof UserGroup)) {
			selection = (DefaultMutableTreeNode) tree.get(0);
		}
		addGroup(groupID, (UserGroup) selection);
	}
		
	public void addGroup(String groupID, UserGroup group) {
		for (UserComponent uc : tree) {
			if (uc.getID().equals(groupID)) {
				System.out.println("Group already exists!");
				JOptionPane.showMessageDialog(frmAdmin, "Group already exists!");
				return;
			}
		}
		UserGroup newGroup = new UserGroup(groupID);
		tree.add(newGroup);
		group.addToGroup(newGroup);
		groupCount++;
		updateTree(newGroup, group);
	}
	

	
	// adds the child to the parent. child can be a new group or user, parent must be a group
	// this updates the JTree and expands the nodes to reflect the changes in the GUI.
	public void updateTree(UserComponent child, UserGroup parent) {
		parent.add((DefaultMutableTreeNode) child);
		DefaultTreeModel model = new DefaultTreeModel((DefaultMutableTreeNode) tree.get(0));
		jtree.setModel(model);
		expandAllNodes(jtree, 0, jtree.getRowCount());
	}

	// opens the user view of the selected user
	private void openUserView() {
		// get the selection and check to make sure a user was selected
		DefaultMutableTreeNode selection = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
		if (!(selection instanceof User)) {
			JOptionPane.showMessageDialog(frmAdmin, "Please select a user before clicking this button!");
			return;
		// get/create a gui for that specific user.	
		} else {
			UserGUI gui = ((User) selection).getGUI();
			if (((User) selection).getGUI() == null) {
				gui = new UserGUI((User) selection);
			}
			// and make it visible (open a new window)
			gui.frmUserGUI.setVisible(true);
		}

	}

	// checks if the user is in the tree already, and returns it if so.
	public User check(String id) {
		for (UserComponent uc : tree) {
			if (uc instanceof User) {
				if (uc.getID().equals(id)) {
					return (User) uc;
				}
			}
		}
		return null;
	}
}
