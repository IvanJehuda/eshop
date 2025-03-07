package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = "REJECTED";

        if ("Voucher Code".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucherCode(voucherCode)) {
                status = "SUCCESS";
            }
        } else if ("Bank Transfer".equals(method)) {
            if (paymentData.containsKey("bankName") && paymentData.containsKey("referenceCode") &&
                    !paymentData.get("bankName").isEmpty() && !paymentData.get("referenceCode").isEmpty()) {
                status = "PENDING";
            }
        } else if ("Cash on Delivery".equals(method)) {
            if (paymentData.containsKey("address") && paymentData.containsKey("deliveryFee") &&
                    !paymentData.get("address").isEmpty() && !paymentData.get("deliveryFee").isEmpty()) {
                status = "PENDING";
            }
        }

        Payment payment = new Payment(order.getId(), method, status, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private boolean isValidVoucherCode(String voucherCode) {
        return voucherCode != null && voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") && voucherCode.replaceAll("[^0-9]", "").length() == 8;
    }
}
