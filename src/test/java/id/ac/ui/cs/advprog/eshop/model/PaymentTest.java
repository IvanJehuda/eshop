package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        payment = new Payment("1", "Bank Transfer", PaymentStatus.PENDING, paymentData);
    }

    @Test
    void testCreatePayment() {
        assertEquals("1", payment.getId());
        assertEquals("Bank Transfer", payment.getMethod());
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertEquals("Bank ABC", payment.getPaymentData().get("bankName"));
        assertEquals("123456789", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testSetStatusSuccess() {
        payment.setStatus(PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus((PaymentStatus) null));
    }

    @Test
    void testCreatePaymentWithEmptyBankName() {
        paymentData.put("bankName", "");
        Payment paymentInvalid = new Payment("2", "Bank Transfer", PaymentStatus.PENDING, paymentData);
        assertEquals(PaymentStatus.REJECTED, paymentInvalid.getStatus());
    }

    @Test
    void testCreatePaymentWithNullReferenceCode() {
        paymentData.put("referenceCode", null);
        Payment paymentInvalid = new Payment("3", "Bank Transfer", PaymentStatus.PENDING, paymentData);
        assertEquals(PaymentStatus.REJECTED, paymentInvalid.getStatus());
    }

    @Test
    void testVoucherCodeSuccess() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
        Payment paymentVoucher = new Payment("4", "Voucher Code", PaymentStatus.PENDING, voucherData);
        assertEquals(PaymentStatus.SUCCESS, paymentVoucher.getStatus());
    }

    @Test
    void testVoucherCodeRejected() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "INVALIDCODE12345");
        Payment paymentVoucher = new Payment("5", "Voucher Code", PaymentStatus.PENDING, voucherData);
        assertEquals(PaymentStatus.REJECTED, paymentVoucher.getStatus());
    }
}
