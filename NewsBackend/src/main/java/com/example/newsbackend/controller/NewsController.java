package com.example.newsbackend.controller;

import com.example.newsbackend.model.News;
import com.example.newsbackend.model.Category;
import com.example.newsbackend.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

  private final NewsService newsService;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/weekly")
  public List<News> getWeeklyNews() {
    return newsService.GetWeeklyNews();
  }

  @GetMapping("/fresh")
  public List<News> getFreshNews() {
    return newsService.GetFreshNews();
  }

  @GetMapping("/popular")
  public List<News> getPopularNews() {
    return newsService.GetPopularNews();
  }

  @GetMapping("/category")
  public List<News> getCategory(@RequestParam Category category) {
    return newsService.GetCategory(category);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public News addNews(@Valid @RequestBody News article) {
    article.setId(0);
    return newsService.AddNews(article);
  }
}
