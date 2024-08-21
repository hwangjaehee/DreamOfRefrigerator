package com.example.blog.api;

import com.example.blog.dto.ArticleDto;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j//log
@RestController  //REST API 용 컨트롤러! 데이터(JSON)를 반환
public class ArticleApiController {
    @Autowired  //DI : 스프링부트에서 떙겨와야한다.
    private ArticleService articleService;

    //조회(전체조회) GET
    @GetMapping("api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    @GetMapping("/api/articles/{articleId}")
    public Article show(@PathVariable Long articleId){
        return articleService.show(articleId);
    }

    //생성 POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDto dto){
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //수정 PATCH
    @PatchMapping("/api/articles/{articleId}")
    public ResponseEntity<Article> update(@PathVariable Long articleId,
                                          @RequestBody ArticleDto dto) {
        Article updated = articleService.update(articleId, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //삭제 DELETE
    @DeleteMapping("/api/articles/{articleId}")
    public ResponseEntity<Article> delete(@PathVariable Long articleId) {
        Article deleted = articleService.delete(articleId);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}