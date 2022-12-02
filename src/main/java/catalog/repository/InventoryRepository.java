package catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catalog.beans.InventoryItem;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 26, 2022
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

}
