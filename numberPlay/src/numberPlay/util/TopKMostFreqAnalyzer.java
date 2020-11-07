package numberPlay.util;

public class TopKMostFreqAnalyzer implements Visitor {
	private Results topKFreqWordsResults;

	public TopKMostFreqAnalyzer(Integer kIn, Results topKFreqWordsResultsIn) {
		topKFreqWordsResults = topKFreqWordsResultsIn;
		((TopKFreqWordsResults) topKFreqWordsResults).setK(kIn);
	}

	// Purpose - Parse sentence stored in myElement
	// and store the topKMostFreq words in topKFreqWordsResults
	@Override
	public void visit(Element myElement) {
		System.out.println("In TopKMostFreqAnalyzer.visit(...) ...");

		// Store the sentence from myElement and trim whitespace
		String sentence = ((MyElement) myElement).toString().trim();

		// Print the sentence being parsed to cmd
		System.out.println("\tsentence: '" + sentence + "'");

		// Split sentence by whitespace
		String[] words = sentence.split(" ");

		// Store the words inside topKFreqWordsResults
		for (String w : words) {
			topKFreqWordsResults.store(w);
		}

		System.out.println("\tPriority Queue: " + topKFreqWordsResults.toString());

		// Store top k most frequent words & then clear the priority queue
		topKFreqWordsResults.reset();
	}
}
