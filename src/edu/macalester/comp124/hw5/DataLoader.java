package edu.macalester.comp124.hw5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Simplify the ridiculously difficult/error prone process of reading text
 * from a file in Java.
 * @author baylor
 */
public class DataLoader
{
	static public List<String> loadLinesFromFile(String fullyQualifiedName)
	{
		File f = new File(fullyQualifiedName);
		List<String> lines = new ArrayList();
        try
		{
			FileInputStream fileReader = new FileInputStream(fullyQualifiedName);
			Scanner parser = new Scanner(fileReader);
			while (parser.hasNext())
			{
				String line = parser.nextLine();
                lines.add(line);
            }
            parser.close();
        }
        catch (FileNotFoundException e)
		{
//			System.out.println("Cannot find file: " + fullyQualifiedName);
			System.out.println("Cannot find file: " + f.getAbsolutePath());
			return null;
		}
        catch (Exception e)
		{
			return null;
        }

		return lines;
	}

	/**
	 * Returns a set of sentences from a file. Sentences are lines delimited
	 * by a new line or a sentence-ender (.?!).
	 * @param fullyQualifiedName
	 * @return List of sentences (i.e., lines split into sentences)
	 */
	static public List<String> loadSentencesFromFile(String fullyQualifiedName)
	{
		List<String> lines = loadLinesFromFile(fullyQualifiedName);
		if (null == lines)
		{
			return null;
		}
		return splitIntoSentences(lines);
	}

	static protected List<String> splitIntoSentences(List<String> text)
	{
		List<String> sentences = new ArrayList<>();

		for (String line : text)
		{
			List<String> aFewMoreSentences = splitIntoSentences(line);
			sentences.addAll(aFewMoreSentences);
		}

		return sentences;
	}

	/**
	 * Split a line into a series of sentences. Uses the computer locale to
	 * determine what the end of a sentence is (in the U.S., a period).
	 * Smart enough to avoid similar characters in other areas (i.e., does not
	 * split up Dr., U.S.A., vs., etc.). It's harder than it sounds.
	 * @param line - A line representing a paragraph of multiple sentences.
	 * @return A List where each entry is a sentence.
	 */
	static protected List<String> splitIntoSentences(String line)
	{
		List<String> sentences = new ArrayList<>();

		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(line);
		int start = iterator.first();
		for (int end = iterator.next();
			 end    != BreakIterator.DONE;
			 start = end, end = iterator.next())
		{
			sentences.add(line.substring(start,end));
		}

		return sentences;
	}
}
