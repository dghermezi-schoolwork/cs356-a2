import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// This is the GUI for user view
// Most of this was created using WindowBuilder.
public class UserGUI {

	private JFrame frmUserGUI;
	private JTextField txtUserId;
	private JList newsFeed;
	private JList followings;
	private JScrollPane newsFeedScroll;
	private JScrollPane followingsScroll;

	private User user; // the user that the GUI is for.
	private JTextField txtTweetMessage;

	/**
	 * Create the application.
	 */
	public UserGUI(User user) {
		this.user = user;
		List<Tweet> usernewsfeed = user.getNewsfeed();
		List<Subject> userFollowings = user.getFollowings();
		newsFeed = new JList(usernewsfeed.toArray());
		followings = new JList(userFollowings.toArray());
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmUserGUI = new JFrame();
		frmUserGUI.setBounds(100, 100, 460, 439);
		frmUserGUI.setTitle(user.getID());
		frmUserGUI.getContentPane().setLayout(null);

		// This is the newsfeed scroll pane

		newsFeedScroll = new JScrollPane(newsFeed);
		newsFeedScroll.setBounds(10, 281, 424, 94);
		frmUserGUI.getContentPane().add(newsFeedScroll);

		newsFeedScroll.setViewportView(newsFeed);
		
		JLabel lblNewsFeed = new JLabel("News Feed");
		lblNewsFeed.setHorizontalAlignment(SwingConstants.CENTER);
		newsFeedScroll.setColumnHeaderView(lblNewsFeed);

		// This is the User ID field for followin a user.
		txtUserId = new JTextField();
		txtUserId.setText("User ID: ");
		txtUserId.setBounds(10, 11, 199, 43);
		frmUserGUI.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);

		// This is the follow user button
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.setBounds(235, 11, 199, 43);
		frmUserGUI.getContentPane().add(btnFollowUser);
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gets the user from the AdminControlPanel instance
				User temp = AdminControlPanel.getInstance().check(txtUserId.getText());

				// makes sure the user exists and is not the current user
				// (cannot follow yourself)
				if (user.getID().equals(txtUserId.getText())) {
					JOptionPane.showMessageDialog(frmUserGUI, "You cannot follow yourself!");
				} else if (temp != null) {

					user.follow(temp);

				} else {
					JOptionPane.showMessageDialog(frmUserGUI, "User not found!");

				}
			}
		});

		// This is the field to write a tweet.
		txtTweetMessage = new JTextField();
		txtTweetMessage.setText("Tweet Message");
		txtTweetMessage.setBounds(10, 170, 199, 94);
		frmUserGUI.getContentPane().add(txtTweetMessage);
		txtTweetMessage.setColumns(10);

		// This button posts the tweet
		JButton btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.setBounds(257, 189, 187, 56);
		frmUserGUI.getContentPane().add(btnPostTweet);
		btnPostTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.tweet(txtTweetMessage.getText());
			}
		});

		// the following list view.
		JScrollPane followingsScroll = new JScrollPane(followings);
		followingsScroll.setBounds(20, 65, 414, 94);
		frmUserGUI.getContentPane().add(followingsScroll);
		followingsScroll.setViewportView(followings);
		
		JLabel lblFollowing = new JLabel("Following");
		lblFollowing.setHorizontalAlignment(SwingConstants.CENTER);
		followingsScroll.setColumnHeaderView(lblFollowing);

	}

	public JFrame getJFrame() {
		return frmUserGUI;
	}

	public JTextField getUserID() {
		return txtUserId;
	}

	public JList getNewsFeed() {
		return newsFeed;
	}

	public JScrollPane getScrollPane() {
		return newsFeedScroll;

	}
	
	public void setScrollPane(JList jlist) {
		newsFeedScroll = new JScrollPane(jlist);
	}
	
	// updates the newsfeed to reflect changes in realtime
	public void updateNewsFeed() {
		newsFeed = new JList(user.getNewsfeed().toArray());
		newsFeedScroll = new JScrollPane(newsFeed);
		newsFeedScroll.setBounds(10, 281, 424, 94);
		frmUserGUI.getContentPane().add(newsFeedScroll);

		newsFeedScroll.setViewportView(newsFeed);
		
		JLabel lblNewsFeed = new JLabel("News Feed");
		lblNewsFeed.setHorizontalAlignment(SwingConstants.CENTER);
		newsFeedScroll.setColumnHeaderView(lblNewsFeed);
	}
	
	// updates the followings to reflect changes in realtime
	public void updateFollowings() {
		followings = new JList(user.getFollowings().toArray());
		followingsScroll = new JScrollPane(followings);
		followingsScroll.setBounds(20, 65, 414, 94);

		frmUserGUI.getContentPane().add(followingsScroll);
		followingsScroll.setViewportView(followings);
		
		JLabel lblFollowing = new JLabel("Following");
		lblFollowing.setHorizontalAlignment(SwingConstants.CENTER);
		followingsScroll.setColumnHeaderView(lblFollowing);
	}
}
