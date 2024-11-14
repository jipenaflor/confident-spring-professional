package com.jeromepenaflor.myfancypdfinvoices.springboot.repository;

import com.jeromepenaflor.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {
    // requires domain object (invoice) and id datatype (string)

    /*
    Spring Data JDBC does not support automatic queries-by-method names like Spring Data JPA,
    so it requires the query annotation with sql statement
     */
    @Query("SELECT id, pdf_url, user_id, amount FROM \"invoices\" where user_id = :userId")
    Iterable<Invoice> findByUserId(@Param("userId") String userId);
}
