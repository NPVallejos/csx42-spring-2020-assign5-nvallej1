package numberPlay.util;

import java.util.List;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import numberPlay.exceptions.EmptyFileException;

// Purpose - visitor that checks for mispelled words
public class SpellCheckAnalyzer implements Visitor {
	private String acceptableWordsFilename;
	private Results spellCheckResults;

	public SpellCheckAnalyzer(String acceptableWordsFilenameIn, Results spellCheckResultsIn) {
		acceptableWordsFilename = acceptableWordsFilenameIn;
		spellCheckResults = spellCheckResultsIn;
	}

	// Purpose - process and store words from acceptable words file
	// @return - an ArrayList of String's
	private List<String> processAcceptableWordsFile() {
		List<String> acceptableWords = null;
		try {
			FileProcessor fileProcessor = new FileProcessor(acceptableWordsFilename);

			acceptableWords = new ArrayList<String>();

			String word = null;
			while ((word = fileProcessor.poll()) != null) {
				acceptableWords.add(word);
			}

			fileProcessor.close();
		} catch (InvalidPathException | SecurityException | FileNotFoundException | EmptyFileException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return acceptableWords;
	}

	@Override
	public void visit(Element myElement) {
		System.out.println("In SpellCheckAnalyzer.visit(...) ...");

		// Store the sentence from myElement and trim whitespace
		String sentence = ((MyElement) myElement).toString().trim();

		// Print the sentence being parsed to cmd
		System.out.println("\tsentence: '" + sentence + "'");

		// Split sentence by whitespace
		String[] words = sentence.split(" ");

		// Process acceptable words file
		List<String> acceptableWords = processAcceptableWordsFile();

		for (String inputWord : words) {
			// For words that can be changed to acceptable words,
			// store the word and the possible acceptable words in Results.
			// Format = word::[acceptable word 1, acceptable word 2, ... ]

			// 'inputWord' length must be > 2
			if (inputWord.length() <= 2) {
				continue;
			}

			for (String acceptableWord : acceptableWords) {
				// Both word lengths must be equal
				if (inputWord.length() != acceptableWord.length()) {
					continue;
				}

				// Words must differ by exactly 1 character
				int differentNumChars = 0;
				for (int i = 0; i < acceptableWord.length(); ++i) {
					if (differentNumChars > 1) {
						break;
					}
					if (inputWord.charAt(i) != acceptableWord.charAt(i)) {
						++differentNumChars;
					}
				}
				// Store the mispelled word with its associated acceptable word
				if (differentNumChars == 1) {
					spellCheckResults.store(inputWord + ":" + acceptableWord);
				}
			}
		}
	}
}
