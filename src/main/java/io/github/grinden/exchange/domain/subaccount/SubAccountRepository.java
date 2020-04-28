package io.github.grinden.exchange.domain.subaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubAccountRepository extends JpaRepository<SubAccount, Long> {
}
