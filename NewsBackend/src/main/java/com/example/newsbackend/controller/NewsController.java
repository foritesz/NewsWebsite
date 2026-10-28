package com.example.newsbackend.controller;

import com.example.newsbackend.dto.CreateNewsRequest;
import com.example.newsbackend.dto.NewsDTO;
import com.example.newsbackend.dto.NewsMapper;
import com.example.newsbackend.entity.NewsEntity;
import com.example.newsbackend.domain.Category;
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
public class NewsController {

  private final NewsUseCases newsService;
  private final NewsMapper newsMapper;

  public NewsController(NewsUseCases newsService, NewsMapper newsMapper) {
    this.newsService = newsService;
    this.newsMapper = newsMapper;
  }

  @GetMapping("/weekly")
  public List<NewsDTO> getWeeklyNews() {
    return toResponses(newsService.getWeeklyNews());
  }

  @GetMapping("/fresh")
  public List<NewsDTO> getFreshNews() {
    return toResponses(newsService.getFreshNews());
  }

  @GetMapping("/popular")
  public List<NewsDTO> getPopularNews() {
    return toResponses(newsService.getPopularNews());
  }

  @GetMapping("/category")
  public List<NewsDTO> getCategory(@RequestParam Category category) {
    return toResponses(newsService.getCategory(category));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public NewsDTO addNews(@Valid @RequestBody CreateNewsRequest request) {
    NewsEntity article = newsMapper.toEntity(request);
    return newsMapper.toResponse(newsService.addNews(article));
  }

  private List<NewsDTO> toResponses(List<NewsEntity> articles) {
    return articles.stream().map(newsMapper::toResponse).toList();
  }
}
