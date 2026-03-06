import java.util.ArrayList;
import java.util.LinkedList;


public class ObjectHashMap extends AbstractHashMap {
    private LinkedList<Entry>[] table;
    public ObjectHashMap(double maxLoad) {
        super(maxLoad);
        table = new LinkedList[super.capacity];
        this.createTable(capacity);
    }

    private void createTable(int capacity){
        for (int i = 0; i < capacity; ++i){
            LinkedList<Entry> list = new LinkedList<Entry>();
            this.table[i] = list;
        }
    }

    @Override
    public void put(Object key, Object value) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
    
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
    
        table[index].add(new Entry(key, value));
        numKeys++;

        if ((double) numKeys / capacity > maxLoad) {
            resize();
        }
    }
    
    @Override
    public Entry[] getEntries() {
        // Time Complexity: O(n), where n is the number of entries
        ArrayList<Entry> entryList = new ArrayList<>();
        for (LinkedList<Entry> bucket : table) {
            entryList.addAll(bucket);
        }
        return entryList.toArray(new Entry[0]);
    }

    @Override
    public Object find(Object key) {   
        int index = hash(key);
        LinkedList<Entry> bucket = table[index];
        for (int i = 0; i < bucket.size(); ++i){
            if (bucket.get(i).key.equals(key)) {
                return bucket.get(i).value;
            }
        }
        return null;
    }


    @Override
    public boolean containsKey(Object key) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    

    @Override
    protected void resize() {
        int newCapacity = Math.max(1, capacity * 2); // Ensure the new capacity is at least 1
        LinkedList<Entry>[] newTable = new LinkedList[newCapacity];
        capacity = newCapacity; // Update the capacity

        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }
    
        // Rehash all entries
        for (LinkedList<Entry> bucket : table) {
            for (Entry entry : bucket) {
                //int newIndex = Math.abs(entry.key.hashCode()) % newCapacity;
                int newIndex = hash(entry.key);
                newTable[newIndex].add(entry);
            }
        }
    
        table = newTable;
    }
}