package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200, message="제목은 최대 200자까지 입력 가능합니다.")
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
}