package sk.rudo.shop.database.service.api;

import org.springframework.lang.Nullable;
import sk.rudo.shop.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers ();
    @Nullable
    Customer getCustomerById (int id);
    @Nullable
    Integer addNewCustomer (Customer customer);

}
