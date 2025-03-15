package com.bramkov.microservice.storage.bramkov_microservice_storage.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public String getArticles(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "article/articles";
    }

    @GetMapping("/create")
    public String createArticle(Model model) {
        model.addAttribute("title","Создать статью");
        model.addAttribute("article", new Article());
        return "article/create";
    }

    /*
        необходимо реализовать валидацию
     */
    @PostMapping("/create")
    public String createArticle(@ModelAttribute("article") Article article) {

        Article _article = new Article();
        _article.setTitle(article.getTitle());
        _article.setDescription(article.getDescription());
        _article.setPublished(false);

        articleRepository.save(_article);

        return "redirect:/article";
    }

    @GetMapping("/update/{id}")
    public String updateArticle(@PathVariable("id") Long id, Model model) {

        Optional<Article> article = articleRepository.findById(id);

        if (article.isPresent()) {
            model.addAttribute("title","Обновить статью");
            model.addAttribute("article", article.get());
            return "article/update";
        } else {
            return "redirect:/article";
        }
    }

    @PutMapping("/update/{id}")
    public String updateArticle(@PathVariable("id") Long id, @ModelAttribute("article") Article article) {

        System.out.println(String.format("method: put; /article/update/%s",id));

        Optional<Article> data = articleRepository.findById(id);

        if (data.isPresent()) {
            Article _article = data.get();
            _article.setTitle(article.getTitle());
            _article.setDescription(article.getDescription());
            _article.setPublished(false);
            articleRepository.save(_article);
        }

        return "redirect:/article";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {

        Optional<Article> data = articleRepository.findById(id);

        if (data.isPresent()) {
            articleRepository.deleteById(id);
        }

        return "redirect:/article";
    }
}