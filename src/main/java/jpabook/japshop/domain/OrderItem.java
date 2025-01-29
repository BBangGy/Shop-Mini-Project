package jpabook.japshop.domain;

import jakarta.persistence.*;
import jpabook.japshop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    //왜냐하면 하나의 오더가 여러개의 오더아이템을 가질수 있어서
    //반대로 오더아이템은 하나의 오더만 가질수 있다.
    private Order order;

    private int orderPrice;//주문 가격
    private int count;//주문 수량

    //생성메서드//
    public static OrderItem createOrderItem(Item item,int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    //비즈니스 로직//
    public void cancel() {
        //재고 수량을 복구해준다.
        getItem().addStock(count);
    }
    //조회로직//
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
