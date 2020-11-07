package numberPlay.util;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;

// Purpose - Handle storing the mispelled words with their associated
// acceptable word(s)
public class SpellCheckResults implements Results {
	private String spellCheckOutputFilename;
	private HashMap<String, List<String> > hashMap;

	public SpellCheckResults(String spellCheckOutputFilenameIn) {
		spellCheckOutputFilename = spellCheckOutputFilenameIn;
		hashMap = new HashMap<String, List<String> >();
	}

	// Purpose - store acceptable word with its associated
	// mispelled word inside hashMap
	// @param 'word' - A string with the format 'mispelledWord':'acceptableWord'
	@Override
	public void store(String word) {
		String[] words = word.split(":");
		String mispelledWord = words[0];  // key
		String acceptableWord = words[1]; // data

		List<String> acceptableWordList = null;

		if (hashMap.containsKey(mispelledWord)) {
			acceptableWordList = hashMap.get(mispelledWord);
		}
		else {
			acceptableWordList = new ArrayList<String>();
		}

		acceptableWordList.add(acceptableWord);
		hashMap.put(mispelledWord, acceptableWordList);
	}

	// Purpose - clears hashMap
	@Override
	public void reset() {
		hashMap.clear();
	}

	// Purpose - write results to output file
	@Override
	public void writeToFile() {
		System.out.println("In SpellCheckResults.writeToFile(...) ...");
		try {
			// Initialize myFileWriter
			FileWriter myFileWriter = new FileWriter(spellCheckOutputFilename);

			// Store hashMap keySet (i.e. the mispelled words)
			Set<String> mispelledWordSet = hashMap.keySet();

			// Begin writing to output file
			List<String> acceptableWordList = null;
			for (String mispelledWord : mispelledWordSet) {
				myFileWriter.write(mispelledWord + "::[");

				// Get the acceptable word list
				acceptableWordList = hashMap.get(mispelledWord);

				// Write the first acceptable word to output file
				myFileWriter.write(acceptableWordList.get(0));

				// Write the remaining acceptable words (if any)
				for (int i = 1; i < acceptableWordList.size(); ++i) {
					myFileWriter.write(", " + acceptableWordList.get(i));
				}

				myFileWriter.write("]\n");
			}

			myFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
