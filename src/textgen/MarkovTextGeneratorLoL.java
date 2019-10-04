package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	private boolean hasTrain = false;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		hasTrain = true;
		if(sourceText.length() == 0) {
			System.out.println("Text is empty.");
		}
		else {
		// TODO: Implement this method
		String[] textArray = sourceText.split(" +");
		starter = textArray[0];
		String prevWord = starter;
		
		if(textArray.length <= 0 )
		{
			hasTrain = false;
		}
		
			for(int i = 1; i < textArray.length;i++) {
				String s = textArray[i];
				
				if(wordList.isEmpty()) {
					ListNode temp = (new ListNode(prevWord));
					temp.addNextWord(s);
					wordList.add(temp);
					prevWord = s;
				}
				boolean has = false;
				for(ListNode ln : wordList) {
					String test = ln.getWord();
					
					if(test.equals(prevWord)) {
						ln.addNextWord(s);
						prevWord = s;
						has = true;
						break;
					}	
				}
				if(!has) {
				ListNode temp = (new ListNode(prevWord));
				temp.addNextWord(s);
				wordList.add(temp);
				prevWord = s;
				}
				if(i == textArray.length - 1) {
				ListNode temp = (new ListNode(prevWord));
				temp.addNextWord(starter);
				wordList.add(temp);
				prevWord = s;
				}

			}
		}
		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		
		if(!hasTrain) {
			return null;
		}
		if(numWords <=0) {
			return "";
		}
		if(wordList.isEmpty()) {
			System.out.println("You need train first!");
			return "";
		}
		String currWord = wordList.get(0).getWord();
		String output = "";
		
		output += currWord;
		
		int textSize = 500;
		int x = 1;
		while(x != textSize) {
			ListNode temp = findNode(currWord);
			if(temp == null) {
			}
			currWord = temp.getRandomNextWord(new Random());
			output = output + " " + currWord;
			x++;
		}
	    // TODO: Implement this method
		return output;
	}
	
	private ListNode findNode(String word) {
		ListNode output;
		for(ListNode ln : wordList) {
			if(ln.getWord().equals(word)) {
				output = ln;
				return output;
			}
		}
		return null;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		if(sourceText.length()== 0 ) {
			System.out.println("Text is empty.");
		}
		else {
		this.wordList.clear();
		this.train(sourceText);
		}
		// TODO: Implement this method.
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public int getListSize() {
		return wordList.size();
	}
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int random = generator.nextInt(this.nextWords.size());
	    return nextWords.get(random);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


