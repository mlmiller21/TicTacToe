import java.util.Iterator;

public class Dictionary implements DictionaryADT {

    private int size;
    private DoublyLinkedList[] table;   // Array of doublyLinkedLists
    private int numElements;


    /**
     * Create a new dictionary, make sure size is prime number
     * @param size
     */
    public Dictionary(int size){
        this.size = size;
        table = new DoublyLinkedList[size];
    }

    /**
     * Hash function utilizing Horner's rule for the Hash Code and a Division Compression Funciton
     * @param config string representing key of record
     * @return hashFunction
     */
    private int hashFunction(String config){
        // Hash code

        int hashCode = 0;
        // prime number coefficient
        int prime = 47;

        // Horner's rule, apply modulus of size to each step to account for overflow
        for (int i = 0; i < config.length(); i++)
            hashCode = (hashCode * prime + (int) config.charAt(i)) % size;

        // Compression function
        return hashCode % size;
    }

    /**
     * Inserts the given Record pair in the dictionary
     * @param pair to insert
     * @return 1 if T[h(pair.getConfig())] already stores at least one element, 0 otherwise
     * @throws DictionaryException if pair is already in dictionary
     */
    public int insert (Record pair) throws DictionaryException {

        int key = hashFunction(pair.getConfig());

        // If pair already exists, throw error
        if (get(pair.getConfig()) != -1)
            throw new DictionaryException();

        // If table[key] doesn't have a linked list, create a new one and insert the pair at the end of the list
        if (table[key] == null){
            table[key] = new DoublyLinkedList();
            table[key].addLast(pair);
            numElements++;
            return 0;
        }

        // A list exists, so insert a new node into the end of the linkedlist
        table[key].addLast(pair);
        numElements++;

        return 1;
    }

    /**
     * Removes the entry with the given config from the dictionary.
     * @param config string representing key of record
     * @throws DictionaryException if the configuration is not in the dictionary.
     */
    public void remove (String config) throws DictionaryException{

        int key = hashFunction(config);

        // list doesn't exist or is empty, error thrown
        if (table[key] == null || table[key].isEmpty())
            throw new DictionaryException();

        // Create an iterator and iterate through bucket until the Record matching config is found
        Iterator<Record> it = table[key].iterator();
        while (it.hasNext()){
            Record record = it.next();
            if (record.getConfig().equals(config))
                it.remove();
        }
        // Free up memory for garbage collector
        if (table[key].isEmpty())
            table[key] = null;
    }

    /**
     *  Return a Record score from dictionary given config
     * @param config string representing key of record
     * @return score for given configuration or -1 if configuration doesn't exist
     */
    public int get (String config){

        int key = hashFunction(config);

        DoublyLinkedList list = table[key];

        // If list isn't null
        if (list != null){
            // create an iterator object and iterate through the list and return Record if it exists
            Iterator<Record> it = list.iterator();
            while (it.hasNext()){
                Record record = it.next();
                // Use equals to compare string
                if (record.getConfig().equals(config))
                    return record.getScore();
            }
        }
        // Record not found
        return -1;
    }

    /**
     * A method that returns the number of Records stored in dictionary
     * @return numElements
     */
    public int numElements(){
        return numElements;
    }
}
