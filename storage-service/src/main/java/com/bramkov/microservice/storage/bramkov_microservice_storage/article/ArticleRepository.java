package com.bramkov.microservice.storage.bramkov_microservice_storage.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article>  findByPublished(boolean published);
    List<Article> findByTitleContaining(String title);
}
