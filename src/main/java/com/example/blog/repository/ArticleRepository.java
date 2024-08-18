package com.example.blog.repository;

import com.example.blog.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();
}
//CrudRepository라는 인터페이스를 상속받아옴
//우리가 정의한 DiaryRepository는 상속받아왔기 때문에 정의없이 그대로 사용 가능
//<관리대상Entity,대표값type>