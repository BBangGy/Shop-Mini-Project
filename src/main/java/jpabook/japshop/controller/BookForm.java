package jpabook.japshop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    private Long id;
    @NotEmpty(message = "이름을 입력하지 않았습니다.")
    private String name;
    private int price;
    private int stockQuantity;

    private String isbn;
    private String author;

}
