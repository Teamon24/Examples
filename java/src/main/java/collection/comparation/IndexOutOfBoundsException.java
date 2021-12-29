package collection.comparation;

public class IndexOutOfBoundsException extends Throwable {
    public IndexOutOfBoundsException(int index){
        super("Элемент с номером \"" + index + "\" не существует");
    }
}
