package core.concurrency.safe_iteration;

import java.util.*;

/**
 * Created by Артем on 18.12.2016.
 */
public class NonThreadSafeIteratorExample {
    public static void arrayListModification() {
        List<String> myList = new ArrayList<>();

        myList.add("ex1");
        myList.add("2");
        myList.add("3");
        myList.add("4");
        myList.add("5");

        Iterator<String> it = myList.iterator();
        System.out.println("Before Deleting");
        while (it.hasNext()) {
            String value = it.next();
            System.out.println("List Value:" + value);
            if (value.equals("3")) {
                myList.add("6"); //throws java.util.ConcurrentModificationException
                myList.remove(value); //throws java.util.ConcurrentModificationException
                it.remove(); //Non-exception code
            }
        }

        System.out.println("After Deleting");
        it = myList.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println("List Value:" + value);
        }
    }

    public static void mapModification() {
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("ex1", "ex1");
        myMap.put("2", "2");
        myMap.put("3", "3");

        Iterator<String> it1 = myMap.keySet().iterator();
        while (it1.hasNext()) {
            String key = it1.next();
            System.out.println("Map Value:" + myMap.get(key));
            if (key.equals("2")) {
                myMap.put("ex1", "4"); //Non-exception code; Map do not put entry ("ex1","4") because of already existed key = "ex1";
                myMap.put("4","4"); //throws java.util.ConcurrentModificationException because of new element adding
            }
        }
    }

    public static void main(String... args) {
        NonThreadSafeIteratorExample.arrayListModification();
        NonThreadSafeIteratorExample.mapModification();
        ThreadSafeIteratorExample.main();
    }
}
