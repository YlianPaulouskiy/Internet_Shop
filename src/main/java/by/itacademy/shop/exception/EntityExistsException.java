package by.itacademy.shop.exception;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException() {
        this("Entity already exist.");
    }

    public EntityExistsException(String message) {
        super(message);
    }
}
