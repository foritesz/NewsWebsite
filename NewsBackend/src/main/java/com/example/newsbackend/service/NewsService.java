package com.example.newsbackend.service;

import com.example.newsbackend.model.News;
import com.example.newsbackend.model.Category;
import com.example.newsbackend.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NewsService{

  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  public List<News> GetArticles() {
    return newsRepository.findAll();
  }

  public News AddNews(News article) {
    return newsRepository.save(article);
  }

  public List<News> GetWeeklyNews() {
    Date sevenDaysAgo = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
    return newsRepository.findAll().stream()
        .filter(article -> article.getPublishDate() != null
            && !article.getPublishDate().before(sevenDaysAgo))
        .sorted(Comparator.comparingInt(News::getViews).reversed())
        .limit(5)
        .toList();
  }

  public List<News> GetFreshNews() {
    return newsRepository.findAll().stream()
      .filter(article-> !article.isFrontpage())
        .sorted(Comparator.comparing(
            News::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .limit(5)
        .toList();
  }

  public List<News> GetPopularNews() {
    return newsRepository.findAll().stream()
        .sorted(Comparator.comparingInt(News::getViews).reversed())
        .limit(5)
        .toList();
  }

  public List<News> GetCategory(Category category) {
    if (category == null) {
      return List.of();
    }
    return newsRepository.findAll().stream()
        .filter(article -> article.getCategory() == category)
        .sorted(Comparator.comparing(
            News::getPublishDate,
            Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }

  public List<News> GetBySearch(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      return List.of();
    }
    String normalizedTerm = searchTerm.trim().toLowerCase(Locale.ROOT);
    return newsRepository.findAll().stream()
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
