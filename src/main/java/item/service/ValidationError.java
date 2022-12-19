package item.service;

public class ValidationError extends RuntimeException {
    private final String message;
    public ValidationError(String message) {
        this.message = message;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
