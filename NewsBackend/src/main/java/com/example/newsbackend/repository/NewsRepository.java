package com.example.newsbackend.repository;

import com.example.newsbackend.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer>, NewsStore {
}
