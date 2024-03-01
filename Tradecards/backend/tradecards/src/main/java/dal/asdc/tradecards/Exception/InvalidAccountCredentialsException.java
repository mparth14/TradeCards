package dal.asdc.tradecards.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidAccountCredentialsException extends Exception{
    public InvalidAccountCredentialsException(String message, Exception e){
        super(message, e);
    }
}
