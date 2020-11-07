package numberPlay.util;

import java.io.IOException;

// Purpose - To be implemented by the following classes
// 	- TopKFreqWordsResults
//  - SpellCheckResults
public interface Results {
	// Purpose - store word
	public void store(String word);
	// Purpose - reset internal data structure
	public void reset();
	// Purpose - write results to output file
	public void writeToFile();
}
