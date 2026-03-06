import java.lang.Math;

public class Hashing {
    public static int hash(int item, int numBins) {
	int index = 0;
        return index; 
    }

    public static int hash(String item, int numBins) {
        int index = 0;
        return index;
    }

    public static void main(String[] args) {
        int numBins = 20;
        int[] intItems = { -1, 24444, 333, 4, 5, 16, 7, 8, 9 };
        String[] stringItems = { "abc", "bca", "acb", "bac" };

        // This simulates the hash function for integers and strings.
        // In general, we would put these hashes into a hash table!
        // TODO: Implement the hash function for integers and strings.
        // Your goal is to make it so we get as few collisions
        // as possible. A collision is when two items hash to
        // the same value (often called a bin).
        for (int i = 0; i < intItems.length; i++) {
            System.out.println("Hash of " + intItems[i] + " is " + hash(intItems[i], numBins));
        }

        for (int i = 0; i < stringItems.length; i++) {
            System.out.println("Hash of " + stringItems[i] + " is " + hash(stringItems[i], numBins));
        }
    }
}
