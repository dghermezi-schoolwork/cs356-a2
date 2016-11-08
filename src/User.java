import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;

// Part of the composite pattern, as well as observer pattern
public class User extends DefaultMutableTreeNode implements UserComponent, Subject, Observer {

	private String id;
	private UserGUI gui;

	// followers are the observers and followings are subjects, as per observer
	// pattern
	private List<Observer> followers;
	private List<Subject> followings;
	private List<Tweet> messages;
	private List<Tweet> newsfeed;

	public User(String id) {
		this.id = id;

		followers = new ArrayList<Observer>();
		followings = new ArrayList<Subject>();
		messages = new ArrayList<Tweet>();
		newsfeed = new ArrayList<Tweet>();

	}

	public String getID() {
		return id;
	}

	public List<Tweet> getNewsfeed() {
		return newsfeed;
	}

	public List<Tweet> getMessages() {
		return messages;
	}

	public List<Observer> getFollowers() {
		return followers;
	}

	public void setGUI(UserGUI gui) {
		this.gui = gui;
	}

	public UserGUI getGUI() {
		return gui;
	}

	// When a user tweets, add it to their messages, and notify their followers
	public void tweet(String message) {
		messages.add(new Tweet(message, this));
		notifyObserver();
	}

	// follows a user
	public void follow(User user) {
		for (Subject subject : followings) {
			// make sure observer isn't already following the subject
			if (subject != user && !(((User) subject).getID().equals(user.getID()))) {
				// add it to the followings and register the observer
				followings.add(user);
				user.register(this);
			}
		}
	}

	// part of the observer pattern, registers a new observer.
	public void register(Observer o) {
		followers.add(o);
	}

	// part of the observer pattern,
	// notifies the observers when a new tweet is sent out.
	public void notifyObserver() {
		Tweet tweet = new Tweet(messages.get(messages.size() - 1).getMessage(), this);
		for (Observer o : followers) {
			// Goes through each of the subject's observers and updates them
			// with the latest tweet.
			o.update(tweet);
		}
	}

	// part of the observer pattern
	// when a new tweet is pushed to the observer, update the newsfeed.
	public void update(Tweet tweet) {
		newsfeed.add(tweet);
		ListModel lm = new DefaultListModel();
		for (int i = newsfeed.size() - 1; i >= 0; i++) {
			((DefaultListModel) lm).addElement(newsfeed.get(i));
		}

		this.getGUI().newsFeed = new JList(lm);
	}

	public String toString() {
		return id;
	}

}
