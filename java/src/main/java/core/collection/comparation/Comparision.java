package core.collection.comparation;

import java.util.*;
import java.util.LinkedList;

/**
 * 31.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class Comparision {
    private String method;
    private int arrayListTime;
    private int linkedListTime;
    private int hashSetTime;
    private int treeSetTime;
    private static int n = 100_000;
    public Comparision() {
    }

    public static void main(String[] args) {

        ArrayList<String> arrayList   = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        HashSet<String> hashSet       = new HashSet<>();
        TreeSet<String> treeSet       = new TreeSet<>();

        System.out.println(getTimeMsOfInsert(arrayList,  arrayList.getClass().getSimpleName()));
        System.out.println(getTimeMsOfInsert(linkedList, linkedList.getClass().getSimpleName()));
        System.out.println(getTimeMsOfInsert(hashSet,    hashSet.getClass().getSimpleName()));
        System.out.println(getTimeMsOfInsert(treeSet,    treeSet.getClass().getSimpleName()));

        System.out.println(getTimeMsOfGet(arrayList,    arrayList.getClass().getSimpleName()));
        System.out.println(getTimeMsOfGet(linkedList,   linkedList.getClass().getSimpleName()));
        System.out.println(getTimeMsOfContains(hashSet, hashSet.getClass().getSimpleName()));
        System.out.println(getTimeMsOfContains(treeSet, treeSet.getClass().getSimpleName()));
    }


    public static long getTimeMsOfInsert(Set set, String info) {
        Date start = new Date();
        insertN(set);
        Date finish = new Date();
        System.out.println(info + " add in #0: 100_000");
        return finish.getTime() - start.getTime();
    }

    public static long getTimeMsOfContains(Set set, String info) {
        Date start = new Date();
        containsN(set);
        Date finish = new Date();
        System.out.println(info + " contains: 100_000");
        return finish.getTime() - start.getTime();
    }

    public static long getTimeMsOfInsert(List list, String info) {
        Date start = new Date();
        insertN(list);
        Date finish = new Date();
        System.out.println(info + " add in #0: 100_000");
        return finish.getTime() - start.getTime();
    }

    public static long getTimeMsOfGet(List list, String info) {
        Date start = new Date();
        getN(list);
        Date finish = new Date();
        System.out.println(info + " get: 100_000");
        return finish.getTime() - start.getTime();
    }

    public static long getTimeMsOfSet(List list, String info) {
        Date start = new Date();
        setN(list);
        Date finish = new Date();
        System.out.println(info + " get: 100_000");
        return finish.getTime() - start.getTime();
    }

    private static void setN(List list) {
        for (int i = 0; i < n; i++) {
            list.set(n/2, String.valueOf(i));
        }
    }

    public static void insertN(List list) {
        for (int i = 0; i < n; i++) {
            list.add(0, String.valueOf(i));
        }
    }

    public static void insertN(Set set) {
        for (int i = 0; i < n; i++) {
            set.add(String.valueOf(i));
        }
    }

    public static void getN(List list) {
        for (int i = 0; i < 100000; i++) {
            list.get(n/2);
        }
    }

    public static void containsN(Set set) {
        for (int i = 0; i < n; i++) {
            set.contains(String.valueOf(i));
        }
    }
}
