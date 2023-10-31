package sk.rudo.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Customer;
import sk.rudo.shop.domain.Merchant;
import sk.rudo.shop.domain.Product;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Merchant merchant;
    @Before
    public void createMerchant () throws Exception {
        if (merchant==null) {
            merchant = new Merchant("xxx", "xxx", "xxx");

            String id = mockMvc.perform(post("/merchant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(merchant)))
                            .andExpect(status().isCreated())
                            .andReturn().getResponse().getContentAsString();

            merchant.setId(objectMapper.readValue(id, Integer.class));
        }
    }

    @Test
    public void customer () throws Exception{
        Customer customer = new Customer("xxx", "xxx", "xxx", "xxx", 50, "xxx");

        String id = mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        customer.setId(objectMapper.readValue(id, Integer.class));

        String jsonCustomer = mockMvc.perform(get("/customer/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Customer customerReturned = objectMapper.readValue(jsonCustomer, Customer.class);
        Assert.assertEquals(customer,customerReturned);

        String jsonList = mockMvc.perform(get("/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Customer> customers = objectMapper.readValue(jsonList, new TypeReference<List<Customer>>(){});
        assert customers.size() == 1;
        Assert.assertEquals(customer, customers.get(0));
    }
    @Test
    public void merchant () throws Exception{
        String jsonMerchant = mockMvc.perform(get("/merchant/" + merchant.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Merchant merchantReturned = objectMapper.readValue(jsonMerchant, Merchant.class);
        Assert.assertEquals(merchant, merchantReturned);

        String jsonList = mockMvc.perform(get("/merchant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Merchant> merchants = objectMapper.readValue(jsonList, new TypeReference<List<Merchant>>() {});

        assert merchants.size() == 1;
        Assert.assertEquals(merchant, merchants.get(0));
    }
    @Test
    public void product () throws Exception {
        Product product = new Product(merchant.getId(), "xxx", "xxx" , 1, 1);

        String id = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        product.setId(objectMapper.readValue(id, Integer.class));

        String jsonProduct = mockMvc.perform(get("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product productReturned = objectMapper.readValue(jsonProduct, Product.class);
        Assert.assertEquals(product, productReturned);

        String jsonList = mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> products = objectMapper.readValue(jsonList, new TypeReference<List<Product>>() {});

        assert products.size() == 1;
        Assert.assertEquals(product, products.get(0));

        double updatePrice = product.getPrice() +1;
        int updateAvailable = product.getAvailable() +1;

        UpdateProductRequest updateProductRequest = new UpdateProductRequest(product.getName(), product.getDescription(), updatePrice, updateAvailable);

        mockMvc.perform(patch("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequest)))
                .andExpect(status().isOk());

        String jsonUpdatedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        Product updatedProductReturned = objectMapper.readValue(jsonUpdatedProduct, Product.class);
        assert updatePrice == updatedProductReturned.getPrice();
        assert updateAvailable == updatedProductReturned.getAvailable();

        mockMvc.perform(delete("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        String jsonListDeleted = mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> deletedProducts = objectMapper.readValue(jsonListDeleted, new TypeReference<List<Product>>() {});

        assert deletedProducts.size() == 0;
    }
}
