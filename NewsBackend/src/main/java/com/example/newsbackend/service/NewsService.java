package com.example.newsbackend.service;

import com.example.newsbackend.model.News;
import com.example.newsbackend.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Calendar;
import java.util.Date;

@Service
public class NewsService {

  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  public List<News> GetArticles() {
    return newsRepository.findAll();
  }

  public List<News> GetWeeklyNews() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, -7);
    Date sevenDaysAgo = calendar.getTime();
    return newsRepository.findWeeklyTop5(sevenDaysAgo);
  }

  public List<News> GetFreshNews() {
    return newsRepository.findFreshTop5();
  }

  public List<News> GetPopularNews() {
    return newsRepository.findTop5MostViewed();
  }

  public List<News> GetCategory(String category) {
    return newsRepository.findByCategory(category.trim());
  }

  public List<News> GetBySearch(String searchTerm) {
    return newsRepository.searchArticles(searchTerm.trim());
  }
}
