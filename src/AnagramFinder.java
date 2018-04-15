import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AnagramFinder {

	public static void main(String[] args) {
		ArrayList<String> dict = parseURL("https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt");
		
		System.out.println("This program finds all the anagrams of a given word in the English dictionary.\n");
		
		Scanner scan = new Scanner(System.in);
		System.out.print("What word would you like to find all the anagrams of?\nWord: ");
		String word = scan.nextLine();
		scan.close();
		
		ArrayList<String> anagrams = findAnagrams(dict, word);
		System.out.println("\nAnagrams of " + word + ":");
		for(int i = 0; i < anagrams.size(); i++) {
			System.out.println(anagrams.get(i));
		}
		
	}
	
	public static ArrayList<String> parseURL(String aURL) {
		ArrayList<String> dict = new ArrayList<String>();
		
		try { 
			URL url = new URL(aURL);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
			String word;
			while((word = bufferedReader.readLine()) != null) {
				dict.add(word);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dict;
	}
	
	public static ArrayList<String> findAnagrams(ArrayList<String> dict, String word){
		ArrayList<String> matches = new ArrayList<String>();
		
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
	
	public static boolean checkForMatch(String base, String test) {
		int same = 0;
		String letter;
		
		for(int i = 0; i < base.length(); i++) {
			letter = base.substring(i, i + 1);
			if(test.contains(letter)) {
				test = test.replaceFirst(letter, "");
				same += 1;
			} else {
				return false;
			}
		}
		if(same == base.length()) {
			return true;
		} else { 
			return false;
		}
	}
}
