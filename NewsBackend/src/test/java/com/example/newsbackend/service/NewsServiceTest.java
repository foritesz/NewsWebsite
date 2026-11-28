package com.example.newsbackend.service;

import com.example.newsbackend.domain.Category;
import com.example.newsbackend.entity.NewsEntity;
import com.example.newsbackend.repository.NewsStore;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsServiceTest {

  @Test
  void addNewsSavesArticleToListBackedRepository() {
    List<NewsEntity> articles = new ArrayList<>();
    NewsStore repository = mock(NewsStore.class);
    doAnswer(invocation -> {
      NewsEntity article = invocation.getArgument(0);
      articles.add(article);
      return article;
    }).when(repository).save(any(NewsEntity.class));
    NewsService service = new NewsService(repository);

    NewsEntity article = new NewsEntity();
    article.setViews(0);
    article.setFrontpage(false);
    article.setCategory(Category.Entertainment);
    article.setTitle("Új hír");
    article.setIntroduction("A hír rövid bevezetője.");
    article.setContent("A hír teljes tartalma.");
    article.setPublishDate(new Date());
    article.setAuthor(1);
    article.setImageUrl(null);

    NewsEntity savedArticle = service.addNews(article);

    assertSame(article, savedArticle);
    assertEquals(1, articles.size());
    assertSame(article, articles.get(0));
    assertEquals(Category.Entertainment, articles.get(0).getCategory());
    assertEquals("Új hír", articles.get(0).getTitle());
    assertEquals("A hír rövid bevezetője.", articles.get(0).getIntroduction());
    assertEquals("A hír teljes tartalma.", articles.get(0).getContent());
    assertEquals(0, articles.get(0).getViews());
    assertFalse(articles.get(0).isFrontpage());
    assertEquals(1, articles.get(0).getAuthor());
    assertNull(articles.get(0).getImageUrl());
    assertEquals(article.getPublishDate(), articles.get(0).getPublishDate());
  }

  @Test
  void setFrontPageClearsThePreviousArticleAndSelectsTheRequestedOne() {
    NewsStore store = mock(NewsStore.class);
    NewsEntity previousFrontPage = new NewsEntity();
    previousFrontPage.setId(1);
    previousFrontPage.setFrontpage(true);
    NewsEntity selectedArticle = new NewsEntity();
    selectedArticle.setId(2);
    selectedArticle.setFrontpage(false);

    when(store.findById(2)).thenReturn(java.util.Optional.of(selectedArticle));
    when(store.findAll()).thenReturn(List.of(previousFrontPage, selectedArticle));
    when(store.save(any(NewsEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    NewsService service = new NewsService(store);

    boolean updated = service.setFrontPage(2, true);

    assertTrue(updated);
    assertFalse(previousFrontPage.isFrontpage());
    assertTrue(selectedArticle.isFrontpage());
    verify(store).save(previousFrontPage);
    verify(store).save(selectedArticle);
  }

  @Test
  void setFrontPageReturnsFalseWhenArticleDoesNotExist() {
    NewsStore store = mock(NewsStore.class);
    when(store.findById(999)).thenReturn(java.util.Optional.empty());
    NewsService service = new NewsService(store);

    assertFalse(service.setFrontPage(999, true));
  }
}
