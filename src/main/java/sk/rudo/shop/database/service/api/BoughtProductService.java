package sk.rudo.shop.database.service.api;

import sk.rudo.shop.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {
    void add (BoughtProduct boughtProduct);

    List<BoughtProduct> getAllByCustomerId (int customerId);
}
