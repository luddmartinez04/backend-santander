package santander.model.bank;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import santander.model.client.Client;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BANK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
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
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Client> clients = new ArrayList<>();
}
