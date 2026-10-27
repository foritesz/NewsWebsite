package com.example.newsbackend.service;

import com.example.newsbackend.model.Category;
import com.example.newsbackend.model.News;
import com.example.newsbackend.repository.NewsStore;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

class NewsServiceTest {

  @Test
  void addNewsSavesArticleToListBackedRepository() {
    List<News> articles = new ArrayList<>();
    NewsStore repository = mock(NewsStore.class);
    doAnswer(invocation -> {
      News article = invocation.getArgument(0);
      articles.add(article);
      return article;
    }).when(repository).save(any(News.class));
    NewsService service = new NewsService(repository);

    News article = new News();
    article.setViews(0);
    article.setFrontpage(false);
    article.setCategory(Category.Entertainment);
    article.setTitle("Új hír");
    article.setIntroduction("A hír rövid bevezetője.");
    article.setContent("A hír teljes tartalma.");
    article.setPublishDate(new Date());
    article.setAuthor(1);
    article.setImageUrl(null);

    News savedArticle = service.addNews(article);

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
}
