package santander.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "error")
public class ErrorResponse {

    private List<String> details;
    private HttpStatus httpStatus;


    public ErrorResponse(List<String> details, HttpStatus httpStatus){
        this.details = details;
        this.httpStatus = httpStatus;
    }

}