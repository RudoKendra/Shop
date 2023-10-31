package sk.rudo.shop.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.rudo.shop.database.repository.MerchantRepository;
import sk.rudo.shop.database.service.api.MerchantService;
import sk.rudo.shop.domain.Merchant;

import java.util.List;
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> getMerchants() {
        return merchantRepository.getAllMerchants();
    }

    @Override
    public Merchant getMerchantById(int id) {
        return merchantRepository.getMerchantById(id);
    }

    @Override
    public Integer addMerchant(Merchant merchant) {
        return merchantRepository.addNewMerchant(merchant);
    }
}
