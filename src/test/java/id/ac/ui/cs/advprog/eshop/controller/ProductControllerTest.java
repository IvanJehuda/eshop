package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"));
    }

    @Test
    void testCreateProductPostValidProduct() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(new Product());

        mockMvc.perform(post("/product/create")
                        .param("productId", "1")
                        .param("productName", "Laptop")
                        .param("productQuantity", "10")) // Ensure all required params are set
                .andExpect(status().is3xxRedirection())  // Expecting a redirect (302)
                .andExpect(redirectedUrl("/product/list")); // Expecting redirection URL

        verify(productService, times(1)).create(any(Product.class));
    }


    @Test
    void testCreateProductPostInvalidProduct() {
        Product product = new Product();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Model model = mock(Model.class);

        String result = productController.createProductPost(product, bindingResult, model);

        assertEquals("createProduct", result);
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductPageProductExists() throws Exception {
        Product product = new Product();
        when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testEditProductPageProductNotExists() throws Exception {
        when(productService.findById("1")).thenReturn(null);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testEditProductPostValidProduct() throws Exception {
        when(productService.update(any(Product.class))).thenReturn(new Product());

        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Tablet")
                        .param("productQuantity", "15")) // Ensure all required params are set
                .andExpect(status().is3xxRedirection())  // Expecting a redirect (302)
                .andExpect(redirectedUrl("/product/list")); // Expecting redirection URL

        verify(productService, times(1)).update(any(Product.class));
    }



    @Test
    void testEditProductPostInvalidProduct() {
        Product product = new Product();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Model model = mock(Model.class);

        String result = productController.editProductPost(product, bindingResult, model);

        assertEquals("editProduct", result);
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("1");
    }
}
