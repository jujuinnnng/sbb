package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;


import com.mysite.sbb.question.Question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.mysite.sbb.user.SiteUser;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //해당 속성만 별도로 번호가 차례대로 늘어나도록 할 때 사용
	private Integer id;
	
	@Column(columnDefinition = "TEXT") //글자수를 제한 할 수 없는 경우
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne //N:1 관계
	private Question question;
	
	@ManyToOne
	private SiteUser author;

	private LocalDateTime modifyDate;

	@ManyToMany
	Set<SiteUser> voter;
}
