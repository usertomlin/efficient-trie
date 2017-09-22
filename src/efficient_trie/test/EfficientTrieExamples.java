package efficient_trie.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.linchimin.efficient_trie.PrefixTrie;
import org.linchimin.efficient_trie.SuffixTrie;
import org.linchimin.efficient_trie.TrieNode;





/**
 * 
 * @author Lin Chi-Min (v381654729@gmail.com)
 *
 */
public class EfficientTrieExamples {
	
	static String SUPPORTED_CHARS_TEST = "abcdefghijklmnopqrstuvwxyz0123456789,.-_:'";
	
	protected static ArrayList<String> readLines(String filePath) throws IOException{
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
	}
	
	
	/**
	 * Example for using a PrefixTrie
	 */
	protected static void exampleForPrefixTrie() {
		
		
		/**
		 * 1. The default supported chars are "abcdefghijklmnopqrstuvwxyz0123456789".
		 * 2. Warning: keys that contain one or more unsupported chars are automatically not added to a trie;
		 * to check if all keys are added after trie construction, check trie.isAllAdded() to see if all characters are added. 
		 * 3. Minimize the number of supported chars for speed optimization.
		 */
		TrieNode.setSupportedChars("abcdefghijklmnopqrstuvwxyz");
		
		String[] keys = {"abcde", "abc", "ab", "abbd", "ee"};
		Integer[] values = {1,2,3,4,5};
		int[] scores = {11,14,1,2,3};
		PrefixTrie<Integer> prefixTrie = new PrefixTrie<Integer>(keys, values, scores);
		PrefixTrie<Integer> prefixTrie2 = new PrefixTrie<Integer>(keys, values);
		
		System.out.println("prefixTrie2.size() = " + prefixTrie2.size());

//		prefixTrie.getBestKeyValueNodes("a", 1,2);
//		prefixTrie.getBestKeyValueNodes(word, substringLength, numTopKeyValueNodes, comparator)
//		prefixTrie.getBestKeyValueNodes(word, numTopKeyValueNodes, comparator)
		
//		prefixTrie.getkeyValueNode(word)
		
		{
			TrieNode<Integer> lc0 = prefixTrie.getNodeWithLongestCommonPart("");
			TrieNode<Integer> lc1 = prefixTrie.getNodeWithLongestCommonPart("a");
			TrieNode<Integer> lc2 = prefixTrie.getNodeWithLongestCommonPart("ab");
			TrieNode<Integer> lc3 = prefixTrie.getNodeWithLongestCommonPart("abc");
			TrieNode<Integer> lc4 = prefixTrie.getNodeWithLongestCommonPart("abcd");
			TrieNode<Integer> lc5 = prefixTrie.getNodeWithLongestCommonPart("abcde");
			TrieNode<Integer> lc6 = prefixTrie.getNodeWithLongestCommonPart("abcdr121");
			System.out.println("lc0 = " + lc0);
			System.out.println("lc1 = " + lc1);
			System.out.println("lc2 = " + lc2);
			System.out.println("lc3 = " + lc3);
			System.out.println("lc4 = " + lc4);
			System.out.println("lc5 = " + lc5);
			System.out.println("lc6 = " + lc6);
			boolean isRoot = lc0.isRoot();
			System.out.println("isRoot = " + isRoot);
		}
		{
			TrieNode<Integer> bln0 = prefixTrie.getBestKeyValueNode("");
			TrieNode<Integer> bln1 = prefixTrie.getBestKeyValueNode("a");
			TrieNode<Integer> bln2 = prefixTrie.getBestKeyValueNode("ab");
			TrieNode<Integer> bln3 = prefixTrie.getBestKeyValueNode("abc");
			TrieNode<Integer> bln4 = prefixTrie.getBestKeyValueNode("abcd");
			TrieNode<Integer> bln5 = prefixTrie.getBestKeyValueNode("abcde");
			TrieNode<Integer> bln6 = prefixTrie.getBestKeyValueNode("fbcde");
			TrieNode<Integer> bln7 = prefixTrie.getBestKeyValueNode("ab1232");
			
			System.out.println("bln0 = " + bln0);
			System.out.println("bln1 = " + bln1);
			System.out.println("bln2 = " + bln2);
			System.out.println("bln3 = " + bln3);
			System.out.println("bln4 = " + bln4);
			System.out.println("bln5 = " + bln5);
			System.out.println("bln6 = " + bln6);
			System.out.println("bln7 = " + bln7);
		}
		
		TrieNode<Integer> keyValueNode = prefixTrie.getkeyValueNode("abc");
		
		
		
		
		System.out.println("keyValueNode = " + keyValueNode);
		
		ArrayList<TrieNode<Integer>> ancestors = keyValueNode.getAncestors();
		System.out.println("ancestors.size() = " + ancestors.size());
		System.out.println("keyValueNode = " + keyValueNode);
		TrieNode<Integer> grege = prefixTrie.getRoot();
		System.out.println("grege = " + grege);
		
		List<TrieNode<Integer>> a1 = prefixTrie.getKeyValueNodes("ab");
		
		
		System.out.println("a1.size() = " + a1.size() + ", a1 = " + a1);
		
		
		prefixTrie.getBestKeyValueNode("ab");
		TrieNode<Integer> fe = prefixTrie.getBestKeyValueNode("abc");
		System.out.println("fe = " + fe);
		TrieNode<Integer> exact1 = prefixTrie.getkeyValueNode("ab");
		System.out.println("exact1 = " + exact1);
		
		
	}
	
	
	/**
	 * Example for using a SuffixTrie
	 */
	protected static void exampleForSuffixTrie() {
		
//		System.out.println("serialVersionUID = " + reverse("abcd"));
//		System.out.println("serialVersionUID = " + reverse("abcde"));

		/**
		 * 1. The default supported chars are "abcdefghijklmnopqrstuvwxyz0123456789".
		 * 2. Warning: keys that contain one or more unsupported chars are automatically not added to a trie;
		 * to check if all keys are added after trie construction, check trie.isAllAdded() to see if all characters are added. 
		 * 3. Minimize the number of supported chars for speed optimization.
		 */
		TrieNode.setSupportedChars("abcdefghijklmnopqrstuvwxyz");
		
		String[] keys = {"abcde", "cde", "de", "bdde", "aa"};
		Integer[] values = {1,2,3,4,5};
		int[] scores = {11,14,1,2,3};
		
		SuffixTrie<Integer> suffixTrie = new SuffixTrie<Integer>(keys, values, scores);
		
		
		
	 	TrieNode<Integer> kvNode = suffixTrie.getBestKeyValueNode("de");
		System.out.println("kvNode = " + kvNode);
		
		List<TrieNode<Integer>> wefwef = suffixTrie.getBestKeyValueNodes("de", 2);
		System.out.println("wefwef = " + wefwef);
		
//		SuffixTrie<Integer> suffixTrie = new SuffixTrie<Integer>(Arrays.asList(keys), Arrays.asList(values), scores);
		
		TrieNode<Integer> bkv = suffixTrie.getBestKeyValueNode();
		TrieNode<Integer> bkv2 = suffixTrie.getBestKeyValueNode("bcde");
		
		System.out.println("bkv = " + bkv + "\nbkv2 = " + bkv2);
		
		
		{
			TrieNode<Integer> lc0 = suffixTrie.getNodeWithLongestCommonPart("");
			TrieNode<Integer> lc1 = suffixTrie.getNodeWithLongestCommonPart("e");
			TrieNode<Integer> lc2 = suffixTrie.getNodeWithLongestCommonPart("de");
			TrieNode<Integer> lc3 = suffixTrie.getNodeWithLongestCommonPart("cde");
			TrieNode<Integer> lc4 = suffixTrie.getNodeWithLongestCommonPart("bcde");
			TrieNode<Integer> lc5 = suffixTrie.getNodeWithLongestCommonPart("abcde");
			TrieNode<Integer> lc6 = suffixTrie.getNodeWithLongestCommonPart("32ab1de");
			System.out.println("lc0 = " + lc0);
			System.out.println("lc1 = " + lc1);
			System.out.println("lc2 = " + lc2);
			System.out.println("lc3 = " + lc3);
			System.out.println("lc4 = " + lc4);
			System.out.println("lc5 = " + lc5);
			System.out.println("lc6 = " + lc6);
			boolean isRoot = lc0.isRoot();
			System.out.println("isRoot = " + isRoot);
			
		}
		
		{
			TrieNode<Integer> bln0 = suffixTrie.getBestKeyValueNode("e");
			TrieNode<Integer> bln1 = suffixTrie.getBestKeyValueNode("de");
			TrieNode<Integer> bln2 = suffixTrie.getBestKeyValueNode("cde");
			TrieNode<Integer> bln3 = suffixTrie.getBestKeyValueNode("bcde");
			TrieNode<Integer> bln4 = suffixTrie.getBestKeyValueNode("abcde");
			TrieNode<Integer> bln5 = suffixTrie.getBestKeyValueNode("fbcde");
			
			System.out.println("bln0 = " + bln0);
			System.out.println("bln1 = " + bln1);
			System.out.println("bln2 = " + bln2);
			System.out.println("bln3 = " + bln3);
			System.out.println("bln4 = " + bln4);
			System.out.println("bln5 = " + bln5);
		}
		
		
		
//		suffixTrie.getExactNode(word);
//		TrieNode<Integer> lc6 = suffixTrie.getNodeWithLongestCommonPart("abcde", 6);
		
		
		TrieNode<Integer> keyValueNode = suffixTrie.getkeyValueNode("de");
		
		
		System.out.println("keyValueNode = " + keyValueNode);
		
		ArrayList<TrieNode<Integer>> ancestors = keyValueNode.getAncestors();
		System.out.println("ancestors.size() = " + ancestors.size());
		System.out.println("keyValueNode = " + keyValueNode);
		TrieNode<Integer> grege = suffixTrie.getRoot();
		System.out.println("grege = " + grege);
		
		List<TrieNode<Integer>> keyValueNodesSuffixedBy_de = suffixTrie.getKeyValueNodes("de");
		
		
		System.out.println("keyValueNodesSuffixedBy_de.size() = " + keyValueNodesSuffixedBy_de.size() + "\nkeyValueNodesSuffixedBy_de = " + keyValueNodesSuffixedBy_de);
		
		
	}

	
	protected static void printMemoryConsumption() {
		int mega = 1 << 20;
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("memoryConsumption(): " + (memory / mega) + " MB");
	}
	
	
	
	
	public static void main(String[] args) throws IOException {

		// example for using a PrefixTrie
		exampleForPrefixTrie();
		
		// example for using a SufixTrie
		exampleForSuffixTrie();
		
				
	}
	
}

