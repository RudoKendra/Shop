package sk.rudo.shop.database.service.impl;

import org.springframework.stereotype.Service;
import sk.rudo.shop.database.repository.CustomerAccountRepository;
import sk.rudo.shop.database.service.api.CustomerAccountService;
import sk.rudo.shop.domain.CustomerAccount;

@Service
public class CustomerServiceAccountImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    public CustomerServiceAccountImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public void addCustomerAccount(CustomerAccount customerAccount) {
        customerAccountRepository.add(customerAccount);
    }

    @Override
    public Double getMoney(int customerId) {
        return customerAccountRepository.getMoney(customerId);
    }

    @Override
    public void setMoney(int customerId, double money) {
        customerAccountRepository.setMoney(customerId, money);
    }
}
