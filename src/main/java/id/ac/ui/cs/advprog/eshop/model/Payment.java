package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private PaymentStatus status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Object status, Map<String, String> paymentData) {
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

        // Allow both String and Enum for compatibility
        if (status instanceof String) {
            try {
                this.status = PaymentStatus.valueOf((String) status);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid payment status string: " + status);
            }
        } else if (status instanceof PaymentStatus) {
            this.status = (PaymentStatus) status;
        } else {
            throw new IllegalArgumentException("Invalid payment status type");
        }

        this.paymentData = paymentData;

        if (method.equals("Bank Transfer")) {
            validateBankTransfer(this.paymentData);
        } else if (method.equals("Voucher Code")) {
            validateVoucherCode(this.paymentData);
        }
    }


    private void validateBankTransfer(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            this.status = PaymentStatus.REJECTED;
        }
    }

    private void validateVoucherCode(Map<String, String> paymentData) {
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
    public void setStatus(String status) { // Accept String input
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = PaymentStatus.valueOf(status); // Convert String to Enum
    }

}
