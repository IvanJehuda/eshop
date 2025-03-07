package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private PaymentStatus status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, PaymentStatus status, Map<String, String> paymentData) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Payment status cannot be null");
        }
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be null or empty");
        }

        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;

        if (method.equals("Bank Transfer")) {
            validateBankTransfer();
        } else if (method.equals("Voucher Code")) {
            validateVoucherCode();
        }
    }

    private void validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            this.status = PaymentStatus.REJECTED;
        }
    }

    private void validateVoucherCode() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.isEmpty()) {
            this.status = PaymentStatus.REJECTED;
            return;
        }

        if (voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") &&
                voucherCode.replaceAll("\\D", "").length() == 8) {
            this.status = PaymentStatus.SUCCESS;
        } else {
            this.status = PaymentStatus.REJECTED;
        }
    }

    public void setStatus(PaymentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}
