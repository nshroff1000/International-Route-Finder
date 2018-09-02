import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V> {@inheritDoc}
 * @param <Key> {@inheritDoc}
 *
 * @author joncho, 16sp
 */
public class BinaryMinHeap<V, Key extends Comparable<Key>> {

    ArrayList<Entry<V, Key>> minHeap;
    Map<V, Integer> values;
    
    public BinaryMinHeap() {
        minHeap = new ArrayList<Entry<V, Key>>();
        values = new HashMap<V, Integer>();
    }
    
    public int size() {
        return minHeap.size();
    }

    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    public boolean containsValue(V value) {
        return values.containsKey(value);
    }

    public void add(V value, Key key) {
        if (key == null || containsValue(value)) {
            throw new IllegalArgumentException();
        }
        
        minHeap.add(new Entry<V, Key>(value, key));
        values.put(value, minHeap.size() - 1);
        decreaseKey(value, key);
    }

    public void decreaseKey(V value, Key newKey) {
        if (newKey == null) {
            throw new IllegalArgumentException();
        }
        
        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        
        int index = values.get(value);
        Entry<V, Key> currentEntry = minHeap.get(index);
        if(newKey.compareTo(currentEntry.getKey()) > 0) {
            throw new IllegalArgumentException();
        }
        
        currentEntry.setKey(newKey);
        while(index > 0 && minHeap.get(parent(index)).getKey().compareTo(newKey) > 0) {
            currentEntry = minHeap.get(index);
            Entry<V, Key> parentEntry = minHeap.get(parent(index));
            minHeap.set(index, parentEntry);
            minHeap.set(parent(index), currentEntry);
            values.put(currentEntry.getValue(), parent(index));
            values.put(parentEntry.getValue(), index);
            index = parent(index);
        }
    }

    public V peek() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return minHeap.get(0).getValue();
    }

    public V extractMin() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Entry<V, Key> minEntry = minHeap.get(0);
        Entry<V, Key> lastEntry = minHeap.get(minHeap.size() - 1);
        minHeap.set(0, lastEntry);
        values.put(lastEntry.getValue(), 0);
        values.remove(minEntry.getValue());
        minHeap.remove(minHeap.size() - 1);
        minHeapify(0);
        return minEntry.getValue();
    }
    
    public Set<V> values() {
        return values.keySet();
    }
    
    void minHeapify(int i) {
        int index = i;
        int smallestIndex = index;
        
        if(leftChild(index) < size()) {
            Entry<V, Key> leftEntry = minHeap.get(leftChild(index));
            if(leftEntry.getKey().compareTo(minHeap.get(smallestIndex).getKey()) < 0) {
                smallestIndex = leftChild(index);
            }
        }
        
        if(rightChild(index) < size()) {
            Entry<V, Key> rightEntry = minHeap.get(rightChild(index));
            if(rightEntry.getKey().compareTo(minHeap.get(smallestIndex).getKey()) < 0) {
                smallestIndex = rightChild(index);
            }
        }
        
        if(smallestIndex != index) {
            Entry<V, Key> currentEntry = minHeap.get(index);
            Entry<V, Key> smallestEntry = minHeap.get(smallestIndex);
            minHeap.set(smallestIndex, currentEntry);
            minHeap.set(index, smallestEntry);
            values.put(currentEntry.getValue(), smallestIndex);
            values.put(smallestEntry.getValue(), index);
            minHeapify(smallestIndex);
        }
    }
    
    int parent(int index) {
        return (index - 1)/2;
    }
    
    int leftChild(int index) {
        return 2*index + 1;
    }
    
    int rightChild(int index) {
        return 2*index + 2;
    }
    
    /**
     * Helper entry class for maintaining value-key pairs.
     * The underlying indexed list for your heap will contain
     * these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A value;
        private B key;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        public A getValue() {
            return this.value;
        }

        /**
         * @return  the key stored in the entry
         */
        public B getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key  the new key
         * @return  the old key
         */
        public B setKey(B key) {
            B oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}
