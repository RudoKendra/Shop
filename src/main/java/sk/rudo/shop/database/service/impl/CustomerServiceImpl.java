package sk.rudo.shop.database.service.impl;

import org.springframework.stereotype.Service;
import sk.rudo.shop.database.repository.CustomerRepository;
import sk.rudo.shop.database.service.api.CustomerService;
import sk.rudo.shop.domain.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public Integer addNewCustomer(Customer customer) {
        return customerRepository.addNewCustomer(customer);
    }
}
