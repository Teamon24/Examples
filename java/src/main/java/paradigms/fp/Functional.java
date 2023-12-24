package paradigms.fp;

/**
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>1. What Is Functional Programming</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>Basically, functional programming is a style of writing computer programs that treat computations as evaluating mathematical functions. So, what is a function in mathematics?
 * <p>A function is an expression that relates an input set to an output set.
 * <p>Importantly, the output of a function depends only on its input. More interestingly, we can compose two or more functions together to get a new function.
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>1.1. Lambda Calculus</strong>
 * <p>To understand why these definitions and properties of mathematical functions are important in programming, we'll have to go a little back in time. In the 1930s, mathematician Alonzo Chruch developed a formal system to express computations based on function abstraction. This universal model of computation came to be known as the Lambda Calculus.
 *
 * <p>Lambda calculus had a tremendous impact on developing the theory of programming languages, particularly functional programming languages. Typically, functional programming languages implement lambda calculus.
 *
 * <p>As lambda calculus focuses on function composition, functional programming languages provide expressive ways to compose software in function composition.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>1.2. Categorization of Programming Paradigms</strong></p>
 * <p>Of course, functional programming is not the only programming style in practice. Broadly speaking, programming styles can be categorized into imperative and declarative programming paradigms:
 *
 * <p>The <strong>IMPERATIVE approach defines a program as a sequence of statements that change the program's state until it reaches the final state</strong>. Procedural programming is a type of imperative programming where we construct programs using procedures or subroutines. One of the popular programming paradigms known as object-oriented programming (OOP) extends procedural programming concepts.
 *
 * <p>In contrast, the <strong>DECLARATIVE approach expresses the logic of a computation without describing its control flow</strong> in terms of a sequence of statements. Simply put, the declarative approach's focus is to define what the program has to achieve rather than how it should achieve it. Functional programming is a sub-set of the declarative programming languages.
 *
 * <p>These categories have further sub-categories, and the taxonomy gets quite complex, but we'll not get into that for this tutorial.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>1.3. Categorization of Programming Languages</strong>
 * Any attempt to formally categorize the programming languages today is an academic effort in itself! However, we'll try to understand how programming languages are divided based on their support for functional programming for our purposes.
 *
 * <p>Pure functional languages, like Haskell, only allow pure functional programs.
 *
 * <p>Other languages, however, allow both <strong>functional and procedural programs</strong> and are considered impure functional languages. Many languages fall into this category, including Scala, Kotlin, and Java.
 *
 * <p>It's important to understand that most of the popular programming languages today are general-purpose languages, and hence they tend to support multiple programming paradigms.
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3. Fundamental Principles and Concepts</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>This section will cover some of the basic principles of functional programming and how to adopt them in Java. Please note that many features we'll be using haven't always been part of Java, and it's advisable to be on Java 8 or later to exercise functional programming effectively.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.1. First-Class and Higher-Order Functions</strong>
 * <p>A programming language is said to have first-class functions if it treats functions as first-class citizens. Basically, it means that <strong>functions are allowed to support all operations typically available to org.home.other entities</strong>:
 * <ul>
 * <li> assigning functions to variables</li>
 * <li> passing them as arguments to org.home.other functions</li>
 * <li> returning them as values from org.home.other functions</li>
 * </ul>
 *
 * <p>This property makes it possible to define higher-order functions in functional programming.
 * <ul>
 * <strong>Higher-order functions are capable of receiving function as arguments and returning a function as a result.</strong>
 * </ul>
 * This further enables several techniques in functional programming like function composition and currying.
 *
 * <p>Traditionally it was only possible to pass functions in Java using constructs like functional interfaces or anonymous inner classes. Functional interfaces have exactly one abstract method and are also known as Single Abstract Method (SAM) interfaces.
 *
 * <p>Let's say we have to provide a custom comparator to Collections.sort method:
 * <pre>{@code
 * Collections.sort(numbers, new Comparator<Integer>() {
 *     @Override
 *     public int compare(Integer n1, Integer n2) {
 *         return n1.compareTo(n2);
 *     }
 * });
 * }</pre>
 * <p>As we can see, this is a tedious and verbose technique — certainly not something that encourages developers to adopt functional programming. Fortunately, Java 8 brought many new features to ease the process, like lambda expressions, method references, and predefined functional interfaces.
 * <p>Let's see how a lambda expression can help us with the same task:
 * <pre>{@code Collections.sort(numbers, (n1, n2) -> n1.compareTo(n2));}</pre>
 * Definitely, this is more concise and understandable. However, please note that while this may give us the impression of using functions as first-class citizens in Java, that's not the case.
 *
 * <p>Behind the syntactic sugar of lambda expressions, Java still wraps these into functional interfaces. Hence, Java treats a lambda expression as an Object, which is, in fact, the true first-class citizen in Java.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.2. Pure Functions</strong>
 * <p>The definition of <strong>pure function emphasizes that a pure function should return a value based only on the arguments and should have no side effects</strong>. Now, this can sound quite contrary to all the best practices in Java.
 *
 * <p>Java, being an object-oriented language, recommends encapsulation as a core programming practice. It encourages hiding an object's internal state and exposing only necessary methods to access and modify it. Hence, these methods aren't strictly pure functions.
 * <p>Of course, encapsulation and org.home.other object-oriented principles are only recommendations and not binding in Java. In fact, developers have recently started to realize the value of defining immutable states and methods without side-effects.
 * <p>Let's say we want to find the sum of all the numbers we've just sorted:
 * <pre>{@code
 * Integer sum(List<Integer> numbers) {
 * return numbers.stream().collect(Collectors.summingInt(Integer::intValue));
 * }
 * }</pre>
 * <p>Now, this method depends only on the arguments it receives, hence, it's deterministic. Moreover, it doesn't produce any side effects.
 * <p>Side effects can be anything apart from the intended behavior of the method. For instance, side-effects can be as simple as updating a local or global state or saving to a database before returning a value. Purists also treat logging as a side effect, but we all have our own boundaries to set!
 * <p>We may, however, reason about how we deal with legitimate side effects. For instance, we may need to save the result in a database for genuine reasons. Well, there are techniques in functional programming to handle side effects while retaining pure functions.
 * <p>We'll discuss some of them in later sections.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.3. Immutability</strong>
 * <p>Immutability is one of the core principles of functional programming, and it <strong>refers to the property that an entity can't be modified after being instantiated</strong>. Now in a functional programming language, this is supported by design at the language level. But, in Java, we have to make our own decision to create immutable data structures.
 * <p>Please note that Java itself provides several built-in immutable types, for instance, String. This is primarily for security reasons, as we heavily use String in class loading and as keys in hash-based data structures. There are several org.home.other built-in immutable types like primitive wrappers and math types.
 *
 * <p>But what about the data structures we create in Java? Of course, they are not immutable by default, and we have to make a few changes to achieve immutability. The use of the final keyword is one of them, but it doesn't stop there:
 * <pre>{@code
 * @AllArgConstructor
 * public class ImmutableData {
 *      @Getter
 *      private final String someData;
 *      @Getter
 *      private final AnotherImmutableData anotherImmutableData;
 * }
 * }</pre>
 * <pre>{@code
 * @AllArgConstructor
 * public class AnotherImmutableData {
 *      @Getter
 *      private final Integer someOtherData;
 * }
 * }</pre>
 * <p>Note that we have to observe a few rules diligently:
 * <ul>
 * <li>All fields of an immutable data structure must be immutable</li>
 * <li>This must apply to all the nested types and collections (including what they contain) as well</li>
 * <li>There should be one or more constructors for initialization as needed</li>
 * <li>There should only be accessor methods, possibly with no side-effects</li>
 * </ul>
 *
 * It's <strong>not easy to get it completely right every time</strong>, especially when the data structures start to get complex. However, several external libraries can make working with immutable data in Java easier. For instance, Immutables and Project Lombok provide ready-to-use frameworks for defining immutable data structures in Java.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.4. Referential Transparency</strong>
 * <p>Referential transparency is perhaps one of the more difficult principles of functional programming to understand. The concept is pretty simple, though. We call an expression referentially transparent if replacing it with its corresponding value has no impact on the program's behavior.
 *
 * <p>This enables some powerful techniques in functional programming like higher-order functions and lazy evaluation. To understand this better, let's take an example:
 * <pre>{@code
 * public class SimpleData {
 *      private Logger logger = Logger.getGlobal();
 *      private String data;
 *      public String getData() {
 *          logger.log(Level.INFO, "Get data called for SimpleData");
 *          return data;
 *      }
 *
 *      public SimpleData setData(String data) {
 *          logger.log(Level.INFO, "Set data called for SimpleData");
 *          this.data = data;
 *          return this;
 *      }
 * }
 * }</pre>
 * <p>This is a typical POJO class in Java, but we're interested in finding if this provides referential transparency. Let's observe the following statements:
 * <pre>{@code
 * String data = new SimpleData().setData("Baeldung").getData();
 * logger.log(Level.INFO, new SimpleData().setData("Baeldung").getData());
 * logger.log(Level.INFO, data);
 * logger.log(Level.INFO, "Baeldung");
 * }</pre>
 * <p>The three calls to logger are semantically equivalent but not referentially transparent. The first call is not referentially transparent as it produces a side-effect. If we replace this call with its value as in the third call, we'll miss the logs.
 *
 * <p>The second call is also not referentially transparent as SimpleData is mutable. A call to data.setData anywhere in the program would make it difficult for it to be replaced with its value.
 *
 * <p>So basically, <strong>for referential transparency, we need our functions to be pure and immutable</strong>. These are the two preconditions we've already discussed earlier. As an interesting outcome of referential transparency, we produce context-free code. In org.home.other words, we can execute them in any order and context, which leads to different optimization possibilities.
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3. Functional Programming Techniques</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>The functional programming principles that we discussed earlier enable us to use several techniques to benefit from functional programming. In this section, we'll cover some of these popular techniques and understand how we can implement them in Java.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.1. Function Composition</strong>
 * <p>Function composition refers to <strong>composing complex functions by combining simpler functions</strong>. This is primarily achieved in Java using functional interfaces, which are, in fact, target types for lambda expressions and method references.
 *
 * <p>Typically, <strong>any interface with a single abstract method can serve as a functional interface</strong>. Hence, we can define a functional interface quite easily. However, Java 8 provides us many functional interfaces by default for different use cases under the package java.util.function.
 *
 * <p>Many of these functional interfaces provide support for function composition in terms of default and static methods. Let's pick the Function interface to understand this better. Function is a simple and generic functional interface that accepts one argument and produces a result.
 *
 * <p>It also provides two default methods, compose and andThen, which will help us in function composition:
 * <pre>{@code
 * Function<Double, Double> log = (value) -> Math.log(value);
 * Function<Double, Double> sqrt = (value) -> Math.sqrt(value);
 * Function<Double, Double> logThenSqrt = sqrt.compose(log);
 * logger.log(Level.INFO, String.valueOf(logThenSqrt.apply(3.14)));
 * // Output: 1.06
 * Function<Double, Double> sqrtThenLog = sqrt.andThen(log);
 * logger.log(Level.INFO, String.valueOf(sqrtThenLog.apply(3.14)));
 * // Output: 0.57
 * }</pre>
 * <p>Both these methods allow us to compose multiple functions into a single function but offer different semantics. While compose applies the function passed in the argument first and then the function on which it's invoked, andThen does the same in reverse.
 *
 * <p>Several org.home.other functional interfaces have interesting methods to use in function composition, such as the default methods and, or, and negate in the Predicate interface. While these functional interfaces accept a single argument, there are two-arity specializations, like BiFunction and BiPredicate.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.2. Monads</strong>
 * <p>Many of the functional programming concepts derive from Category Theory, which is a general theory of functions in mathematics. It presents several concepts of categories like functors and natural transformations. For us, it's only important to know that this is the basis of using monads in functional programming.
 *
 * <p>Formally, a monad is an abstraction that allows structuring programs generically. So basically, a <strong>monad allows</strong>:
 * <ul>
 *  <li>to wrap a value</li>
 *  <li>to apply a set of transformations</li>
 *  <li>to get the value back with all transformations applied.</li>
 * </ul>
 * Of course, there are <strong>three laws that any monad needs to follow</strong>:
 * <ul>
 * <li>left identity</li>
 * <li>right identity</li>
 * <li>and associativity</li>
 * </ul>
 * – but we'll not get into the details.
 *
 * <p>In Java, there are a few monads that we use quite often, like Optional and Stream:
 * <pre>{@code
 * Optional.of(2).flatMap(f -> Optional.of(3).flatMap(s -> Optional.of(f + s)))
 * }</pre>
 * <p>Now, why do we call Optional a monad? Here, Optional allows us to wrap a value using the method of and apply a series of transformations. We're applying the transformation of adding another wrapped value using the method flatMap.
 *
 * <p>If we want, we can show that Optional follows the three laws of monads. However, critics will be quick to point out that an Optional does break the monad laws under some circumstances. But, for most practical situations, it should be good enough for us.
 *
 * <p>If we understand monads' basics, we'll soon realize that there are many org.home.other examples in Java, like Stream and CompletableFuture. They help us achieve different objectives, but they all have a standard composition in which context manipulation or transformation is handled.
 *
 * <p>Of course, <strong>we can define our own monad types in Java to achieve different objectives</strong> like log monad, report monad, or audit monad. Remember how we discussed handling side-effects in functional programming? Well, as it appears, the monad is one of the functional programming techniques to achieve that.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.3. Currying</strong>
 * <p>Currying is a mathematical <strong>technique of converting a function that takes multiple arguments into a sequence of functions that take a single argument</strong>. But, why do we need them in functional programming? It gives us a powerful composition technique where we do not need to call a function with all its arguments.
 * <p>Moreover, a curried function does not realize its effect until it receives all the arguments.
 * <p>In pure functional programming languages like Haskell, currying is well supported. In fact, all functions are curried by default. However, in Java, it's not that straightforward:
 * <pre>{@code
 * Function<Double, Function<Double, Double>> weight = mass -> gravity -> mass * gravity;
 * Function<Double, Double> weightOnEarth = weight.apply(9.81);
 * logger.log(Level.INFO, "My weight on Earth: " + weightOnEarth.apply(60.0));
 * Function<Double, Double> weightOnMars = weight.apply(3.75);
 * logger.log(Level.INFO, "My weight on Mars: " + weightOnMars.apply(60.0));
 * }</pre>
 * <p>Here, we've defined a function to calculate our weight on a planet. While our mass remains the same, gravity varies by the planet we're on. We can partially apply the function by passing just the gravity to define a function for a specific planet. Moreover, we can pass this partially applied function around as an argument or return value for arbitrary composition.
 * <p>Currying depends upon the language to provide two fundamental features: lambda expressions and closures. Lambda expressions are anonymous functions that help us to treat code as data. We've seen earlier how to implement them using functional interfaces.
 * <p>Now, a lambda expression may close upon its lexical scope, which we define as its closure. Let's see an example:
 * <pre>{@code
 * private static Function<Double, Double> weightOnEarth() {
 *      final double gravity = 9.81;
 *      return mass -> mass * gravity;
 * }
 * }</pre>
 * <p>Please note how the lambda expression, which we return in the method above, depends on the enclosing variable, which we call closure. Unlike org.home.other functional programming languages, Java has a limitation that the enclosing scope has to be final or effectively final.
 *
 * <p>As an interesting outcome, currying also allows us to create a functional interface in Java of arbitrary arity.
 --------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.4. Recursion</strong>
 * <p>Recursion is another powerful technique in functional programming that <strong>allows us to break down a problem into smaller pieces</strong>. The main benefit of recursion is that it helps us eliminate the side effects, which is typical of any imperative style looping.
 *
 * <p>Let's see how we calculate the factorial of a number using recursion:
 * <pre>{@code
 * Integer factorial(Integer number) {
 *      return (number == 1) ? 1 : number * factorial(number - 1);
 * }
 * }</pre>
 * <p>Here, we call the same function recursively until we reach the base case and then start to calculate our result. Notice that we're making the recursive call before calculating the result at each step or in words at the head of the calculation. Hence, <strong>this style of recursion is also known as HEAD RECURSION</strong>.
 *
 * <p>A drawback of this type of recursion is that every step has to hold the state of all previous steps until we reach the base case. This is not really a problem for small numbers, but holding the state for large numbers can be inefficient.
 *
 * <p>A solution is a <strong>slightly different implementation of the recursion known as TAIL RECURSION</strong>. Here, we ensure that the recursive call is the last call a function makes. Let's see how we can rewrite the above function to use tail recursion:
 * <pre>{@code
 * Integer factorial(Integer number, Integer result) {
 *      return (number == 1) ? result : factorial(number - 1, result * number);
 * }
 * }</pre>
 * <p>Notice the use of an accumulator in the function, eliminating the need to hold the state at every step of recursion. The real benefit of this style is to leverage compiler optimizations where the compiler can decide to let go of the current function's stack frame, a technique known as tail-call elimination.
 *
 * <p>While many languages like Scala supports tail-call elimination, Java still does not have support for this. This is part of the backlog for Java and will perhaps come in some shape as part of larger changes proposed under Project Loom.</p>
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>4. Why Functional Programming Matters?</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>After going through the tutorial so far, we must wonder why we even want to take this much effort. For someone coming from a Java background, <strong>the shift that functional programming demands are not trivial</strong>. So, there should be some really promising advantages for adopting functional programming in Java.
 *
 * <p>The biggest advantage of adopting functional programming in any language, including Java, is <strong>pure functions and immutable states</strong>. If we think in retrospect, most of the programming challenges are rooted in the side-effects and mutable state one way or the org.home.other. Simply getting rid of them makes our program easier to read, reason about, test, and maintain.
 *
 * <p>Declarative programming, as such, <strong>leads to very concise and readable programs</strong>. Functional programming, being a subset of declarative programming, offers several constructs like higher-order functions, function composition, and function chaining. Think of the benefits that Stream API has brought into Java 8 for handling data manipulations.
 *
 * <p>But don't get tempted to switch over unless completely ready. Please note that functional programming is not a simple design pattern that we can immediately use and benefit from. Functional programming is <strong>more of a change in how we reason about problems and their solutions</strong> and how to structure the algorithm.
 *
 * <p>So, before we start using functional programming, we must train ourselves to think about our programs in terms of functions.
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>5. Is Java a Suitable Fit?</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>While it's difficult to deny functional programming benefits, we cannot help but ask ourselves if Java is a suitable choice for it. Historically, <strong>Java evolved as a general-purpose programming language more suitable for object-oriented programming</strong>. Even thinking of using functional programming before Java 8 was tedious! But things have definitely changed after Java 8.
 * <p>The very fact that <strong>there are no true function types in Java</strong> goes against functional programming's basic principles. The functional interfaces in the disguise of lambda expressions make up for it largely, at least syntactically. Then, the fact that <strong>types in Java are inherently mutable</strong> and we have to write so much boilerplate to create immutable types does not help.
 * <p>We expect org.home.other things from a functional programming language that are missing or difficult in Java. For instance, <strong>the default evaluation strategy for arguments in Java is eager</strong>. But, <strong>lazy evaluation is a more efficient and recommended way in functional programming</strong>.
 * <p>We can still achieve lazy evaluation in Java using operator short-circuiting and functional interfaces, but it's more involved.
 * <p>The list is certainly not complete and can include generics support with type-erasure, missing support for tail-call optimization, and org.home.other things. However, we get a broad idea. <strong>Java is definitely not suitable for starting a program from scratch in functional programming</strong>.
 * <p>But what if we already have an existing program written in Java, probably in object-oriented programming? Nothing stops us from getting some of the benefits of functional programming, especially with Java 8.
 * <p>This is where most of the benefits of functional programming lie for a Java developer. <strong>A combination of object-oriented programming with the benefits of functional programming can go a long way</strong>.
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>6. Conclusion</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>In this tutorial, we went through the basics of functional programming. We covered the fundamental principles and how we can adopt them in Java. Further, we discussed some popular techniques in functional programming with examples in Java.
 *
 * <p>Finally, we covered some of the benefits of adopting functional programming and answered if Java is suitable for the same.
 */
public interface Functional {
}
