package santander.model.client;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import santander.model.bank.Bank;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String idChannel;
    @NotNull
    private String dni;
    @Email
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;
}
