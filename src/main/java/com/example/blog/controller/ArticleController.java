package com.example.blog.controller;

import com.example.blog.dto.ArticleDto;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Slf4j  //로깅을 위한 어노테이션
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    //데이터 생성
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleDto dto){
        log.info(dto.toString());
        /* lombok -> @Slf4j  //로깅을 위한 어노테이션
        System.out.println(dto.toString());
         */

        //1.dto를 entity로 변환
        Article article=dto.toEntity();
        log.info(article.toString());//System.out.println(article.toString());

        //2.Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());//System.out.println(saved.toString());
        // 리다이렉트 적용: 생성 후, 브라우저가 해당 URL로 재요청
        return "redirect:/articles/"+saved.getArticleId();
    }

    //조회(단건조회)
    @GetMapping("/articles/{articleId}")
    public String show(@PathVariable Long articleId,Model model){
        log.info("articleId="+articleId);
        //1.articleId로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(articleId).orElse(null);
        //2.가져온 데이터를 모델에 등록
        model.addAttribute("articles",articleEntity);
        //3.보여줄 페이지 설정
        return "articles/show";
    }
    //조회
    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);

        // 3: 뷰 페이지를 설정!
        return "articles/index";
    }

    //수정
    //수정할 게시글 불러오기
    @GetMapping("/articles/{articleId}/edit")
    public String edit(@PathVariable Long articleId, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(articleId).orElse(null);
        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }

    //수정한 내용 전달하기
    //@PatchMapping을 html의 form이 제공하지 않아서
    @PostMapping("/articles/update")
    public String update(ArticleDto dto){
        log.info(dto.toString());
        //1. DTO를 Entity로 변경한다.
        Article articleEntity = dto.toEntity();
        //2. Entity를 DB로 저장한다
        //2-1. DB에서 기존 데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getArticleId()).orElse(null);
        //2-2: 기존 데이터가 있다면, 값을 갱신
        if(target!=null){//기존데이터가 있다면
            articleRepository.save(articleEntity);  //엔티티가 DB로 갱신됨
        }
        //3. 수정과 결과 페이지로 리다이렉트 한다
        //log.info(dto.toString());
        return "redirect:/articles/" + articleEntity.getArticleId();
    }

    //삭제
    @GetMapping("/articles/{articleId}/delete")
    public String delete(@PathVariable Long articleId, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1: 삭제 대상을 가져온다.
        Article target = articleRepository.findById(articleId).orElse(null);
        log.info(target.toString());

        // 2: 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다!");
        }

        // 3: 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }

}
