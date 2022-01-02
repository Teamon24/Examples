package core.collection.comparation;

import java.util.*;

/**
 * 12.01.2017.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class Comparision2 {
    static ArrayList<Integer>            arrayList     = new ArrayList<>();
    static java.util.LinkedList<Integer> list          = new java.util.LinkedList<>();
    static core.collection.comparation.LinkedList<Integer> list2         = new core.collection.comparation.LinkedList<>();
    static HashSet<Integer>              hashSet       = new HashSet<>();
    static LinkedHashSet<Integer>        linkedHashSet = new LinkedHashSet<>();
    static TreeSet<Integer>              treeSet       = new TreeSet<>();
    static PriorityQueue<Integer>        priorityQueue = new PriorityQueue<>();
    static HashMap<Integer, Integer>     hashMap       = new HashMap<>();
    static TreeMap<Integer, Integer>     treeMap       = new TreeMap<>();


    static core.collection.comparation.Timer timer = new Timer(System::currentTimeMillis);
    static int n = 300_000;

    static Random random = new Random();
    static int[] indexes = new int[n];

    public static void compareAdd() {
//        for (int i = 0; i < n; i++)
//            priorityQueue.add(i);
//        timer.finish();
//        System.out.printf("%-15s" + "%-10s" + n + ": " + timer.result(),"PriorityQueue","#add");
        timer.start();
        for (int i = 0; i < n; i++)
            arrayList.add(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "ArrayList", "#add");

        for (int i = 0; i < n; i++)
            list.addLast(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "LinkedList", "#add");

        timer.start();
        for (int i = 0; i < n; i++)
            list2.addLast(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "myLinkedList", "#add");

        timer.start();
        for (int i = 0; i < n; i++)
            hashSet.add(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "HashSet", "#add");

        timer.start();
        for (int i = 0; i < n; i++)
            linkedHashSet.add(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "LinkedHashSet", "#add");

        timer.start();
        for (int i = 0; i < n; i++)
            hashMap.put(i, i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "HashMap", "#put");

        timer.start();
        for (int i = 0; i < n; i++)
            treeMap.put(i, i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "TreeMap", "#put");

        timer.start();
        for (int i = 0; i < n; i++)
            treeSet.add(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "TreeSet", "#add");

        System.out.println();
    }

    public static void compareSet() {
        for (int i = 0; i < n; i++)
            arrayList.set(indexes[i], 0);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "ArrayList", "#set");

        timer.start();
        for (int i = 0; i < n; i++)
            list.set(indexes[i], 0);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "LinkedList", "#set");

        timer.start();
        for (int i = 0; i < n; i++) {
            try {
                list2.set(indexes[i], 0);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "myLinkedList", "#set");
        System.out.println();

    }

    public static void compareGet() {
//        for (int i = 0; i < n; i++)
//            priorityQueue.poll();
//        timer.finish();
//        System.out.printf("%-15s" + "%-10s" + n + ": " + timer.result(),"PriorityQueue","#poll");
        timer.start();
        for (int i = 0; i < n; i++)
            arrayList.get(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "ArrayList", "#get");

        timer.start();
        for (int i = 0; i < n; i++)
            list.get(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "LinkedList", "#get");

        timer.start();
        for (int i = 0; i < n; i++) {
            try {
                list2.get(indexes[i]);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "myLinkedList", "#get");

        timer.start();
        for (int i = 0; i < n; i++)
            hashSet.contains(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "HashSet", "#contains");

        timer.start();
        for (int i = 0; i < n; i++)
            linkedHashSet.contains(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "LinkedHashSet", "#contains");

        timer.start();
        for (int i = 0; i < n; i++)
            hashMap.containsKey(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "HashMap", "#containsKey");

        timer.start();
        for (int i = 0; i < n; i++)
            treeMap.containsKey(i);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "TreeMap", "#containsKey");

        timer.start();
        for (int i = 0; i < n; i++)
            treeSet.contains(indexes[i]);
        timer.finish();
        System.out.printf("%-15s" + "%-15s" + n + ": " + timer.result(), "TreeSet", "#contains");


        System.out.println();
    }

    public static void main(String[] args) throws IndexOutOfBoundsException {
        for (int i = 0; i < n; i++)
            indexes[i] = i;

        compareAdd();

        compareSet();

        compareGet();
    }
}
