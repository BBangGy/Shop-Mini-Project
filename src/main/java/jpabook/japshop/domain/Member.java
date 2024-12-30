package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    //여기서 오더는 오더 테이블에있는 member 필드에 인해서 맵핑 된거다.
    //하나의 회원이 여러개의 상품을 주문하기 때문에 onetomany
    //order에 의해서 mapping되는거다.
    private List<Order> orders = new ArrayList<>();

}
