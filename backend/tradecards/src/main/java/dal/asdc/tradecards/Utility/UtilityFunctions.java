package dal.asdc.tradecards.Utility;

import org.springframework.stereotype.Component;

@Component
public class UtilityFunctions {
    public int generateOTP() {
        return (int) Math.floor(100000 + Math.random() * 900000);
    }
}
