package com.banking.virtualwallet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("WalletTransactions")
public class WalletTransaction {

    @Id //to set as primary key
    private String transactionId;
    @Indexed
    private String walletId;
    private String type;
    private Date timestamp;
    private float amount;
    private float postBalance;
    private String description;

    public WalletTransaction() {
    }

    public WalletTransaction(String type, Date timestamp, float amount, float postBalance, String description, String walletId) {
        this.walletId = walletId;
        this.type = type;
        this.timestamp = timestamp;
        this.amount = amount;
        this.postBalance = postBalance;
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getAmount() {
        return amount;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(float postBalance) {
        this.postBalance = postBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
