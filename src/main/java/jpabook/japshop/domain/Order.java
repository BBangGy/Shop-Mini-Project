package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders") //이름이 데이터베이스에 orders로 되어있기 때문에 맞춰줘야한다.
@Setter
@Getter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    //여러벰버는 하나의 오더를 가진다.
    @JoinColumn(name = "member_id")
    //foreign key의 이름이 member_id / 주문한 회원에 대한 아이디를 맵핑
    //양방향 연관관계이기 때문에 연관관계의 주인을 잡아줘야한다.
    private Member member;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    //order를 저장하면 orderItems에도 같이저장이 된다.지울때도 같다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;//주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문상태[order, cancel]

    //연관관계 메서드(컨트롤 하는 쪽에 설정)
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDeliver(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }
}