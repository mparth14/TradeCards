package dal.asdc.tradecards.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class OTPVerificationFailedException extends Exception{
    public OTPVerificationFailedException(String message, Exception e){
        super(message, e);
    }
}
