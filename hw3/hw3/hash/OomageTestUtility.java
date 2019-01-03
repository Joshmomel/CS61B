package hw3.hash;

import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        int N = oomages.size();
        int max = (int) (N / 2.5);
        int min = N / 50;

        HashMap<Integer, Integer> bucketStore = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (!bucketStore.containsKey(bucketNum)) {
                bucketStore.put(bucketNum, 0);
            } else {
                bucketStore.put(bucketNum, bucketStore.get(bucketNum) + 1);
            }
        }

        for (Integer i : bucketStore.keySet()) {
            if (bucketStore.get(i) <= min || bucketStore.get(i) >= max) {
                return false;
            }
        }

        return true;
    }
}
