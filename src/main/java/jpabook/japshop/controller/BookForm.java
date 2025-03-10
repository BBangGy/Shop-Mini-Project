package jpabook.japshop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String isbn;
    private String author;

}
