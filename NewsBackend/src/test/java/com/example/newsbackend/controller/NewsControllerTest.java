package com.example.newsbackend.controller;

import com.example.newsbackend.domain.Category;
import com.example.newsbackend.entity.NewsEntity;
import com.example.newsbackend.dto.NewsMapper;
import com.example.newsbackend.service.NewsUseCases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsControllerTest {

  private NewsUseCases newsUseCases;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    newsUseCases = mock(NewsUseCases.class);
    mockMvc = MockMvcBuilders.standaloneSetup(new NewsController(newsUseCases, new NewsMapper())).build();
  }

  @Test
  void getWeeklyNewsReturnsWeeklyArticles() throws Exception {
    when(newsUseCases.getWeeklyNews()).thenReturn(List.of(article()));

    mockMvc.perform(get("/api/news/weekly"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Teszt hír"));

    verify(newsUseCases).getWeeklyNews();
  }

  @Test
  void getNewsByIdReturnsMatchingArticle() throws Exception {
    when(newsUseCases.getNewsById(42)).thenReturn(Optional.of(articleWithId(42)));

    mockMvc.perform(get("/api/news/42"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(42))
        .andExpect(jsonPath("$.title").value("Teszt hír"));

    verify(newsUseCases).getNewsById(42);
  }

  @Test
  void getNewsByIdReturnsNotFoundWhenArticleDoesNotExist() throws Exception {
    when(newsUseCases.getNewsById(999)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/news/999"))
        .andExpect(status().isNotFound());

    verify(newsUseCases).getNewsById(999);
  }

  @Test
  void getFreshNewsReturnsFreshArticles() throws Exception {
    when(newsUseCases.getFreshNews()).thenReturn(List.of(article()));

    mockMvc.perform(get("/api/news/fresh"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Teszt hír"));

    verify(newsUseCases).getFreshNews();
  }

  @Test
  void getPopularNewsReturnsPopularArticles() throws Exception {
    when(newsUseCases.getPopularNews()).thenReturn(List.of(article()));

    mockMvc.perform(get("/api/news/popular"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Teszt hír"));

    verify(newsUseCases).getPopularNews();
  }

  @Test
  void getCategoryPassesEnumCategoryAndReturnsArticles() throws Exception {
    when(newsUseCases.getCategory(Category.Entertainment)).thenReturn(List.of(article()));

    mockMvc.perform(get("/api/news/category").param("category", "Entertainment"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category").value("Entertainment"));

    verify(newsUseCases).getCategory(Category.Entertainment);
  }

  @Test
  void addNewsAcceptsJsonAndReturnsCreatedArticle() throws Exception {
    NewsEntity article = article();
    when(newsUseCases.addNews(any(NewsEntity.class))).thenReturn(article);
    String requestBody = """
        {
          "views": 0,
          "category": "Entertainment",
          "frontpage": false,
          "title": "Teszt hír",
          "introduction": "Bevezető",
          "content": "Tartalom",
          "imageUrl": null,
          "publishDate": "2026-10-27T10:30:00Z",
          "author": 1
        }
        """;

    mockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Teszt hír"))
        .andExpect(jsonPath("$.category").value("Entertainment"));

    var articleCaptor = forClass(NewsEntity.class);
    verify(newsUseCases).addNews(articleCaptor.capture());
    assertEquals("Teszt hír", articleCaptor.getValue().getTitle());
    assertEquals(Category.Entertainment, articleCaptor.getValue().getCategory());
    assertNull(articleCaptor.getValue().getImageUrl());
  }

  @Test
  void setFrontPagePostsTheRequestedArticleAndReturnsNoContent() throws Exception {
    when(newsUseCases.setFrontPage(42, true)).thenReturn(true);

    mockMvc.perform(post("/api/news/42/frontpage").param("frontpage", "true"))
        .andExpect(status().isNoContent());

    verify(newsUseCases).setFrontPage(42, true);
  }

  @Test
  void setFrontPageReturnsNotFoundWhenArticleDoesNotExist() throws Exception {
    when(newsUseCases.setFrontPage(999, true)).thenReturn(false);

    mockMvc.perform(post("/api/news/999/frontpage").param("frontpage", "true"))
        .andExpect(status().isNotFound());

    verify(newsUseCases).setFrontPage(999, true);
  }

  private NewsEntity article() {
    NewsEntity article = new NewsEntity();
    article.setViews(10);
    article.setCategory(Category.Entertainment);
    article.setFrontpage(false);
    article.setTitle("Teszt hír");
    article.setIntroduction("Bevezető");
    article.setContent("Tartalom");
    article.setPublishDate(new Date());
    article.setAuthor(1);
    return article;
  }

  private NewsEntity articleWithId(int id) {
    NewsEntity article = article();
    article.setId(id);
    return article;
  }
}
