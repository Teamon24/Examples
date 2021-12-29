/**
 * Bridge is used when we need to decouple an abstraction from its implementation so that the two can vary
 * independently. This type of design pattern comes under structural pattern as this pattern decouples
 * implementation class and abstract class by providing a bridge structure between them.
 *
 * This pattern involves an interface which acts as a bridge which makes the functionality of concrete classes
 * independent of interface implementer classes. Both types of classes can be altered structurally without
 * affecting each other.
 *
 * We are demonstrating use of Bridge pattern via following example in which a circle can be drawn in different
 * colors using same abstract class method but different bridge implementer classes.
 */