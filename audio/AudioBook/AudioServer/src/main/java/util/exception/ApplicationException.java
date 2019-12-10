package util.exception;

public class ApplicationException extends Exception {
    private int errorCode;

    public ApplicationException(int message){
        super(String.valueOf(message));
        this.errorCode = message;
    }

    public ApplicationException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }


    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }



}