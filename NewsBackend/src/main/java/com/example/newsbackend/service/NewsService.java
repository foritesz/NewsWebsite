package com.example.newsbackend.service;

import com.example.newsbackend.entity.NewsEntity;
import com.example.newsbackend.domain.Category;
import com.example.newsbackend.repository.NewsStore;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NewsService implements NewsUseCases {

  private final NewsStore newsStore;

  public NewsService(NewsStore newsStore) {
    this.newsStore = newsStore;
  }

  public List<NewsEntity> getArticles() {
    return newsStore.findAll();
  }

  @Override
  public NewsEntity addNews(NewsEntity article) {
    return newsStore.save(article);
  }

  @Override
  public List<NewsEntity> getWeeklyNews() {
    Date sevenDaysAgo = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
    return newsStore.findAll().stream()
        .filter(article -> article.getPublishDate() != null
            && !article.getPublishDate().before(sevenDaysAgo))
        .sorted(Comparator.comparingInt(NewsEntity::getViews).reversed())
        .limit(5)
        .toList();
  }

  @Override
  public List<NewsEntity> getFreshNews() {
    return newsStore.findAll().stream()
      .filter(article-> !article.isFrontpage())
        .sorted(Comparator.comparing(
            NewsEntity::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .limit(5)
        .toList();
  }

  @Override
  public List<NewsEntity> getPopularNews() {
    return newsStore.findAll().stream()
        .sorted(Comparator.comparingInt(NewsEntity::getViews).reversed())
        .limit(5)
        .toList();
  }

  @Override
  public List<NewsEntity> getCategory(Category category) {
    if (category == null) {
      return List.of();
    }
    return newsStore.findAll().stream()
        .filter(article -> article.getCategory() == category)
        .sorted(Comparator.comparing(
            NewsEntity::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }

  public List<NewsEntity> getBySearch(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      return List.of();
    }
    String normalizedTerm = searchTerm.trim().toLowerCase(Locale.ROOT);
    return newsStore.findAll().stream()
        .filter(article -> contains(article.getTitle(), normalizedTerm)
            || contains(article.getIntroduction(), normalizedTerm)
            || contains(article.getContent(), normalizedTerm))
        .sorted(Comparator.comparing(
            NewsEntity::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }

  private boolean contains(String text, String normalizedTerm) {
    return text != null && text.toLowerCase(Locale.ROOT).contains(normalizedTerm);
  }
}
