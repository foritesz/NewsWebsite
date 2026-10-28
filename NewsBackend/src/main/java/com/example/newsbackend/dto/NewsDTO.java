package com.example.newsbackend.dto;

import com.example.newsbackend.domain.Category;

import java.util.Date;

/** API response shape for a news article. */
public record NewsDTO(
    int id,
    int views,
    Category category,
    boolean frontpage,
    String title,
    String introduction,
    String content,
    String imageUrl,
    Date publishDate,
    Integer author
) {}
