package jpabook.japshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable//내장 타입이란걸 명시
@Getter
public class Address {
    private String city;
    private String street;
    private  String zipcode;

    protected Address(){
    }
    public Address(String zipcode, String street, String city) {
        this.zipcode = zipcode;
        this.street = street;
        this.city = city;
    }
}
