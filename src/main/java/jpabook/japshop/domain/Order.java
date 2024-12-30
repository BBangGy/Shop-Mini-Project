package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //이름이 데이터베이스에 orders로 되어있기 때문에 맞춰줘야한다.
@Setter
@Getter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    //여러벰버는 하나의 오더를 가진다.
    @JoinColumn(name = "member_id")
    //foreign key의 이름이 member_id / 주문한 회원에 대한 아이디를 맵핑
    //양방향 연관관계이기 때문에 연관관계의 주인을 잡아줘야한다.
    private Member member;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;//주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문상태[order, cancel]

}
