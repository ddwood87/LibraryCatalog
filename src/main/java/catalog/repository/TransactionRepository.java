package catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catalog.beans.Transaction;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Dec 1, 2022
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
