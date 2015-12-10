/**
 * 
 */
package de.fhbingen.epro.controller.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 * @author Johannes Hiemer
 *
 */
@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}