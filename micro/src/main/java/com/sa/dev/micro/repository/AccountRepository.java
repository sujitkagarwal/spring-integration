package com.sa.dev.micro.repository;


import com.sa.dev.micro.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Account data implemented using Spring Data JPA.
 *
 * @author sujit agarwal
 */


@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {



    Account findByAccountNumber(String accountNumber);
    /**
     * Fetch the number of accounts known to the system.
     *
     * @return The number of accounts.
     */
    @Query("SELECT count(*) from Account")
    public int countAccounts();

}
