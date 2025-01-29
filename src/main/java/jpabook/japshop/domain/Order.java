package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders") //이름이 데이터베이스에 orders로 되어있기 때문에 맞춰줘야한다.
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//직접 생성하는것을 방지하기 위해서 적는다.위의 코드
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
    //생성 메서드//
    public static Order createOrder(Member member, Delivery delivery,OrderItem... orderItems){
        //OrderItem... orderItems는 여러 개의 주문 항목을 가변인자로 받아 처리합니다.
        //OrderItem item1, item2가 있으면
        //createOrder(member,delivery,item1,item2)->이렇게가 가능하다는 뜻이다.
        Order order = new Order();
        order.setMember(member);
        order.setDeliver(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직//
    /**
    * 주문취소
    * */
    public void cancel(){
     if(delivery.getStatus() == DeliveryStatus.COMP){
         throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능 합니다.");
     }
     this.setStatus(OrderStatus.CANCEL);
     for(OrderItem orderItem:orderItems){
         orderItem.cancel();
         //orderItem에도 cancel을 해줘야해서
     }
    }

    //조회 로직//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem:orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}