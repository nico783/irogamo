package myapp.exception;

public class TaskNotFindException extends IllegalArgumentException {

    public TaskNotFindException(String message) {
        super(message);
    }

}
