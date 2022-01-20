/**
 *
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Intent</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>When a business transaction is completed, all the updates are sent as one big unit of work to be persisted
 * in one go to minimize database round-trips.
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Explanation</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>Real-world example
 * <ul>Arms dealer has a database containing weapon information. Merchants all over the town are constantly updating
 * this information and it causes a high load on the database server. To make the load more manageable
 * we apply to Unit of Work pattern to send many small updates in batches.</ul>
 * <p>In plain words
 * <ul>Unit of Work merges many small database updates in a single batch to optimize the number of round-trips.</ul>
 * <p>MartinFowler.com says
 * <ul>Maintains a list of objects affected by a business transaction and coordinates the writing out of changes and
 * the resolution of concurrency problems.</ul>
 *
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>JPA</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>The persistence context is an implementation of the Unit of Work pattern. It keeps track of all loaded data,
 * tracks changes of that data, and is responsible to eventually synchronize any changes back to the database
 * at the end of the business transaction.
 */
package patterns.enterprise.unit_of_work;