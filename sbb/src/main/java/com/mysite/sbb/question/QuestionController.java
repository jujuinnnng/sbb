package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
	
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
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "question_form"; // 오류가 있는 경우 폼 페이지로 돌아감
	    }
	    this.questionService.create(questionForm.getSubject(), questionForm.getContent());
	    return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
	}
}
