package com.maheshgaire.qaautomation.model.enhanced;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Payment information model")
public class PaymentInfo {
    
    @Schema(description = "Payment method (credit_card, debit_card, paypal, etc.)")
    private String paymentMethod;
    
    @Schema(description = "Masked card number (last 4 digits)")
    private String maskedCardNumber;
    
    @Schema(description = "Card type (Visa, MasterCard, etc.)")
    private String cardType;
    
    @Schema(description = "Payment status")
    private String paymentStatus;
    
    @Schema(description = "Transaction ID")
    private String transactionId;
    
    @Schema(description = "Payment processor")
    private String processor;

    // Getters and Setters
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}