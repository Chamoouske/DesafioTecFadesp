package fadesp.desafio.tec.desafio.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationErrorException extends RuntimeException{
    private final String field;
    public ValidationErrorException(String field, String message){
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
