package jpabook.japshop.domain.item;

import jakarta.persistence.*;
import jpabook.japshop.domain.Category;
import jpabook.japshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
//구분 컬럼 이름을 dytpe으로 지정
@Getter
@Setter
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

    //==비즈니스 로직==//
    //핵심 비즈니스 로직을 여기에 넣은 이유는 stackQuantity가 item에 있기에 관리가 하기 쉬워서 여기다 설정
    //가장 객체 지향적인 방법이다.
    //제고 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    //제고 감소
    public void removeStock(int quantity){
        int reStock = this.stockQuantity - quantity;
        if(reStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity =reStock;
    }

}
