package numberPlay.util;

// Purpose - To be implemented by MyArrayList and MyElement. To be visted by visitors
public interface Element {
	// Purpose - Accepts a visitor and delegates the accept(...) to each of the elements
	public void accept(Visitor visitor);
}
