package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.*;

public class PaymentRepository {
    private final Map<String, Payment> paymentStorage = new HashMap<>();

    public Payment save(Payment payment) {
        paymentStorage.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        return paymentStorage.get(paymentId);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentStorage.values());
    }

    public Payment addPayment(String id, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(id, method, PaymentStatus.PENDING, paymentData);
        paymentStorage.put(id, payment);
        return payment;
    }

    public Payment setStatus(String paymentId, PaymentStatus status) {
        Payment payment = paymentStorage.get(paymentId);
        if (payment != null) {
            payment.setStatus(status);
        }
        return payment;
    }
}
