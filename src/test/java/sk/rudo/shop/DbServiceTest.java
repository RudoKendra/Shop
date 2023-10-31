package sk.rudo.shop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import sk.rudo.shop.database.service.api.CustomerService;
import sk.rudo.shop.database.service.api.MerchantService;
import sk.rudo.shop.database.service.api.ProductService;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Customer;
import sk.rudo.shop.domain.Merchant;
import sk.rudo.shop.domain.Product;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class DbServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;
    private Merchant merchant;

    @Before
    public void newMerchant () {
        if (merchant == null) {
            merchant = new Merchant("xxx", "xxx", "xxx");
            Integer id = merchantService.addMerchant(merchant);
            assert id != null;
            merchant.setId(id);
        }
    }

    @Test
    public void customer () {
        Customer customer = new Customer("xxx", "xxx", "xxx", "xxx", 50, "xxx");
        Integer id = customerService.addNewCustomer(customer);
        assert id != null;
        customer.setId(id);

        Customer dbCustomer = customerService.getCustomerById(id);
        Assert.assertEquals(customer,dbCustomer);

        List<Customer> customers = customerService.getAllCustomers();
        Assert.assertEquals(1, customers.size());
        Assert.assertEquals(customer, customers.get(0));
    }

    @Test
    public void merchant () {
        Merchant dbMerchant = merchantService.getMerchantById(merchant.getId());
        Assert.assertEquals(merchant, dbMerchant);

        List<Merchant> merchants = merchantService.getMerchants();
        Assert.assertEquals(1, merchants.size());
        Assert.assertEquals(merchant, merchants.get(0));
    }

    @Test
    public void product () {
        Product product = new Product(merchant.getId(), "xxx", "xxx" , 1, 1);
        Integer id = productService.addProduct(product);
        assert id != null;
        product.setId(id);

        Product dbProduct = productService.getProductById(id);
        Assert.assertEquals(product, dbProduct);

        List<Product> products = productService.getProducts();
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(product, products.get(0));

        product.setName("yyy");
        UpdateProductRequest productRequest = new UpdateProductRequest(product.getName(),product.getDescription(),product.getPrice(),product.getAvailable());
        productService.updateProduct(id, productRequest);
        Product dbProductUp = productService.getProductById(id);
        Assert.assertEquals(product, dbProductUp);

        productService.deleteProduct(id);
        Assert.assertEquals(0, productService.getProducts().size());
    }
}
