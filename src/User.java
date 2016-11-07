import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

// Part of the composite pattern, as well as observer pattern
public class User extends DefaultMutableTreeNode implements UserComponent, Subject, Observer {
	private String id;
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

	public void tweet(String message) {
		messages.add(new Tweet(message, this));
		notifyObserver();
	}

	public void follow(User user) {
		followings.add(user);
		user.register(this);
	}

	public void register(Observer o) {
		followers.add(o);
	}

	public void notifyObserver() {
		Tweet tweet = new Tweet(messages.get(messages.size() - 1).getMessage(), this);
		for (Observer o : followers) {
			o.update(tweet);
		}

	}

	public void update(Tweet tweet) {
		newsfeed.add(tweet);
	}
	
	public String toString() {
		return id;
	}


}
