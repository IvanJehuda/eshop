package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentServiceImpl paymentService;
    private PaymentRepository paymentRepository;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        paymentService = new PaymentServiceImpl(paymentRepository);
        mockOrder = Mockito.mock(Order.class);

        // Ensure mockOrder returns a valid ID
        when(mockOrder.getId()).thenReturn("12345");
    }

    @Test
    void testAddPayment_VoucherCode_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment expectedPayment = new Payment("12345", "Voucher Code", PaymentStatus.SUCCESS, paymentData);

        when(paymentRepository.save(any(Payment.class))).thenReturn(expectedPayment);

        Payment result = paymentService.addPayment(mockOrder, "Voucher Code", paymentData);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        assertEquals("Voucher Code", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_VoucherCode_Rejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE");

        Payment expectedPayment = new Payment("12345", "Voucher Code", PaymentStatus.REJECTED, paymentData);

        when(paymentRepository.save(any(Payment.class))).thenReturn(expectedPayment);

        Payment result = paymentService.addPayment(mockOrder, "Voucher Code", paymentData);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatus_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("key", "value"); // Ensure it's not empty

        Payment payment = new Payment("12345", "Bank Transfer", PaymentStatus.PENDING, paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatus_Rejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("12345", "Cash on Delivery", PaymentStatus.PENDING, paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentById() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("12345", "Voucher Code", PaymentStatus.SUCCESS, paymentData);
        when(paymentRepository.findById("12345")).thenReturn(payment);

        Payment result = paymentService.getPayment("12345");

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        verify(paymentRepository, times(1)).findById("12345");
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        List<Payment> payments = Arrays.asList(
                new Payment("12345", "Voucher Code", PaymentStatus.SUCCESS, paymentData),
                new Payment("67890", "Bank Transfer", PaymentStatus.PENDING, paymentData)
        );

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }
}
