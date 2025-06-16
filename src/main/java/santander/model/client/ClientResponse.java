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
public class ClientResponse {


        @NotNull
        private String name;
        @NotNull
        private String idChannel;
        @NotNull
        private String dni;
        @Email
        private String email;
        @NotNull
        private Long bankId;


}
