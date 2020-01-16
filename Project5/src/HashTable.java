public class HashTable<K, V> {
    private SinglyLinkedList<KV>[] hashTable;
    private int size = 127;

    private class KV{
        public K key;
        public V value;

        public KV(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    public HashTable() {
        hashTable = new SinglyLinkedList[size];
        for (int i = 0; i < size; i++) {
            hashTable[i] = new SinglyLinkedList<>();
        }
    }

    /**
     * Put a given key value pair into the hash table, or update its value if the key already exists.
     *
     * @param key the key object
     * @param value the value object
     */
    public void put(K key, V value){
        int idx = get_index(key);
        SinglyLinkedList<KV> l = hashTable[idx];
        for(KV kv: l){
            if(key.equals(kv.key)){
                // update existing value instead
                kv.value = value;
                return;
            }
        }
        l.add_at_ent(new KV(key, value));
    }

    /**
     * Get the value paired with the given key in the hash table.
     *
     * @param key the key object
     * @return the matching value object, or null if none found
     */
    public V get_value(K key){
        int idx = get_index(key);
        SinglyLinkedList<KV> l = hashTable[idx];
        for(KV kv: l){
            if(key.equals(kv.key)){
                return kv.value;
            }
        }
        return null;
    }

    /**
     * Get the index of the list to which the key belongs in the table.
     *
     * @param key the key object
     * @return index to the destination list
     */
    public int get_index(K key){
        return (key.hashCode() & 0x7FFFFFFF) % this.size;
    }

    /**
     * Check if the hash table contains the specified key
     *
     * @param key the key to be searched for
     * @return true / false answer
     */
    public boolean containsKey(K key){
        int idx = get_index(key);
        SinglyLinkedList<KV> l = hashTable[idx];
        for(KV kv:l){
            if (key.equals(kv.key)) {
                return true;
            }
        }
        return false;
    }

}
