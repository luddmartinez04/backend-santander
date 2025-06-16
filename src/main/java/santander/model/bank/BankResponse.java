package santander.model.bank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {


    private String name;
    @NotNull
    private String cuit;
    @Email
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

}