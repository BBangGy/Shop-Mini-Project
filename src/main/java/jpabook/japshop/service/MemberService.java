package jpabook.japshop.service;

import jpabook.japshop.domain.Member;
import jpabook.japshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    //변경할 이유가 없기 때문에 final로 한다.
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    private Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

       //리턴은 pK를 넘겨줘야 나중에 다른 추가작업이 가능하다.
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원한명조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
