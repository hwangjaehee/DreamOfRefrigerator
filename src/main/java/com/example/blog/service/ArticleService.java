package com.example.blog.service;

import com.example.blog.dto.ArticleDto;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service//서비스 선언(서비스 객체를 스프링부트에 생성! 여기서 객체가 만들어지니까 Autowired할 수 있는 것!)(DI 할 수 있는 것!)
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    public Article create(ArticleDto dto) {
        Article article = dto.toEntity();
        if(article.getArticleId()!=null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long articleId, ArticleDto dto) {
        // 1: DTO -> 엔티티
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", articleId, article.toString());
        // 2: 타겟 조회
        Article target = articleRepository.findById(articleId).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || articleId != article.getArticleId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", articleId, article.toString());
            return null;
        }
        // 4: 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long articleId) {
        // 대상 찾기
        Article target = articleRepository.findById(articleId).orElse(null);
        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }
}
