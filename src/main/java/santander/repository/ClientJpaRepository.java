package santander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santander.model.client.Client;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {


}
