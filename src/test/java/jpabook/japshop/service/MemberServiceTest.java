package jpabook.japshop.service;

import jakarta.annotation.security.RunAs;
import jpabook.japshop.domain.Member;
import jpabook.japshop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Test

    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long saveId = memberService.join(member);
        //then
        assertEquals(member,memberRepository.findOne(saveId));

    }
    @Test()
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        //then
        //두번째 회원 등록시 중복 예외 발생
        assertThrows(IllegalStateException.class,()->{
            memberService.join(member2);
        });

    }

}