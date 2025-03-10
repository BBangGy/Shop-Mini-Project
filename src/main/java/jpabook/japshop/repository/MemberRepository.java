package jpabook.japshop.repository;

import jakarta.persistence.EntityManager;
import jpabook.japshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//밑의 어노테이션을 넣으면 final로 설정한 속성의 생성자를 자동으로 만들어진다.
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);
        //첫번째 타입, 두번째 PK를 넣는다.
    }
    public List<Member> findAll(){
        //첫번째가 jpql을 쓰고 두번째가 반환타입
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name).getResultList();
        //parameter를 통해서 바인딩한다.
        //특정이름을 통해서 찾는걸 만들었다.
    }
}
