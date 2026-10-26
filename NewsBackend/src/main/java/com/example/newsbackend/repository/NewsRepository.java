package com.example.newsbackend.repository;

import com.example.newsbackend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
  @Query(value = """
    SELECT * FROM "News"
    WHERE date >= :since
    ORDER BY views DESC
    LIMIT 5
    """, nativeQuery = true)
    List<News> findWeeklyTop5(@Param("since") Date since);

  @Query(value = """
    SELECT * FROM "News"
    ORDER BY date DESC
    LIMIT 5
    """, nativeQuery = true)
    List<News> findFreshTop5();

  @Query(value = """
    SELECT * FROM "News"
    ORDER BY views DESC
    LIMIT 5
    """, nativeQuery = true)
    List<News> findTop5MostViewed();

  @Query(value = """
    SELECT * FROM "News"
    WHERE LOWER(category) = LOWER(:category)
    ORDER BY date DESC
    """, nativeQuery = true)
    List<News> findByCategory(@Param("category") String category);

  @Query(value = """
    SELECT * FROM "News"
    WHERE title ILIKE CONCAT('%', :term, '%')
       OR introduction ILIKE CONCAT('%', :term, '%')
       OR content ILIKE CONCAT('%', :term, '%')
    ORDER BY date DESC
    """, nativeQuery = true)
    List<News> searchArticles(@Param("term") String term);
}
