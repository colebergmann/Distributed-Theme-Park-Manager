package tayto.parkmanager.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid attraction id")
public class NoSuchAttractionException extends RuntimeException {

}
