package sk.rudo.shop.database.service.api;

import org.springframework.lang.Nullable;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();
    @Nullable
    Product getProductById (int id);
    @Nullable
    Integer addProduct (Product product);
    void deleteProduct (int id);
    void updateProduct (int id, UpdateProductRequest request);
    void updateAvailable (int id, int newAvailable);
}
