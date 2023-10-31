package sk.rudo.shop.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.rudo.shop.database.repository.ProductRepository;
import sk.rudo.shop.database.service.api.ProductService;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Product;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Integer addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

    @Override
    public void updateProduct(int id, UpdateProductRequest request) {
        productRepository.updateProduct(id, request);
    }

    @Override
    public void updateAvailable(int id, int newAvailable) {
        productRepository.updateAvailable(id, newAvailable);
    }
}
