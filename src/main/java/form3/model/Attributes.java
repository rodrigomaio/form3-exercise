package form3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;

@Embeddable
@JsonInclude(ALWAYS)
public class Attributes {

    private Double amount;

    private String currency;

    @JsonProperty("end_to_end_reference")
    private String endToEndReference;

    @JsonProperty("numeric_reference")
    private Long numericReference;

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("payment_purpose")
    private String paymentPurpose;

    @JsonProperty("payment_scheme")
    private String paymentScheme;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("processing_date")
    private Date processingDate;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("scheme_payment_sub_type")
    private String schemePaymentSubType;

    @JsonProperty("scheme_payment_type")
    private String schemePaymentType;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getNumericReference() {
        return numericReference;
    }

    public void setNumericReference(Long numericReference) {
        this.numericReference = numericReference;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getPaymentScheme() {
        return paymentScheme;
    }

    public void setPaymentScheme(String paymentScheme) {
        this.paymentScheme = paymentScheme;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = new Date(processingDate.getTime());
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSchemePaymentSubType() {
        return schemePaymentSubType;
    }

    public void setSchemePaymentSubType(String schemePaymentSubType) {
        this.schemePaymentSubType = schemePaymentSubType;
    }

    public String getSchemePaymentType() {
        return schemePaymentType;
    }

    public void setSchemePaymentType(String schemePaymentType) {
        this.schemePaymentType = schemePaymentType;
    }

    public String getEndToEndReference() {
        return endToEndReference;
    }

    public void setEndToEndReference(String endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

}
