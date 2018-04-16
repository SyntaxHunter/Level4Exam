import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnagramFinder {

	public static void main(String[] args) {
		List<String> dict = buildDict("https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt");
		
		System.out.println("This program finds all the anagrams of a given word in the English dictionary.\n");
		
		Scanner scan = new Scanner(System.in);
		System.out.print("What word would you like to find all the anagrams of?\nWord: ");
		String word = scan.nextLine();
		scan.close();
		
		List<String> anagrams = findAnagrams(dict, word);
		System.out.println("\nAnagrams of " + word + ":");
		for(int i = 0; i < anagrams.size(); i++) {
			System.out.println(anagrams.get(i));
		}
		
	}
	
	public static List<String> buildDict(String aURL) {
		List<String> dict = new ArrayList<String>();
		
		URLConnection urlConnection = null;
	
		try {
			urlConnection = new URL(aURL).openConnection();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if(urlConnection == null)
			return dict;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));) { 
			String word;
			while((word = bufferedReader.readLine()) != null) {
				dict.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dict;
	}
	
	public static List<String> findAnagrams(List<String> dict, String word){
		List<String> matches = new ArrayList<String>();
		
		int wordLength = word.length();
		String test;
		
		for(int i = 0; i < dict.size(); i++) {
			test = dict.get(i);
			if(test.length() == wordLength && !test.equals(word) && checkForMatch(word, test)) {
				matches.add(dict.get(i));
			}
		}
		return matches;
	}
	
	private static boolean checkForMatch(String word1, String word2) {
		char[] word1Array = word1.toCharArray();
		char[] word2Array = word2.toCharArray();
		
		Arrays.sort(word1Array);
		Arrays.sort(word2Array);
		
		return Arrays.equals(word1Array, word2Array);
	}
}
