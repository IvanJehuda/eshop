package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Payment status cannot be null or empty");
        }
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be null or empty");
        }

        this.id = id;
        this.method = method;
        this.status = status;
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
            this.status = "REJECTED";
        }
    }

    private void validateVoucherCode(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.isEmpty()) {
            this.status = "REJECTED";
            return;
        }

        if (voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") &&
                voucherCode.replaceAll("\\D", "").length() == 8) {
            this.status = "SUCCESS";
        } else {
            this.status = "REJECTED";
        }
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals("SUCCESS") && !status.equals("REJECTED") && !status.equals("PENDING")) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }
}