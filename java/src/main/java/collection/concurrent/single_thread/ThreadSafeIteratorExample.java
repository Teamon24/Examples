package collection.concurrent.single_thread;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Артем on 18.12.2016.
 */

class ThreadSafeIteratorExample {

    public static void main(String... args) {
        System.out.println("Concurrent order");
        List<String> myList = new CopyOnWriteArrayList<String>();

        myList.add("ex1");
        myList.add("2");
        myList.add("3");
        myList.add("4");
        myList.add("5");

        Iterator<String> it = myList.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println("List Value:" + value);
            if (value.equals("3")) {
                myList.remove("4");
                myList.add("6");
                myList.add("7");
            }
        }
        System.out.println("List Size:" + myList.size());

        Map<String, String> myMap = new ConcurrentHashMap<String, String>();
        myMap.put("ex1", "ex1");
        myMap.put("2", "2");
        myMap.put("3", "3");

        Iterator<String> it1 = myMap.keySet().iterator();
        while (it1.hasNext()) {
            String key = it1.next();
            System.out.println("Map Value:" + myMap.get(key));
            if (key.equals("ex1")) {
                myMap.remove("3");
                myMap.put("4", "4");
                myMap.put("5", "5");
            }
        }
        System.out.println("Map Size:" + myMap.size());
    }
}