package com.example.blog.dto;

import com.example.blog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;
@AllArgsConstructor
@ToString
public class ArticleDto {
    //필드선언
    private Long articleId; //수정 폼 전달을 위해 필드 추가
    private String title;
    private String date;
    private String contents;

    /* lombok -> @AllArgsConstructor
    public articleDto(String title, String date, String contents) {
        this.title = title;
        this.date = date;
        this.contents = contents;
    }
    */


    /*lombok -> @ToString
    @Override
    public String toString() {
        return "articleDto{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
    */
    public Article toEntity() {
        return new Article(articleId,title,date,contents);
    }
}
