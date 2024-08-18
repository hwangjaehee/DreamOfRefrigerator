package com.example.blog;

import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit1();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final ArticleRepository diaryRepository;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Article article1 = new Article(1L, "t1", "d1", "c1");
            diaryRepository.save(article1);

            Article article2 = new Article(2L, "t2", "d2", "c2");
            diaryRepository.save(article2);

            Article article3 = new Article(3L, "t3", "d3", "c3");
            diaryRepository.save(article3);
        }
    }
}
