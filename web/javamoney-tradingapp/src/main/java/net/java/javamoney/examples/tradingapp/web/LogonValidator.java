/*
 * JSR 354 Stock-Trading Example
 * Copyright 2005-2013, Werner Keil and individual contributors by the @author tag. 
 * See the copyright.txt in the distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.java.javamoney.examples.tradingapp.web;

import net.java.javamoney.examples.tradingapp.business.Credentials;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


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