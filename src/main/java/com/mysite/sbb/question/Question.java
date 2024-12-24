package com.mysite.sbb.question;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.mysite.sbb.user.SiteUser;


@Getter
@Setter
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //해당 속성만 별도로 번호가 차례대로 늘어나도록 할 때 사용
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT") //글자수를 제한 할 수 없는 경우
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //질문을 삭제하면 그에 따른 답변도 삭제처리
	private List<Answer> answerList;
	
	@ManyToOne
    private SiteUser author;
	
	private LocalDateTime modifyDate;

	@ManyToMany
	Set<SiteUser> voter; //voter 속성값이 서로 중복되지 않도록 하기 위해서 set

}
