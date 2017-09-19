package simpletrie.comparison;

import java.util.ArrayList;
import java.util.Iterator;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;


/**
 * A simple wrapper for com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
 * @author Lin Chi-Min  (v381654729@gmail.com)
 *
 * @param <T> : the generic type parameter
 */
public class PrefixConcurrentRadixTree<T> extends ConcurrentRadixTree<T> {
	
	public PrefixConcurrentRadixTree() {
		super(new DefaultCharArrayNodeFactory());
	}

	public T getByExactKey(String key) {
		return getValueForExactKey(key);
	}

	public ArrayList<T> getValuesByPrefix(String prefix) {
		Iterator<T> iterator = super.getValuesForKeysStartingWith(prefix).iterator();
		ArrayList<T> list = new ArrayList<T>();
		while (iterator.hasNext()) {
			T entry = iterator.next();
			list.add(entry);
		}
		return list;
	}
	
	public ArrayList<String> getKeysByPrefix(String prefix){
		Iterable<CharSequence> keysStartingWith = super.getKeysStartingWith(prefix);
		ArrayList<String> result = new ArrayList<String>();
		keysStartingWith.forEach(a -> result.add(a.toString()));
		return result;
		
	}
	
	public void put(String key, T value){
		super.put(key, value);
	}
	
	public void add(String key, T value){
		super.put(key, value);
	}
	
	public static void main(String[] args) {
		
		PrefixConcurrentRadixTree<String> prefixTree = new PrefixConcurrentRadixTree<String>();
		prefixTree.put("ab", "gre");
		prefixTree.put("abe", "g5re");
		
//		Iterable<String> gerge = prefixTree.getValuesByPrefix("ab");
//		System.out.println(gerge);
		ArrayList<String> fef = prefixTree.getKeysByPrefix("ac");
		System.out.println("fef = " + fef);
		
		
	}
}