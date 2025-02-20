package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("123");
        sampleProduct.setProductName("Sample Product");
        sampleProduct.setProductQuantity(50);
    }

    @Test
    void testCreate() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);

        Product createdProduct = productService.create(sampleProduct);

        assertNotNull(createdProduct);
        assertEquals(sampleProduct.getProductId(), createdProduct.getProductId());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleProduct.getProductId(), result.get(0).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById("123")).thenReturn(sampleProduct);

        Product foundProduct = productService.findById("123");

        assertNotNull(foundProduct);
        assertEquals(sampleProduct.getProductId(), foundProduct.getProductId());
        verify(productRepository, times(1)).findById("123");
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById("999")).thenReturn(null);

        Product foundProduct = productService.findById("999");

        assertNull(foundProduct);
        verify(productRepository, times(1)).findById("999");
    }

    @Test
    void testUpdate() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);

        Product updatedProduct = productService.update(sampleProduct);

        assertNotNull(updatedProduct);
        assertEquals(sampleProduct.getProductId(), updatedProduct.getProductId());
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testDelete() {
        when(productRepository.delete("123")).thenReturn(true);

        boolean isDeleted = productService.delete("123");

        assertTrue(isDeleted);
        verify(productRepository, times(1)).delete("123");
    }

    @Test
    void testDeleteNotExisting() {
        when(productRepository.delete("999")).thenReturn(false);

        boolean isDeleted = productService.delete("999");

        assertFalse(isDeleted);
        verify(productRepository, times(1)).delete("999");
    }
}
