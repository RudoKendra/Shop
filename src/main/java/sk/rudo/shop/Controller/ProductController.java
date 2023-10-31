package sk.rudo.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.rudo.shop.database.service.api.ProductService;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Product;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity getAll () {
        List<Product> productList = productService.getProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity get (@PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity add (@RequestBody Product product) {
        Integer id = productService.addProduct(product);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PatchMapping("{id}")
    public ResponseEntity update (@PathVariable("id") int id, @RequestBody UpdateProductRequest updateProductRequest) {
        if (productService.getProductById(id) == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Product with ID " + id + " not exist");
        }
        productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete (@PathVariable("id") int id) {
        if (productService.getProductById(id) == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Product with ID " + id + " not exist");
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
