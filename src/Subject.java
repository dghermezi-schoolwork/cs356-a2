// Observer interface
public interface Subject {

	public void register(Observer o);

	// not using this, but it is usually part of the pattern
	// public void unregister(Observer o);
	
	public void notifyObserver();
}
