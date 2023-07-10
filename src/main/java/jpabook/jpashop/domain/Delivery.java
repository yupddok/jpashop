package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    //Enum 타입 사용시
    //@Enumerated 애노테이션 필수
    //EnumType.ORDINAL(컬럼이 1,2,3 숫자로 들어감 but Enum추가 되면 꼬임) -> 절대 사용금지
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
