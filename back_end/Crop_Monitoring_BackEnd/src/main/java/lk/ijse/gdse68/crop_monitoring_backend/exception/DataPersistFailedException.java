package lk.ijse.gdse68.crop_monitoring_backend.exception;

public class DataPersistFailedException extends RuntimeException {
    public DataPersistFailedException() {}
    public DataPersistFailedException(String message) {
        super(message);
    }
    public DataPersistFailedException(String message, Throwable cause) {}
}
