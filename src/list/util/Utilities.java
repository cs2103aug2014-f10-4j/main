//@author A0113672L
package list.util;

import java.util.Iterator;

public class Utilities {
    
    /**
     * Checks whether a particular collection of item is sorted.
     * @param iterable
     * @return true if the collection is sorted. Otherwise, returns false.
     */
    public static <T extends Comparable<? super T>>
    boolean isSorted(Iterable<T> iterable) {
        Iterator<T> iter = iterable.iterator();
        if (!iter.hasNext()) {
            return true;
        }
        T t = iter.next();
        while (iter.hasNext()) {
            T t2 = iter.next();
            if (t.compareTo(t2) > 0) {
                return false;
            }
            t = t2;
        }
        return true;
    }
    
}
