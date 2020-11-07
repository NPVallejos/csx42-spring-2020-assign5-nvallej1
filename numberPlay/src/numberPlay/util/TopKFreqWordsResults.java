package numberPlay.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

import java.io.FileWriter;
import java.io.IOException;

// Purpose - Store the top k frequent words
public class TopKFreqWordsResults implements Results {
	private String topKOutputFilename;
	private Integer k;
	private Queue<WordCount> pQ;
	private List<String> topKFreqWordList;

	public TopKFreqWordsResults(String topKOutputFilenameIn) {
		topKOutputFilename = topKOutputFilenameIn;
		k = null;
		pQ = new PriorityQueue<WordCount>(new WordCountComparator());
		topKFreqWordList = new ArrayList<String>();
	}

	// Purpose - set k value
	public void setK(Integer kIn) {
		k = kIn;
	}

	// Purpose - store top k most frequent words from the
	//           priority queue into 'topKFreqWordList' ArrayList
	// *Helper method for reset()*
	public void storeTopKFreqWords() {
		WordCount wc = null;
		int count = 0;
		while ( ((wc = pQ.poll()) != null) && count < k) {
			topKFreqWordList.add(wc.getWord());
			++count;
		}
	}

	// Purpose - store 'word' into priority queue 'pQ'
	// Note: 'word' is stored inside a WordCount object and
	// then is inserted into the 'pQ'
	@Override
	public void store(String word) {
		WordCount newWord = new WordCount(word, 1);

		// (src - https://piazza.com/class/k5k3yuyx97612d?cid=452)
		// First, check if 'word' is already stored in the priority queue
		if (pQ.contains(newWord)) {
			// Increment the frequency for 'word' by 1
			// Store all objects in the priority queue into 'wordCountList'
			List<WordCount> wordCountList = new ArrayList<WordCount>();

			WordCount wc = null;
			while ((wc = pQ.poll()) != null) {
				if (wc.getWord().equals(word)) {
					wc.incrementCount();
				}
				wordCountList.add(wc);
			}

			// Now the priority queue is empty.
			// We have updated the frequency for 'word'.
			// We now add all the WordCount obj's back into the priority queue.
			for (WordCount w : wordCountList) {
				pQ.add(w);
			}
		}
		else {
			// 'pQ' does not contain 'word'
			// add 'newWord' to 'pQ'
			pQ.add(newWord);
		}
	}

	// Purpose - storeTopKFreqWords() & clear the priority queue
	@Override
	public void reset() {
		storeTopKFreqWords();
		pQ.clear();
	}

	// Purpose - write results to output file
	@Override
	public void writeToFile() {
		System.out.println("In TopKFreqWordsResults.writeToFile(...) ...");
		try {
			// Initialize myFileWriter
			FileWriter myFileWriter = new FileWriter(topKOutputFilename);

			// Persist top k words from wordFreq to output file per sentence
			for (int i = 0; i < topKFreqWordList.size(); ++i) {
				if ( ((i+1) % k) == 0) {
					myFileWriter.write(", " + topKFreqWordList.get(i) + "]\n");
				}
				else if ( ((i+1) % k) == 1) {
					myFileWriter.write("[" + topKFreqWordList.get(i));

					if (i == topKFreqWordList.size()-1) {
						myFileWriter.write("]");
					}
				}
				else {
					myFileWriter.write(", " + topKFreqWordList.get(i));
				}
			}

			// Close myFileWriter
			myFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public String toString() {
		return pQ.toString();
	}
}

class WordCount {
	private String word;
	private Integer count;

	public WordCount(String wordIn, Integer countIn) {
		word = wordIn;
		count = countIn;
	}

	public String getWord() {
		return word;
	}

	public Integer getCount() {
		return count;
	}

	public void incrementCount() {
		++count;
	}

	// Two WordCount's are the same if they have same 'word'
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof WordCount)) return false;
		WordCount oWordCount = (WordCount) o;
		return oWordCount.word.equals(word);
	}

	@Override
	public String toString() {
		return "WordCount["+word+", "+count+"]";
	}
}

class WordCountComparator implements Comparator<WordCount> {
	@Override
	public int compare(WordCount w1, WordCount w2) {
		int w1Count = w1.getCount();
		int w2Count = w2.getCount();
		if (w1Count == w2Count) {
			return 0;
		}
		if (w1Count < w2Count) {
			return 1; // higher frequency => higher priority.
		}
		return -1;
	}
}
