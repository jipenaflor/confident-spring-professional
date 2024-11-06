package com.jeromepenaflor.myfancypdfinvoices.springboot.repository;

import com.jeromepenaflor.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {
    // requires domain object (invoice) and id datatype (string)
}
