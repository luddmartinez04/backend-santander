package santander.model.bank;


import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String cuit;

}
