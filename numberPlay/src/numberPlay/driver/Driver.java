package numberPlay.driver;

// Util class imports
import numberPlay.util.FileProcessor;
import numberPlay.util.Element;
import numberPlay.util.MyElement;
import numberPlay.util.MyArrayList;
import numberPlay.util.Visitor;
import numberPlay.util.Results;
import numberPlay.util.SpellCheckResults;
import numberPlay.util.SpellCheckAnalyzer;
import numberPlay.util.TopKMostFreqAnalyzer;
import numberPlay.util.TopKFreqWordsResults;

// Command arg validator
import numberPlay.validator.CommandArgHandler;

// Custom exceptions
import numberPlay.exceptions.EmptyFileException;
import numberPlay.exceptions.InvalidArgNameException;
import numberPlay.exceptions.NumberOfArgsException;

// Other exceptions
import java.lang.IllegalArgumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class Driver {
	private static void runAnalysis(FileProcessor fileProcessor, Visitor... visitors) {
		System.out.println("In runAnalysis...");
		Element myArrayList = new MyArrayList.Builder()
			.withFileProcessor(fileProcessor)
			.build();

		for (Visitor visitor : visitors) {
			myArrayList.accept(visitor);
		}
	}

	private static void persistResults(Results... analysisResults) {
		for (Results results : analysisResults) {
			results.writeToFile();
		}
	}

	public static void main(String[] args) {
		// Command-line args validation.
		try {
			int numberOfRequiredArgs = 5;
			CommandArgHandler cmdArgHandler = new CommandArgHandler(args.length, args, numberOfRequiredArgs);
		}
		catch (InvalidArgNameException | IllegalArgumentException | NumberOfArgsException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Command-line parsing and initialization of following variables.
		// 	1. inputFilename.
		//	2. acceptableWordsFilename.
		// 	3. k.
		// 	4. topKOutputFilename.
		// 	5. spellCheckOutputFilename.
		String inputFilename = args[0];
		String acceptableWordsFilename = args[1];
		Integer k = Integer.parseInt(args[2]);
		String topKOutputFilename = args[3];
		String spellCheckOutputFilename = args[4];

		try {
			FileProcessor fileProcessor = new FileProcessor(inputFilename);

			Results topKFreqWordsResults = new TopKFreqWordsResults(topKOutputFilename);
			Visitor topKMostFreqAnalyzer = new TopKMostFreqAnalyzer(k, topKFreqWordsResults);

			Results spellCheckResults = new SpellCheckResults(spellCheckOutputFilename);
			Visitor spellCheckAnalyzer = new SpellCheckAnalyzer(acceptableWordsFilename, spellCheckResults);

			runAnalysis(fileProcessor, topKMostFreqAnalyzer, spellCheckAnalyzer);

			persistResults(topKFreqWordsResults, spellCheckResults);
		} catch (InvalidPathException | SecurityException | FileNotFoundException | EmptyFileException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
