package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	 
	 /*질문목록 화면(+페이징구현)*/
	 public Page<Question> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.desc("createDate")); //작성 일시(createDate) 역순(Desc)으로 조회
		 Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		 Specification<Question> spec = search(kw);
		 return this.questionRepository.findAll(spec, pageable);
	 } 
	
	 /*상세페이지 화면*/
	 public Question getQuestion(Integer id) {
		 Optional<Question> question = this.questionRepository.findById(id);
		 
		 if (question.isPresent()) {
	        return question.get();
	     } else {
	         throw new DataNotFoundException("question not found");
	     }
		 
	 }
	 
	 /*질문등록하기 기능*/
	 public void create(String subject, String content, SiteUser user) {
		 Question q = new Question();
		 q.setSubject(subject);
		 q.setContent(content);
		 q.setCreateDate(LocalDateTime.now());
		 q.setAuthor(user);
		 this.questionRepository.save(q);
	 }
	 
	/*수정된 질문 처리*/
	 public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    } 
	 
	 /*질문삭제 기능*/
	 public void delete(Question question) {
        this.questionRepository.delete(question);
    }

	/*추천 기능*/
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}

	private Specification<Question> search(String kw){
		return new Specification<Question>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> ques, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복을 제거
				Join<Question, SiteUser> user1 = ques.join("author", JoinType.LEFT);
				Join<Question, Answer> ans = ques.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> user2 = ans.join("author", JoinType.LEFT);
				return cb.or(cb.like(ques.get("subject"), "%" + kw + "%"), //제목
						cb.like(ques.get("content"), "%" + kw + "%"), //제목
						cb.like(user1.get("username"), "%" + kw + "%"),    // 질문 작성자
						cb.like(ans.get("content"), "%" + kw + "%"),      // 답변 내용
						cb.like(user2.get("username"), "%" + kw + "%"));   // 답변 작성자

			}
		};
	}



}
