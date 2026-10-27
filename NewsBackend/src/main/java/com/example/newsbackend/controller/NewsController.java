package com.example.newsbackend.controller;

import com.example.newsbackend.model.News;
import com.example.newsbackend.model.Category;
import com.example.newsbackend.service.NewsUseCases;
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
public class NewsController implements NewsUseCases {

  private final NewsUseCases newsService;

  public NewsController(NewsUseCases newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/weekly")
  public List<News> getWeeklyNews() {
    return newsService.getWeeklyNews();
  }

  @GetMapping("/fresh")
  public List<News> getFreshNews() {
    return newsService.getFreshNews();
  }

  @GetMapping("/popular")
  public List<News> getPopularNews() {
    return newsService.getPopularNews();
  }

  @GetMapping("/category")
  public List<News> getCategory(@RequestParam Category category) {
    return newsService.getCategory(category);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public News addNews(@Valid @RequestBody News article) {
    article.setId(0);
    return newsService.addNews(article);
  }
}
