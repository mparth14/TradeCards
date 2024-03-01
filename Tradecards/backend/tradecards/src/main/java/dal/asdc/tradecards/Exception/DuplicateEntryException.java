package dal.asdc.tradecards.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateEntryException extends Exception{
    public DuplicateEntryException(String message, Exception e){
        super(message, e);
    }
}

