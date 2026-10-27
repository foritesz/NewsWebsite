package com.example.newsbackend.service;

import com.example.newsbackend.model.News;
import com.example.newsbackend.model.Category;
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

  public List<News> getArticles() {
    return newsStore.findAll();
  }

  @Override
  public News addNews(News article) {
    return newsStore.save(article);
  }

  @Override
  public List<News> getWeeklyNews() {
    Date sevenDaysAgo = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
    return newsStore.findAll().stream()
        .filter(article -> article.getPublishDate() != null
            && !article.getPublishDate().before(sevenDaysAgo))
        .sorted(Comparator.comparingInt(News::getViews).reversed())
        .limit(5)
        .toList();
  }

  @Override
  public List<News> getFreshNews() {
    return newsStore.findAll().stream()
      .filter(article-> !article.isFrontpage())
        .sorted(Comparator.comparing(
            News::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .limit(5)
        .toList();
  }

  @Override
  public List<News> getPopularNews() {
    return newsStore.findAll().stream()
        .sorted(Comparator.comparingInt(News::getViews).reversed())
        .limit(5)
        .toList();
  }

  @Override
  public List<News> getCategory(Category category) {
    if (category == null) {
      return List.of();
    }
    return newsStore.findAll().stream()
        .filter(article -> article.getCategory() == category)
        .sorted(Comparator.comparing(
            News::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }

  public List<News> getBySearch(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      return List.of();
    }
    String normalizedTerm = searchTerm.trim().toLowerCase(Locale.ROOT);
    return newsStore.findAll().stream()
        .filter(article -> contains(article.getTitle(), normalizedTerm)
            || contains(article.getIntroduction(), normalizedTerm)
            || contains(article.getContent(), normalizedTerm))
        .sorted(Comparator.comparing(
            News::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }

  private boolean contains(String text, String normalizedTerm) {
    return text != null && text.toLowerCase(Locale.ROOT).contains(normalizedTerm);
  }
}
