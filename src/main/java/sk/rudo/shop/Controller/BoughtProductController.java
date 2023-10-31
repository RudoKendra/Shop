package sk.rudo.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.rudo.shop.database.service.api.BoughtProductService;
import sk.rudo.shop.domain.BoughtProduct;

import java.util.List;

@RestController
@RequestMapping("bought-product")
public class BoughtProductController {
    private final BoughtProductService boughtProductService;

    public BoughtProductController(BoughtProductService boughtProductService) {
        this.boughtProductService = boughtProductService;
    }
    @GetMapping("{customerId}")
    public ResponseEntity getAllByCustomerId (@PathVariable ("customerId") int customerId) {
        List<BoughtProduct> boughtProducts = boughtProductService.getAllByCustomerId(customerId);
        return new ResponseEntity<>(boughtProducts, HttpStatus.OK);
    }
}
