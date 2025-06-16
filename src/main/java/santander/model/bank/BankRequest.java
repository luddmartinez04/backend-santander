package santander.model.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankRequest {

    private String name;
    private String cuit;
    @Email
    private String email;
    private String phoneNumber;
}
