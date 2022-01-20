package core.concurrency.safe_iteration;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class ThreadSafeIteratorExample {

    public static void main(String... args) {
        Map<String, String> hashMap = new HashMap<>();
        List<String> arrayList = new ArrayList<>();

        tryIterateAndModify(arrayList);
        tryIterateAndModify(hashMap);

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        iterateAndModify(concurrentHashMap);
        iterateAndModify(copyOnWriteArrayList);
    }

    private static void tryIterateAndModify(Map<String, String> hashMap) {
        try {
            iterateAndModify(hashMap);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    private static void tryIterateAndModify(List<String> arrayList) {
        try {
            iterateAndModify(arrayList);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    private static void iterateAndModify(List<String> myList) {
        String searchedElement = "3";
        myList.add(searchedElement);
        myList.add("4");
        myList.add("5");

        Iterator<String> it = myList.iterator();
        while (it.hasNext()) {
            String value = it.next();
            if (value.equals(searchedElement)) {
                it.remove(); //Non-exception code
                myList.remove("4"); //can throw java.util.ConcurrentModificationException
                myList.add("6"); //can throw java.util.ConcurrentModificationException
                myList.add("7"); //can throw java.util.ConcurrentModificationException
            }
        }
    }

    private static void iterateAndModify(Map<String, String> myMap) {
        String searchedKey = "2";
        myMap.put("ex1", "ex1");
        myMap.put(searchedKey, "2");
        myMap.put("3", "3");

        for (String key : myMap.keySet()) {
            if (searchedKey.equals(key)) {
                myMap.put("ex1", "4"); //Non-exception code; Map do not put entry ("ex1","4") because of already existed key = "ex1";
                myMap.put("4", "4"); //can throw java.util.ConcurrentModificationException because of new element adding
            }
        }
    }
}