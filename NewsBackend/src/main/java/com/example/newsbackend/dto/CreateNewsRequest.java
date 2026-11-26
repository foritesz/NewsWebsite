package com.example.newsbackend.dto;

import com.example.newsbackend.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateNewsRequest(
    int views,
    boolean frontpage,
    @NotBlank String title,
    @NotBlank String introduction,
    @NotBlank String content,
    String imageUrl,
    @NotNull Date publishDate,
    @NotNull Integer author,
    @NotNull Category category
) {}
