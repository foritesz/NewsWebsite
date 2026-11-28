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
    article.setImageUrl(normalizeImageUrl(request.imageUrl()));
    article.setPublishDate(request.publishDate());
    article.setAuthor(request.author());
    return article;
  }

  public NewsDTO toResponse(NewsEntity article) {
    return new NewsDTO(
        article.getId(),
        article.getViews(),
        article.isFrontpage(),
        article.getTitle(),
        article.getIntroduction(),
        article.getContent(),
        normalizeImageUrl(article.getImageUrl()),
        article.getPublishDate(),
        article.getAuthor(),
        article.getCategory()
    );
  }

  /** Accepts either a plain URL or Markdown's [label](url) form. */
  private String normalizeImageUrl(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    if (trimmed.matches("^\\[[^]]+\\]\\(https?://[^)]+\\)$")) {
      return trimmed.substring(trimmed.indexOf('(') + 1, trimmed.length() - 1);
    }
    return trimmed;
  }
}
