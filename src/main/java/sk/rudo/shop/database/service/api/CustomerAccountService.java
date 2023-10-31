package sk.rudo.shop.database.service.api;


import org.springframework.lang.Nullable;
import sk.rudo.shop.domain.CustomerAccount;

public interface CustomerAccountService {

    void addCustomerAccount (CustomerAccount customerAccount);
    @Nullable
    Double getMoney (int customerId);
    void setMoney (int customerId, double money);
}
