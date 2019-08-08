package com.kafkaqa.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text")
    private String text;
    @Column(name = "isPaymentMessage")
    private boolean isPaymentMessage;

    public Message() {
    }

    public Message(String text, boolean isPaymentMessage) {
        this.text = text;
        this.isPaymentMessage = isPaymentMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPaymentMessage() {
        return isPaymentMessage;
    }

    public void setPaymentMessage(boolean paymentMessage) {
        isPaymentMessage = paymentMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return isPaymentMessage == message.isPaymentMessage &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text, isPaymentMessage);
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", isPaymentMessage=" + isPaymentMessage +
                '}';
    }
}
