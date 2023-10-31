package sk.rudo.shop.database.service.api;

import sk.rudo.shop.database.service.api.request.BuyProductRequest;
import sk.rudo.shop.database.service.api.response.BuyProductResponse;

public interface ShoppingService {
    BuyProductResponse buyProduct (BuyProductRequest request);
}
