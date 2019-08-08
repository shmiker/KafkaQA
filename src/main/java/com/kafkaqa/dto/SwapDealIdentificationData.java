package com.kafkaqa.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SwapDealIdentificationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transactionNumber")
    private String transactionNumber;

    @Column(name = "client")
    private String client;

    @Column(name = "updateApplication")
    private String updateApplication;

    public SwapDealIdentificationData() {
    }

    public SwapDealIdentificationData(String transactionNumber, String client, String updateApplication) {
        this.transactionNumber = transactionNumber;
        this.client = client;
        this.updateApplication = updateApplication;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setUpdateApplication(String updateApplication) {
        this.updateApplication = updateApplication;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public String getClient() {
        return client;
    }

    public String getUpdateApplication() {
        return updateApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SwapDealIdentificationData that = (SwapDealIdentificationData) o;

        if (client != null ? !client.equals(that.client) : that.client != null) {
            return false;
        }
        if (transactionNumber != null ? !transactionNumber.equals(that.transactionNumber) : that.transactionNumber != null) {
            return false;
        }
        if (updateApplication != null ? !updateApplication.equals(that.updateApplication) : that.updateApplication != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionNumber != null ? transactionNumber.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (updateApplication != null ? updateApplication.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SwapDealIdentificationData");
        sb.append("{transactionNumber='").append(transactionNumber).append('\'');
        sb.append(", client='").append(client).append('\'');
        sb.append(", updateApplication='").append(updateApplication).append('\'');
        sb.append('}');
        return sb.toString();
    }
}