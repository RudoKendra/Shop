package sk.rudo.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.rudo.shop.database.service.api.MerchantService;
import sk.rudo.shop.domain.Merchant;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping()
    public ResponseEntity getAll () {
        List<Merchant> merchantList = merchantService.getMerchants();
        return new ResponseEntity<>(merchantList, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity get (@PathVariable("id") int id) {
        Merchant merchant = merchantService.getMerchantById(id);
        if (merchant == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity add (@RequestBody Merchant merchant) {
        Integer id = merchantService.addMerchant(merchant);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
