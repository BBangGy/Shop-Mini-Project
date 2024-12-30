package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Setter
@Getter
public class Book extends Item{
    //상속이기 때문에 extends item을 해준다.
    private String author;
    private String isbn;

}
