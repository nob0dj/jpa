package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.exception.MemberNotFoundException;
import com.kh.spring.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
@SessionAttributes({"loginMember", "next"})
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;


	@GetMapping("/memberLogin.do")
	public String memberLogin(
			@RequestHeader(name="Referer", required=false) String referer,
			@SessionAttribute(required=false) String next, 
			Model model
	) {
		log.info("referer = {}", referer);
		
		if(next == null)
			model.addAttribute("next", referer);
		
		return "member/memberLogin";
	}
	
	@PostMapping("/memberLogin.do")
	public String memberLogin(
			@RequestParam String id, 
			@RequestParam String password,
			Model model,
			@SessionAttribute(required = false) String next,
			RedirectAttributes redirectAttr) {
		// redirect location
		String location = "/";
		
		// 인증
		Member member = memberService.findById(id).orElse(null);
		
		if(member != null && 
				bcryptPasswordEncoder.matches(password, member.getPassword())) {
			//로그인 성공
			model.addAttribute("loginMember", member);
			
			//로그인성공한 경우만 로그인권한 요구했던 페이지 이동
			if(next != null) {
				location = next;
//				session.removeAttribute("next"); // session에서 직접 삭제
			}
		}
		else {
			//로그인 실패
			redirectAttr.addFlashAttribute("msg", "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
		} 
		
		return "redirect:" + location;
	}
	
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus sessionStatus, ModelMap model) {
		
		model.clear(); // 관리되는 model속성 완전 제거
		
		// 현재 세션객체의 사용완료 설정 - 세션속성등 내부 초기화, 세션객체는 재사용
		if(!sessionStatus.isComplete())
			sessionStatus.setComplete();
		return "redirect:/";
	}
	
	@GetMapping("/memberEnroll.do")
	public String memberEnroll(){
		return "member/memberEnroll";
	}

	@PostMapping("/memberEnroll.do")
	public String memberEnroll(@RequestBody Member member, RedirectAttributes redirectAttr) {
		log.info("member = {}", member);

		try {
			// 비밀번호 암호화
			member.setPassword(bcryptPasswordEncoder.encode(member.getPassword()));
			
			member = memberService.save(member);
			log.info("member = {}", member);
			
			redirectAttr.addFlashAttribute("msg", "회원 등록 성공!");
			
			return "redirect:/";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * 
	 * Spring Data JPA의 Domain Class Converter에 의해서 id를 가지고 Member Entity를 조회해서 DI한다.
	 * 다만, 여긴 영속성 컨텍스트밖이므로 변경감지가 작동하지 않는다.
	 * 
	 * @param id
	 * @param member
	 * @return
	 */
	@PostMapping("/memberQuit.do")
	public String memberQuit(
			@RequestParam String id, 
			@RequestParam("id") Member member, 
			RedirectAttributes redirectAttr) {
		log.debug("id = {}", id);
		log.debug("member = {}", member);
		
		try {
			// 아래 처리결과는 동일하다.
			memberService.deleteById(id);
//			memberService.delete(member);
			
			redirectAttr.addFlashAttribute("msg", "회원 삭제 성공!");
			
			return "redirect:/";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		
		try {
			Optional<Member> member = memberService.findById(id);
			log.debug("member = {}", member);
			return ResponseEntity.ok(member.orElseThrow(MemberNotFoundException::new));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// boolean allowEmpty - true 빈문자열 ""인 경우 null변환함.
		PropertyEditor editor = new CustomDateEditor(sdf, true);
		// j.u.Date변환시 해당 editor객체 사용
		binder.registerCustomEditor(Date.class, editor);
	}
}
