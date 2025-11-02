package com.order.coffee.infrastructure.gateway.outbound.persistence.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class CustomerEmbedded {
    private String name;
    private String phoneNumber;

    public CustomerEmbedded() {
    }

    public CustomerEmbedded(String name, String phoneNumber){
        this.name =  name;
        this.phoneNumber =  phoneNumber;
    }

    public String getName(){
        return this.name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }
}
