/**
 * <a href="https://courses.cs.washington.edu/courses/cse373/18au/files/slides/lecture13.pdf">
 *     <strong>Введение в hash code и hash-таблицы </strong>
 * </a>
 *
 * <p><a href="https://www.geeksforgeeks.org/hash-functions-and-list-types-of-hash-functions/?ref=rp">
 *     <strong>Функции вычисления hash code и их типы</strong>
 * </a></p>
 *
 * Handling Hash Collisions
 *
 * The intrinsic behavior of hash tables brings up a relevant aspect of these data structures: Even with an efficient hashing algorithm, two or more objects might have the same hash code even if they're unequal. So, their hash codes would point to the same bucket even though they would have different hash table keys.
 *
 * This situation is commonly known as a hash collision, and various methods exist for handling it, with each one having their pros and cons. Java's HashMap uses the separate chaining method for handling collisions:
 *
 * “When two or more objects point to the same bucket, they're simply stored in a linked list. In such a case, the hash table is an array of linked lists, and each object with the same hash is appended to the linked list at the bucket index in the array.
 *
 * In the worst case, several buckets would have a linked list bound to it, and the retrieval of an object in the list would be performed linearly.”
 *
 * Hash collision methodologies show in a nutshell why it's so important to implement hashCode() efficiently.
 *
 * Java 8 brought an interesting enhancement to HashMap implementation. If a bucket size goes beyond the certain threshold, a tree map replaces the linked list. This allows achieving O(logn) lookup instead of pessimistic O(n).
 */
package core.base.hashcode;