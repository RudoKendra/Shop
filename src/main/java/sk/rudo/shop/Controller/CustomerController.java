package sk.rudo.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.rudo.shop.database.service.api.CustomerAccountService;
import sk.rudo.shop.database.service.api.CustomerService;
import sk.rudo.shop.domain.Customer;
import sk.rudo.shop.domain.CustomerAccount;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerAccountService customerAccountService;

    public CustomerController(CustomerService customerService, CustomerAccountService customerAccountService) {
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
    }

    @GetMapping()
    public ResponseEntity getAll () {
        List<Customer> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity get (@PathVariable("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity add (@RequestBody Customer customer) {
        Integer id = customerService.addNewCustomer(customer);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/account")
    public ResponseEntity addAccount (@RequestBody CustomerAccount customerAccount) {
        customerAccountService.addCustomerAccount(customerAccount);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
