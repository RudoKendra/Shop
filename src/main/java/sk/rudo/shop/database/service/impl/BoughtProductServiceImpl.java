package sk.rudo.shop.database.service.impl;

import org.springframework.stereotype.Service;
import sk.rudo.shop.database.repository.BoughtProductRepository;
import sk.rudo.shop.database.service.api.BoughtProductService;
import sk.rudo.shop.domain.BoughtProduct;

import java.util.List;

@Service
public class BoughtProductServiceImpl implements BoughtProductService {

    private final BoughtProductRepository boughtProductRepository;

    public BoughtProductServiceImpl(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;
    }

    @Override
    public void add(BoughtProduct boughtProduct) {
        boughtProductRepository.add(boughtProduct);
    }

    @Override
    public List<BoughtProduct> getAllByCustomerId(int customerId) {
        return boughtProductRepository.getAllCustomerId(customerId);
    }
}
