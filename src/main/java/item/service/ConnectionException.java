package item.service;

public class ConnectionException extends Throwable {
    public String message;
    public Exception exception;
    public ConnectionException(String message, Exception exception) {
            this.message = message;
            this.exception = exception;
        }
    }
