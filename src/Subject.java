// Observer interface
public interface Subject {

	// follow a user
	public void register(Observer o);

	// not using this, but it is usually part of the pattern
	// this would be for unfollowing a user
	// public void unregister(Observer o);
	
	// when subjec tweets, notify the observers
	public void notifyObserver();
}
