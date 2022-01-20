package patterns.basic.other_source.creational.factory.simple.ex2;

public class ReaderCreationStrategyFactory {

    enum StrategyType {
        XML,
        CSV,
        DB
    }

    public static ReaderCreationStrategy createStrategy(StrategyType type) {
        switch (type) {
            case XML:
                return new XMLReaderCreationStrategy();
            case CSV:
                return new CSVReaderCreationStrategy();
            case DB:
                return new DBReaderCreationStrategy();
            default:
                String message = String.format("Add appropriate ReaderCreationStrategy for this StrategyType enum: %s", type.toString());
                throw new RuntimeException(message);
        }
    }
}
