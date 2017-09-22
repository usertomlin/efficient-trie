# efficient-trie

A Java library for efficient implementation of prefix trie and suffix trie 

See package 'efficient_trie' in the src folder for examples and tests.

## Usage

1. Add 'efficient-trie-1.0.jar' to build path of a Java project, and refer to examples in EfficientTrieExamples.java to use this library.

2. To run the snippets for performance comparison with an another library 'concurrent-trees-2.4.0.jar', add both 'efficient-trie-1.0.jar' and 'concurrent-trees-2.4.0.jar' to build path and run the snippets in TriesComparison.java. This repository is also an Eclipse Java project and so can be imported for testing with examples in the efficient_trie package.


Example for prefix trie and suffix trie construction:

```java
String[] words = {"abcde", "abc", "ab", "abbd", "ee"};
Integer[] values = {1,2,3,4,5};
int[] scores = {11,14,1,2,3};
PrefixTrie<Integer> prefixTrie = new PrefixTrie<Integer>(words, values, scores);
PrefixTrie<Integer> prefixTrie2 = new PrefixTrie<Integer>(words, values);
String[] words2 = {"abcde", "cde", "de", "bdde", "aa"};
		
SuffixTrie<Integer> suffixTrie = new SuffixTrie<Integer>(words2, values, scores);
SuffixTrie<Integer> suffixTrie2 = new SuffixTrie<Integer>(words2, values);
``` 

See the javadoc, examples in EfficientTrieExamples.java and TriesComparison.java, as well as the source codes, for APIs and method details.

## Features 

An efficient, primitive array implementation of prefix trie and suffix trie. 

After running method 'compareSpeeds()' in TriesComparison.java, the console results on a Windows NB with i7-4750Q and jdk1.8.0_121 are similar to: 
 
 	  Time taken to construct PrefixTries : 292 milliseconds.
	  Time taken to construct PrefixConcurrentRadixTrees : 1093 milliseconds.
	  ----------------------------------------------------------
	  Time taken to get iterables of key value pairs by prefix for PrefixTrie: 78 milliseconds.
	  Time taken to get iterables of key value pairs by prefix for PrefixConcurrentRadixTree : 219 milliseconds.
	  ----------------------------------------------------------
	  Time taken to get keys by prefix for PrefixTrie: 2496 milliseconds.
	  Time taken to get keys by prefix for PrefixConcurrentRadixTree : 4247 milliseconds.
	  ----------------------------------------------------------
	  Time taken to get values by prefix for PrefixTrie: 501 milliseconds.
	  Time taken to get values by prefix for PrefixConcurrentRadixTree : 3307 milliseconds.
	  ----------------------------------------------------------
	  Time taken to get values by exact keys for PrefixTrie : 46 milliseconds.
	  Time taken to get values by exact keys for PrefixConcurrentRadixTree : 312 milliseconds.

	  
	  