package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank ABC");
        paymentData.put("referenceCode", "123456789");
        payment = new Payment("1", "Bank Transfer", "PENDING", paymentData);
    }

    @Test
    void testCreatePayment() {
        assertEquals("1", payment.getId());
        assertEquals("Bank Transfer", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Bank ABC", payment.getPaymentData().get("bankName"));
        assertEquals("123456789", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testSetStatusSuccess() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID"));
    }

    @Test
    void testCreatePaymentWithEmptyBankName() {
        paymentData.put("bankName", "");
        Payment invalidPayment = new Payment("2", "Bank Transfer", "PENDING", paymentData);
        assertEquals("REJECTED", invalidPayment.getStatus());
    }

    @Test
    void testCreatePaymentWithNullReferenceCode() {
        paymentData.put("referenceCode", null);
        Payment invalidPayment = new Payment("3", "Bank Transfer", "PENDING", paymentData);
        assertEquals("REJECTED", invalidPayment.getStatus());
    }

}
