package catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catalog.beans.Book;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 19, 2022
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
