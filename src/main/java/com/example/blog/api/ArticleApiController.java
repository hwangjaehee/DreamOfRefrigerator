package com.example.blog.api;

import com.example.blog.dto.ArticleDto;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j//log
@RestController  //REST API 용 컨트롤러! 데이터(JSON)를 반환
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    //조회(전체조회) GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }
    //조회(전체조회) GET
    @GetMapping("/api/articles/{articleId}")//단일 article 반환할 거니까 return 타입 article!
    public Article show(@PathVariable Long articleId){
        return articleRepository.findById(articleId).orElse(null);
    }

    //생성 POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleDto dto){
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //수정 PATCH
    @PatchMapping("/api/articles/{articleId}")
    public ResponseEntity<Article> update(@PathVariable Long articleId, @RequestBody ArticleDto dto){
        //1. 수정용 엔티티 생성  [ DTO -> 엔티티 ]
        Article article = dto.toEntity();  //수정한 dto받아서 entity로 바꾸기
        //2. 대상 엔티티 조회 [타겟 조회]
        Article target = articleRepository.findById(articleId).orElse(null);
        //3.잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target==null || articleId!=article.getArticleId()){
            log.info("잘못된 요청! articleId : {}, article : {}",articleId,article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4.업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);//새롭게 붙여진 애를 save
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //삭제 DELETE
    @DeleteMapping("/api/articles/{articleId}")
    public ResponseEntity<Article> delete(@PathVariable Long articleId){
        //대상 찾기
        Article target=articleRepository.findById(articleId).orElse(null);
        //잘못된 요청 처리 삭제하려고 한 게 없을 경우
        if (target ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();//or .body(null);
    }

}
