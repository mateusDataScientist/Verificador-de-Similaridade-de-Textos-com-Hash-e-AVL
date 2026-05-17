import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class HashTable {

    private LinkedList<Entry>[] table;
    private int size;

    static class Entry {

        String key;
        Integer value;

        Entry(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashTable(int size) {

        this.size = size;

        table = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Primeira função hash
    private int hash1(String key) {

        int hash = 0;

        for (char c : key.toCharArray()) {
            hash = (31 * hash + c) % size;
        }

        return Math.abs(hash);
    }

    // Segunda função hash
    private int hash2(String key) {

        int hash = 7;

        for (char c : key.toCharArray()) {
            hash = hash * 37 + c;
        }

        return Math.abs(hash % size);
    }

    public void put(String key, Integer value) {

        int index = hash1(key);

        for (Entry e : table[index]) {

            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }

        table[index].add(new Entry(key, value));
    }

    public Integer get(String key) {

        int index = hash1(key);

        for (Entry e : table[index]) {

            if (e.key.equals(key)) {
                return e.value;
            }
        }

        return null;
    }

    public Set<String> keySet() {

        Set<String> keys = new HashSet<>();

        for (LinkedList<Entry> bucket : table) {

            for (Entry e : bucket) {
                keys.add(e.key);
            }
        }

        return keys;
    }
}