package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
	
    /*질문목록 화면*/
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page)  {
		Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
		return "question_list";	
	}
	
	/*상세페이지 화면*/
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
		return "question_detail";
	}
	
	/*질문등록하기 화면*/
	@GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
	
	/*질문등록하기 기능*/
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
	    if (bindingResult.hasErrors()) {
	        return "question_form"; // 오류가 있는 경우 폼 페이지로 돌아감
	    }
	    SiteUser siteUser = this.userService.getUser(principal.getName());
	    this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
	    return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
	}
	
	
	/*질문수정하기 화면*/
	@PreAuthorize("isAuthenticated()") //사용자가 로그인을 했고 인증이 된 상태에서만 해당 메소드에 접근할 수 있도록함
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
	
	
	/*질문수정하기 기능*/
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
	
	
	/*질문삭제 기능*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
	  Question question = this.questionService.getQuestion(id);
	  if (!question.getAuthor().getUsername().equals(principal.getName())) {
	      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
	  }
	  this.questionService.delete(question);
	  return "redirect:/";
   }
	
	
}
