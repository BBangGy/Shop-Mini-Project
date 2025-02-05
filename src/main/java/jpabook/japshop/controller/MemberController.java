package jpabook.japshop.controller;

import jakarta.validation.Valid;
import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        //memberForm이라는 키를 사용하여 new MemberForm()객체를 저장한다.
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
//        BindingResult는 오류를 담아서 코드를 실행하게 해준다
//        그럼 MemberForm에 입력한 NOTEMPTY의 메세지가 나온다.
        if(result.hasErrors()){
            return"members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
//      model이라는 객체를 통해서 화면에 전달이 된다.
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        //첫번째 인자에 key를 뜻하고 , 두번째 인자는 value로 넣어줄걸 적는다.
        return "/members/memberList";
    }

}
