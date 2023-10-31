package sk.rudo.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.rudo.shop.database.service.api.ShoppingService;
import sk.rudo.shop.database.service.api.request.BuyProductRequest;
import sk.rudo.shop.database.service.api.response.BuyProductResponse;

@RestController
@RequestMapping("shop")
public class ShoppingController {
    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping("/buy")
    public ResponseEntity buy (@RequestBody BuyProductRequest request) {
        BuyProductResponse buyProductResponse = shoppingService.buyProduct(request);
        if (buyProductResponse.isSuccess()) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(buyProductResponse.getErrorMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }
}
