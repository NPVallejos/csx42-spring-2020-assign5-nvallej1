package numberPlay.util;

// Purpose - Wrapper around each sentence in the input file
public class MyElement implements Element {
	private String sentence;

	// Purpose - store a string inside this instance
	public MyElement(String sentenceIn) {
		sentence = sentenceIn;
	}

	// Purpose - Accepts a visitor and delegates the accept(...) to each of the elements
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return sentence;
	}
}
