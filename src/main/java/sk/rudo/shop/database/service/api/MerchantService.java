package sk.rudo.shop.database.service.api;

import org.springframework.lang.Nullable;
import sk.rudo.shop.domain.Merchant;

import java.util.List;

public interface MerchantService {

    List<Merchant> getMerchants ();
    @Nullable
    Merchant getMerchantById (int id);
    @Nullable
    Integer addMerchant (Merchant merchant);
}
