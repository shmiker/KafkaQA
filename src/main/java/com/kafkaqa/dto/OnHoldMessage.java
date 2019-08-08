package com.kafkaqa.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OnHoldMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "messageType")
    private String messageType;
    @Column(name = "action")
    private String action;
    public  String ON_HOLD_MESSAGE_KEY = "OnHoldMessageKey";

    public OnHoldMessage() {
    }

    public OnHoldMessage(Builder builder){
        this.messageType = builder.messageType;
        this.action = builder.action;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnHoldMessage that = (OnHoldMessage) o;

        if (messageType != null ? !messageType.equals(that.messageType) : that.messageType != null) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OnHoldMessage{" +
                "messageType='" + messageType + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

    public static final class Builder {

        private String messageType;
        private String action;

        public Builder(){

        }

        public Builder messageType(String messageType){
            this.messageType = messageType;
            return this;
        }

        public Builder action(String action){
            this.action = action;
            return this;
        }

        public OnHoldMessage build(){
            return new OnHoldMessage(this);
        }
    }
}
