package com.bramkov.microservice.storage.bramkov_microservice_storage.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class ArticleRestController {

    @Autowired
    private ArticleRepository articleRepository;

    /*
            method: GET
            url: http://localhost:8082/api/articles
    */
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required=false) String title) {

        try {
            List<Article> articles = new ArrayList<>();

            if (title == null)
                articleRepository.findAll().forEach(articles::add);
            else
                articleRepository.findByTitleContaining(title).forEach(articles::add);

            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(articles, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
            method: GET
            url: http://localhost:8082/api/articles/{id}
    */
    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {

        Optional<Article> articleData =  articleRepository.findById(id);

        if (articleData.isPresent()) {
            return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
        method: POST
        url: http://localhost:8082/api/articles
        body:   {
                    "title": "wfsdf",
                    "description": "nmnm22",
                    "published": false
                }
    */
    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {

        try {
            String title = article.getTitle();
            String description = article.getDescription();
            System.out.println(String.format("title = %s, description = %s", title, description));

            Article _article =  articleRepository
                    .save(new Article(article.getTitle(), article.getDescription(), false));
            return new ResponseEntity<>(_article, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
        method: PUT
        url: http://localhost:8082/api/articles/{id} -> http://localhost:8082/api/articles/352
        body:   {
                    "title": "new: wfsdf",
                    "description": "new: nmnm22",
                    "published": false
                }
    */
    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article) {

        Optional<Article> articleData = articleRepository.findById(id);
        if (articleData.isPresent()) {
            Article _article = articleData.get();
            _article.setTitle(article.getTitle());
            _article.setDescription(article.getDescription());
            _article.setPublished(article.isPublished());
            return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
        method: delete
        url: http://localhost:8082/api/articles/{id} -> http://localhost:8082/api/articles/352
    */
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {

        try {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
        method: delete
        url: http://localhost:8082/api/articles
    */
    @DeleteMapping("/articles")
    public ResponseEntity<HttpStatus> deleteAllArticle() {

        try {
            articleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}