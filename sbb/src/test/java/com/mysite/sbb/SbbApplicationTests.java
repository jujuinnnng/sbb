package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Transactional
	@Test
	void testJpa() {
		/* @@1. 질문 데이터 저장하기*/
		/*Question q1 = new Question();
		q1.setSubject("test1 이란?");
		q1.setContent("test1에 대해서 알고싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); //테이블 첫 번째 행에 들어갈 데이터
		
		Question q2 = new Question();
		q2.setSubject("test2 이란?");
		q2.setContent("test2에 대해서 알고싶습니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2); //테이블 두 번째 행에 들어갈 데이터*/
		
		/*findAll 메서드*/
		/*List<Question> all = this.questionRepository.findAll(); // 데이터를 조회할 때 사용하는 메서드
		assertEquals(2, all.size()); // 기댓값(질문 2개), 실제 all 배열의 크기
		
		Question q1 = all.get(0); // 인덱스 0번의 값을 q1에 저장
		assertEquals("test1 이란?", q1.getSubject()); // 질문이 맞는지 */
		
		/*findById 메서드*/
		/*Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) { // null이 아니면 true
			Question q2 = oq.get();
			assertEquals("test1 이란?", q2.getSubject());
		}*/
		
		/*findBySubject 메서드*/
		/*Question q3 = this.questionRepository.findBySubject("test1 이란?"); //동일한 데이터가있으면 에러가 남 
		assertEquals(1, q3.getId()); assertEquals("test1에 대해서 알고싶습니다.",q3.getContent());*/
		
		/*findBySubjectAndContent 메서드*/
		/*Question q4 = this.questionRepository.findBySubjectAndContent("test1 이란?", "test1에 대해서 알고싶습니다.");
    	assertEquals(1, q4.getId());*/
	
    	/*findBySubjectLike 메서드*/
		/*List<Question> qList = this.questionRepository.findBySubjectLike("test1%");
    	Question q5 = qList.get(0);
    	assertEquals("test1 이란?", q5.getSubject());*/
    	
    	/*데이터 수정하기*/
		/*Optional<Question> oqc = this.questionRepository.findById(1); // id가 1인 데이터
    	assertTrue(oqc.isPresent()); // 값이 true인지 테스트
    	Question q6 = oqc.get();
    	q6.setSubject("수정된 제목"); //'test1 이란?' -> '수정된 제목'
    	q6.setContent("수정된 내용"); //'test1에 대해서 알고싶습니다.' -> '수정된 내용'
    	q6.setCreateDate(LocalDateTime.now());
    	this.questionRepository.save(q6);*/
		
		/*데이터 삭제하기*/
		/*assertEquals(2, this.questionRepository.count()); // 질문이 두개가 맞는 지 확인
    	Optional<Question> op = this.questionRepository.findById(2); // ID가 2인 질문 조회 후 op에 저장
    	Question q7 = op.get(); // op의 데이터를 Question 타입으로 저장
    	this.questionRepository.delete(q7); // 'test2 이란?', 'test2에 대해서 알고싶습니다.' -> 삭제
    	assertEquals(1, this.questionRepository.count()); // 질문이 한개가 되었는지(삭제되었는지 확인)*/
    
		
		
		
		
		
		/*@@2. 답변 데이터 생성 후 저장하기*/
		/*Optional<Question> oq = this.questionRepository.findById(1); // id가 1인 질문 조회 
    	assertTrue(oq.isPresent()); // 널값이 아니면 true
    	Question q8 = oq.get(); // Question 타입으로 저장
    	
    	Answer a = new Answer(); // Answer 타입의 인스턴스 생성
    	a.setContent("답변 추가해보기"); // 답변저장
    	a.setQuestion(q8); // 어떤 질문에 대한 답변인지 저장
    	a.setCreateDate(LocalDateTime.now()); // 시간 저장
    	this.answerRepository.save(a); // 저장	*/
		
		/*findById 메서드 답변 데이터 조회하기*/
		/*Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(1, a.getQuestion().getId()); // -> 실행결과 left join이 사용 됨 */
		
		/*findById 질문에서 답변 데이터 조회하기*/
		Optional<Question> oq = this.questionRepository.findById(1); // ID가 1인 질문 저장
		assertTrue(oq.isPresent()); // 널값이 아니면 true
		Question q = oq.get(); // Question타입으로 인스턴스 생성
		
		List<Answer> a = q.getAnswerList(); // 답변리스트를 리스트로 저장
		assertEquals(1, a.size()); // 답변이 1개인지 확인
		assertEquals("답변 추가해보기", a.get(0).getContent()); // 인덱스가 0인 답변의 내용 조회
		// -> com.mysite.sbb.Question.answerList, could not initialize proxy - no Session 에러가 나는 이유?
		// QuestionRepository findById를 호출하여 Question 객체를 조회하고 나면 DB세션이 끊어지기 때문
		// 그 이후에 실행되는 q.getAnswerList() 메서드는 세션이 종료되어 오류가 발생.  답변 데이터 리스트는 q 객체를 조회할때 가져오지 않고 q.getAnswerList() 메서드를 호출하는 시점에 가져오기 때문이다.
		// 테스트코드에서만 발생(실제 서버에서는 이상없음) -> @Transactional 어노테이션 추가
	} 
	

}
