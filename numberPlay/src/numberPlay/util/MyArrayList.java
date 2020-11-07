package numberPlay.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.util.StringTokenizer;

// Purpose - wrapper around an ArrayList of MyElement objects
public class MyArrayList implements Element {
	private List<Element> myElements;

	// Purpose - default constructor
	public MyArrayList() {
		myElements = new ArrayList<Element>();
	}

	// Purpose - Returns an iterator to the internal ArrayList
	public Iterator<Element> getIterator() {
		return myElements.iterator();
	}

	// Purpose - Accepts a visitor and delegates the accept(...) to each of the elements
	@Override
	public void accept(Visitor visitor) {
		for (Element element : myElements) {
			element.accept(visitor);
		}
	}

	// Purpose - Implements the builder design pattern
	public static class Builder {
		private FileProcessor fileProcessor;

		// Purpose - Default Builder constructor
		public Builder() {
			System.out.println("In Builder()...");
			fileProcessor = null;
		}

		// Purpose - Store reference to fileProcessorIn and return Builder instance
		public Builder withFileProcessor(FileProcessor fileProcessorIn) {
			System.out.println("In withFileProcessor(...) ...");
			fileProcessor = fileProcessorIn;
			return this;
		}

		// Purpose - Create and return an instance of MyArrayList
		public Element build() {
			System.out.println("In build(...) ...");
			Element myArrayList = new MyArrayList();

			/*
			** Parse input file through fileProcessor
			** - Use "." as a delimiter and StringTokenizer to
			**   split up the sentences in the input file
			** - Convert sentence to lower case
			** - Note: Input file cannot contain a newline character
			*/
			try {
				String line = null;
				while ((line = fileProcessor.poll()) != null) {
					System.out.println("\tIn fileProcessor while loop");
					StringTokenizer tokenizer = new StringTokenizer(line, ".");
					while (tokenizer.hasMoreTokens()) {
						String sentence = tokenizer.nextToken().toLowerCase();
						((MyArrayList) myArrayList).myElements.add(new MyElement(sentence));

						System.out.println("\t" + sentence);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			return myArrayList;
		}
	}

	@Override
	public String toString() {
		String retStr = new String();
		Iterator<Element> iter = getIterator();
		while(iter.hasNext()) {
			retStr += ((MyElement)iter.next()).toString();
			retStr += ".";
		}
		return retStr;
	}
}
