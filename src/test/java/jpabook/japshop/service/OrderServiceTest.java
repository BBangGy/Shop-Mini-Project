package jpabook.japshop.service;

import jakarta.persistence.EntityManager;
import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderStatus;
import jpabook.japshop.domain.item.Book;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.exception.NotEnoughStockException;
import jpabook.japshop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest

class OrderServiceTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("서울 jpa", 100000, 10);

        int orderCount =2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals( OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야한다.");
        assertEquals(100000*orderCount,getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals(8,book.getStockQuantity(),"주문 수량 만큼 제고가 줄어야한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("서울 jpa", 100000, 10);  // 재고 10개
        int orderCount = 11;  // 주문 수량 11개, 재고 초과

        // when & then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member= createMember();
        Book item = createBook("시골잡종", 10000, 10);
        int orderCount=2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 취소시 상태는 cancel이다.");
        assertEquals(10,item.getStockQuantity(),"주문 취소시 재고 수량이 같아야한다.");

    }
    @Test
    public void 재고수량초과() throws Exception{
        //given
        
        //when
        
        //then
    
    }
    private Book createBook(String name, int orderPrice, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}