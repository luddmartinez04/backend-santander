package santander.model.client;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String name;
    private String idChannel;
    private String dni;
    private String email;

}
