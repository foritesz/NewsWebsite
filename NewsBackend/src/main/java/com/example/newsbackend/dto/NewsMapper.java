package com.example.newsbackend.dto;

import com.example.newsbackend.entity.NewsEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

  public NewsEntity toEntity(CreateNewsRequest request) {
    NewsEntity article = new NewsEntity();
    article.setViews(request.views());
    article.setCategory(request.category());
    article.setFrontpage(request.frontpage());
    article.setTitle(request.title());
    article.setIntroduction(request.introduction());
    article.setContent(request.content());
    article.setImageUrl(request.imageUrl());
    article.setPublishDate(request.publishDate());
    article.setAuthor(request.author());
    return article;
  }

  public NewsDTO toResponse(NewsEntity article) {
    return new NewsDTO(
        article.getId(),
        article.getViews(),
        article.getCategory(),
        article.isFrontpage(),
        article.getTitle(),
        article.getIntroduction(),
        article.getContent(),
        article.getImageUrl(),
        article.getPublishDate(),
        article.getAuthor()
    );
  }
}
