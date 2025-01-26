package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {
    @GeneratedValue
    @Id
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    //EnumType.STRING은 데이터베이스에 값이 명시적으로 저장됩니다.
    //이로 인해 코드 변경 없이도 데이터베이스에 저장된 값만으로 의미를 명확히 알 수 있습니다.
    private  DeliveryStatus status;//READY, COMP
}
