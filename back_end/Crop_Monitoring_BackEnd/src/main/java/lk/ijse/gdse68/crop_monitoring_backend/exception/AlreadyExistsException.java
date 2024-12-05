package lk.ijse.gdse68.crop_monitoring_backend.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(){}
    public AlreadyExistsException(String message){
        super(message);
    }
    public AlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
