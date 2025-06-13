package santander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santander.model.bank.Bank;

@Repository
public interface BankJpaRepository extends JpaRepository<Bank, Long> {
}
