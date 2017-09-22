package efficient_trie.comparison;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.linchimin.efficient_trie.PrefixTrie;
import org.linchimin.efficient_trie.TrieNode;

import com.googlecode.concurrenttrees.common.KeyValuePair;


/**
 * 
 * a class for performance comparison 
 * between ConcurrentRadixTree (com.googlecode.concurrenttrees.radix.ConcurrentRadixTree)
 * and this jar's PrefixTrie
 * 
 * @author Lin Chi-Min  (v381654729@gmail.com)
 *
 */
public class TriesComparison {
	
	private static String SUPPORTED_CHARS_TEST = "abcdefghijklmnopqrstuvwxyz0123456789,.-_:'";

	private static String DIC_FILE_PATH = "enVocabulary-45k.txt";
	
	
	protected static ArrayList<String> readLines(String filePath) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
			ArrayList<String> result = new ArrayList<>(100);
			for (;;) {
				String line = reader.readLine();
				if (line == null)
					break;
				result.add(line);
			}
			reader.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
	/**
	 * Check if all searched keys and values of PrefixTrie and PrefixConcurrentRadixTree are consistent
	 */
	public static void checkKeyValuePairsConsistency() {
		/** 
		 * 1. The default supported chars are "abcdefghijklmnopqrstuvwxyz0123456789";
		 * 2. warning: keys that contain one or more unsupported chars are automatically not added to a trie
		 * to check if all keys are added after trie construction, check trie.isAllAdded() to see if all characters are added; 
		 * 3. Minimize the number of supported chars for speed optimization; 
		 */
		TrieNode.setSupportedChars(SUPPORTED_CHARS_TEST);
		
		List<String> keys = readLines(DIC_FILE_PATH);

		ArrayList<Integer> indices = new ArrayList<>();
		for (int i = 0; i < keys.size(); i++) {
			indices.add(i);
		}
		PrefixTrie<Integer> trie = new PrefixTrie<Integer>(keys, indices);
		PrefixConcurrentRadixTree<Integer> tree = new PrefixConcurrentRadixTree<Integer>();
		for (int j = 0; j < keys.size(); j ++) {
			tree.add(keys.get(j), j);
		}
		
		//////////////////////////////////////////////////////////
		
		int count = 0;
		
		/**
		 * check consistency
		 */
		for (String word : keys) {
			
			String prefix = word.substring(0, Math.min(word.length(), 2));
			List<TrieNode<Integer>> KeyValueNodes1 = trie.getKeyValueNodes(prefix);

			ArrayList<String> keys1 = new ArrayList<>();
			ArrayList<Integer> values1 = new ArrayList<>();
			for (TrieNode<Integer> trieNode : KeyValueNodes1) {
				keys1.add(trieNode.getKey());
				values1.add(trieNode.getValue());
			}
			
			ArrayList<String> keys2 = tree.getKeysByPrefix(prefix);
			ArrayList<Integer> values2 = tree.getValuesByPrefix(prefix);
			
			Collections.sort(keys1);
			Collections.sort(keys2);
			Collections.sort(values1);
			Collections.sort(values2);
			
			if (keys1.equals(keys2) == false ){
				throw new Error(
						"Error in TriesTest.checkKeyValuePairsConsistency() : keys1 shuould be equal to keys2.");
			}
			if (values1.equals(values2) == false ){
				throw new Error(
						"Error in TriesTest.checkKeyValuePairsConsistency() : values1 shuould be equal to values2.");
			}
			
			count++;
			if (count % 2000 == 0){
				System.out.println("Checked " + count + " prefixes.");
			}
		}
		System.out.println("The key-value nodes are consistent.");
	}
	
	
	
	/**
	 * Snippet for a brief execution speed comparison of the following 5 operations. 
	 * 
	 * The console results on a Windows NB with i7-4750Q and jdk1.8.0_121 is similar to: 
	 * 
	 * Time taken to construct PrefixTries : 292 milliseconds.
	 * Time taken to construct PrefixConcurrentRadixTrees : 1093 milliseconds.
	 * ----------------------------------------------------------
	 * Time taken to get iterables of key value pairs by prefix for PrefixTrie: 78 milliseconds.
	 * Time taken to get iterables of key value pairs by prefix for PrefixConcurrentRadixTree : 219 milliseconds.
	 * ----------------------------------------------------------
	 * Time taken to get keys by prefix for PrefixTrie: 2496 milliseconds.
	 * Time taken to get keys by prefix for PrefixConcurrentRadixTree : 4247 milliseconds.
	 * ----------------------------------------------------------
	 * Time taken to get values by prefix for PrefixTrie: 501 milliseconds.
	 * Time taken to get values by prefix for PrefixConcurrentRadixTree : 3307 milliseconds.
	 * ----------------------------------------------------------
	 * Time taken to get exact key-value nodes by prefix for PrefixTrie : 32 milliseconds.
	 * Time taken to get exact key-value nodes by prefix for PrefixConcurrentRadixTree : 312 milliseconds.
	 * ----------------------------------------------------------
	 */
	public static void compareSpeeds()  {
		
		/**
		 * 1. The default supported chars are "abcdefghijklmnopqrstuvwxyz0123456789".
		 * 2. Warning: keys that contain one or more unsupported chars are automatically not added to a trie;
		 * to check if all keys are added after trie construction, check trie.isAllAdded() to see if all characters are added. 
		 * 3. Minimize the number of supported chars for speed optimization.
		 */
		TrieNode.setSupportedChars(SUPPORTED_CHARS_TEST);
		
		List<String> keys = readLines(DIC_FILE_PATH);
	
		ArrayList<Integer> indices = new ArrayList<>();
		for (int i = 0; i < keys.size(); i++) {
			indices.add(i);
		}
		PrefixTrie<Integer> trie = new PrefixTrie<Integer>(keys, indices);
		PrefixConcurrentRadixTree<Integer> tree = new PrefixConcurrentRadixTree<Integer>();
		for (int j = 0; j < keys.size(); j ++) {
			tree.add(keys.get(j), j);
		}
		
		//////////////////////////////////////////////////////////
		
		long timeTaken_a1 = System.currentTimeMillis();

		/**
		 * construct a PrefixTrie
		 */
		for (int i = 0; i < 10; i++) {
			PrefixTrie<Integer> trie_i = new PrefixTrie<Integer>(keys, indices);
		}

		System.out.println("Time taken to construct PrefixTries : " + (System.currentTimeMillis() - timeTaken_a1)
				+ " milliseconds.");
		

		long timeTaken_a2 = System.currentTimeMillis();
		
		/**
		 * construct a com.googlecode.concurrenttrees.radix.ConcurrentRadixTree
		 */
		for (int i = 0; i < 10; i++) {
			PrefixConcurrentRadixTree<Integer> tree_i = new PrefixConcurrentRadixTree<Integer>();
			for (int j = 0; j < keys.size(); j ++) {
				tree_i.add(keys.get(j), j);
			}
		}
		
		System.out.println("Time taken to construct PrefixConcurrentRadixTrees : " + (System.currentTimeMillis() - timeTaken_a2)
				+ " milliseconds.");
		
		System.out.println("----------------------------------------------------------");
		
		//////////////////////////////////////////////////////////
		

		long timeTaken1 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			for (String word : keys) {
				String prefix = word.substring(0, Math.min(word.length(), 2));
				List<TrieNode<Integer>> KeyValueNodes1 = trie.getKeyValueNodes(prefix);
//				trie.getBestKeyValueNode(word, substringLength)
			}
		}

		System.out.println("Time taken to get iterables of key value pairs by prefix for PrefixTrie: " + (System.currentTimeMillis() - timeTaken1)
				+ " milliseconds.");
		
		
		//////////////////////////////////////////////////////////
		
		long timeTaken2 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			for (String word : keys) {
				String prefix = word.substring(0, Math.min(word.length(), 2));
				Iterable<KeyValuePair<Integer>> KeyValueNodes2 = tree.getKeyValuePairsForKeysStartingWith(prefix);
			}
		}
		
		System.out.println("Time taken to get iterables of key value pairs by prefix for PrefixConcurrentRadixTree: " + (System.currentTimeMillis() - timeTaken2)
				+ " milliseconds.");
		
		System.out.println("----------------------------------------------------------");
		


		long timeTaken3 = System.currentTimeMillis();

		for (String word : keys) {
			String prefix = word.substring(0, Math.min(word.length(), 2));
			List<TrieNode<Integer>> KeyValueNodes1 = trie.getKeyValueNodes(prefix);
			
			ArrayList<CharSequence> list = new ArrayList<CharSequence>();
			for (TrieNode<Integer> trieNode : KeyValueNodes1) {
				list.add(trieNode.getKey());
			}				
		}

		System.out.println("Time taken to get keys by prefix for PrefixTrie: " + (System.currentTimeMillis() - timeTaken3)
				+ " milliseconds.");
		
		
		//////////////////////////////////////////////////////////
		
		long timeTaken4 = System.currentTimeMillis();

		for (String word : keys) {
			
			String prefix = word.substring(0, Math.min(word.length(), 2));
			Iterable<CharSequence> KeyValueNodes2 = tree.getKeysStartingWith(prefix);
			ArrayList<CharSequence> list = new ArrayList<CharSequence>();
			for ( CharSequence key : KeyValueNodes2) {
				list.add(key);
			}				
		}

		
		System.out.println("Time taken to get keys by prefix for PrefixConcurrentRadixTree: " + (System.currentTimeMillis() - timeTaken4)
				+ " milliseconds.");
		
		
		System.out.println("----------------------------------------------------------");
		
		long timeTaken5 = System.currentTimeMillis();

		for (String word : keys) {
			String prefix = word.substring(0, Math.min(word.length(), 2));
			List<TrieNode<Integer>> KeyValueNodes1 = trie.getKeyValueNodes(prefix);
			
			ArrayList<Integer> list = new ArrayList<Integer>(KeyValueNodes1.size());
			for (TrieNode<Integer> trieNode : KeyValueNodes1) {
				list.add(trieNode.getValue());
			}				
		}

		System.out.println("Time taken to get values by prefix for PrefixTrie: " + (System.currentTimeMillis() - timeTaken5)
				+ " milliseconds.");
		
		
		//////////////////////////////////////////////////////////
		
		long timeTaken6 = System.currentTimeMillis();

		for (String word : keys) {
			
			String prefix = word.substring(0, Math.min(word.length(), 2));
			
			Iterable<Integer> KeyValueNodes2 = tree.getValuesForKeysStartingWith(prefix);
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer value : KeyValueNodes2) {
				list.add(value);
			}				
		}

		
		System.out.println("Time taken to get values by prefix for PrefixConcurrentRadixTree: " + (System.currentTimeMillis() - timeTaken6)
				+ " milliseconds.");
		
		
		System.out.println("----------------------------------------------------------");
		
		
		long timeTaken7 = System.currentTimeMillis();

		for (int i = 0; i < 10; i++) {
			for (String word : keys) {
				String prefix = word.substring(0, Math.min(word.length(), 3));
				TrieNode<Integer> KeyValueNode1 = trie.getNode(prefix);
			}
		}

		System.out.println("Time taken to get exact key-value nodes by prefix for PrefixTrie: " + (System.currentTimeMillis() - timeTaken7) + " milliseconds.");
		
		long timeTaken8 = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			for (String word : keys) {
				String prefix = word.substring(0, Math.min(word.length(), 3));
				Integer KeyValueNode2 = tree.getValueForExactKey(prefix);
			}	
		}
				
		System.out.println("Time taken to get exact key-value nodes by prefix for PrefixConcurrentRadixTree: " + (System.currentTimeMillis() - timeTaken8) + " milliseconds.");
		
	}
	

	
	

	
	protected static void printMemoryConsumption() {
		int mega = 1 << 20;
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("memoryConsumption(): " + (memory / mega) + " MB");
	}
	
	
	/**
	 * compare memory consumption for PrefixTrie and PrefixConcurrentRadixTree
	 *  
	 * Construction of a PrefixTrie takes around 48 MB
	 * Construction of a PrefixConcurrentRadixTree takes around 51 MB
	 */
	protected static void compareMemoryConsumption() {
		/** 
		 * 1. The default supported chars are "abcdefghijklmnopqrstuvwxyz0123456789";
		 * 2. warning: keys that contain one or more unsupported chars are automatically not added to a trie
		 * to check if all keys are added after trie construction, check trie.isAllAdded() to see if all characters are added; 
		 * 3. Minimize the number of supported chars for speed optimization; 
		 */
		TrieNode.setSupportedChars(SUPPORTED_CHARS_TEST);
		
		List<String> keys = readLines(DIC_FILE_PATH);
	
		
		ArrayList<Integer> indices = new ArrayList<>();
		for (int i = 0; i < keys.size(); i++) {
			indices.add(i);
		}
		
//		boolean checkFirstElseSecond = true;
		boolean checkFirstElseSecond = false;
		
		if (checkFirstElseSecond)
		{
			printMemoryConsumption();
			PrefixTrie<Integer> trie = new PrefixTrie<Integer>(keys, indices);
			printMemoryConsumption();
			
		} else {
			printMemoryConsumption();
			
			PrefixConcurrentRadixTree<Integer> tree = new PrefixConcurrentRadixTree<Integer>();
			for (int j = 0; j < keys.size(); j ++) {
				tree.add(keys.get(j), j);
			}		
			
			printMemoryConsumption();
		}
		
		
		
		System.out.println("System.exit(0) at TriesTest.compareMemoryConsumption()");
		System.exit(0);
	}
	
	
	
	public static void main(String[] args) {

		
		/**
		 *  compare memory consumption for PrefixTrie and PrefixConcurrentRadixTree
		 */
//		compareMemoryConsumption();
		
		/**
		 * running this to check if all searched keys and values of PrefixTrie and PrefixConcurrentRadixTree are consistent
		 */
//		checkKeyValuePairsConsistency();
		
		/**
		 * Snippet for a brief execution speed comparison of the 5 operations
		 */
		compareSpeeds();
				
		
	}
	
}

