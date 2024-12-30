package jpabook.japshop.domain.item;

import jakarta.persistence.*;
import jpabook.japshop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
//구분 컬럼 이름을 dytpe으로 지정
@Getter @Setter
public abstract class Item {
    @GeneratedValue
    @Id
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
