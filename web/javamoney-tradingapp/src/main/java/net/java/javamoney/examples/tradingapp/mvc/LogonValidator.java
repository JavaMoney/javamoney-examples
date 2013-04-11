package net.java.javamoney.examples.tradingapp.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.java.javamoney.examples.tradingapp.domain.Credentials;

public class LogonValidator implements Validator {

    private final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) {
        return clazz.equals(Credentials.class);
    }

    public void validate(Object obj, Errors errors) {
        Credentials credentials = (Credentials) obj;
        if (credentials == null) {
            errors.rejectValue("username", "error.login.not-specified", null,
                    "Value required.");
        } else {
            logger.info("Validating user credentials for: "
                    + credentials.getUsername()
                    + " @ "
                    + credentials.getMarket());
            if (credentials.getUsername().equals("guest") == false) {
                errors.rejectValue("username", "error.login.invalid-user",
                        null, "Incorrect Username.");
            } else {
                if (credentials.getPassword().equals("guest") == false) {
                    errors.rejectValue("password", "error.login.invalid-pass",
                            null, "Incorrect Password.");
                }
            }

        }
    }

}