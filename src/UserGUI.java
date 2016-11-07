import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class UserGUI {

	JFrame frmUserGUI;
	private JTextField txtUserId;

	private User user;
	private JTextField txtTweetMessage;

	/**
	 * Create the application.
	 */
	public UserGUI(User user) {
		this.user = user;
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserGUI = new JFrame();
		frmUserGUI.setBounds(100, 100, 460, 439);
		frmUserGUI.setTitle(user.getID());
		frmUserGUI.getContentPane().setLayout(null);

		txtUserId = new JTextField();
		txtUserId.setText("User ID: ");
		txtUserId.setBounds(10, 11, 199, 43);
		frmUserGUI.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);

		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.setBounds(235, 11, 199, 43);
		frmUserGUI.getContentPane().add(btnFollowUser);
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User temp = AdminControlPanel.getInstance().check(txtUserId.getText());

				if (user.getID().equals(txtUserId.getText())) {
					JOptionPane.showMessageDialog(frmUserGUI, "You cannot follow yourself!");
				} else if (temp != null) {
					
					user.follow(temp);

				} else {
					JOptionPane.showMessageDialog(frmUserGUI, "User not found!");

				}
			}
		});
		JList Following = new JList();
		Following.setBounds(10, 65, 424, 94);
		frmUserGUI.getContentPane().add(Following);

		JList Newsfeed = new JList();
		Newsfeed.setBounds(10, 275, 424, 114);
		frmUserGUI.getContentPane().add(Newsfeed);

		txtTweetMessage = new JTextField();
		txtTweetMessage.setText("Tweet Message");
		txtTweetMessage.setBounds(10, 170, 199, 94);
		frmUserGUI.getContentPane().add(txtTweetMessage);
		txtTweetMessage.setColumns(10);

		JButton btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.setBounds(257, 189, 187, 56);
		frmUserGUI.getContentPane().add(btnPostTweet);
		btnPostTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.tweet(txtTweetMessage.getText());
			}
		});

	}
}
