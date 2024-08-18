package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity //DB가 해당 객체를 인식 가능하게 함(해당 클래스로 테이블을 만든다)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 1,2,3..자동 생성 전략
    //데이터베이스가 id를 알아서 넣을 수 있도록 바꾸기!
    private Long articleId; //article id (서버용)
    @Column //DB에서 관리하는 테이블이라는 단위에 연결되게 함!
    private String title;
    @Column
    private String date;
    @Column
    private String contents;

    public void patch(Article article) {
        if (article.title!=null)
            this.title=article.title;
        if (article.date!=null)
            this.date=article.date;
        if(article.contents!=null)
            this.contents= article.contents;
    }

    /* lombok -> @Getter
    public Long getArticleId() {
        return articleId;
    }
    */
    /*lombok -> @AllArgsConstructor
    public Article(Long articleId, String title, String date, String contents) {
        this.articleId = articleId;
        this.title = title;
        this.date = date;
        this.contents = contents;
    }
    */

    /*lombok -> @ToString
    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
     */
}
