package components.support_classes.exceptions;


/**
 * Пустой класс ошибки, содержащий лишь имя
 * (Нужен был чтобы как-то отличить самодельный exception о некорректном запросе http)
 */
public class IncorrectBodyFormatException extends Exception {

    public IncorrectBodyFormatException(String message) {
        super(message);
    }

    public IncorrectBodyFormatException() {
        super();
    }
}
