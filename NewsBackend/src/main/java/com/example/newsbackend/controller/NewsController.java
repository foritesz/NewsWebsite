package com.example.newsbackend.controller;


import com.example.newsbackend.domain.Category;
import com.example.newsbackend.dto.CreateNewsRequest;
import com.example.newsbackend.dto.NewsDTO;
import com.example.newsbackend.dto.NewsMapper;
import com.example.newsbackend.entity.NewsEntity;
import com.example.newsbackend.service.NewsUseCases;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@Tag(name = "News", description = "News listing and creation endpoints")
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class NewsController {

  private final NewsUseCases newsService;
  private final NewsMapper newsMapper;

  public NewsController(NewsUseCases newsService, NewsMapper newsMapper) {
    this.newsService = newsService;
    this.newsMapper = newsMapper;
  }

  @GetMapping("/weekly")
  @Operation(summary = "Get weekly news", description = "Returns up to five news articles from the last seven days, ordered by views.")
  @ApiResponse(
      responseCode = "200",
      description = "Weekly news retrieved",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))
  )
  public List<NewsDTO> getWeeklyNews() {
    return toResponses(newsService.getWeeklyNews());
  }

  @GetMapping("/fresh")
  @Operation(summary = "Get fresh news", description = "Returns the five newest non-frontpage articles.")
  @ApiResponse(
      responseCode = "200",
      description = "Fresh news retrieved",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))
  )
  public List<NewsDTO> getFreshNews() {
    return toResponses(newsService.getFreshNews());
  }

  @GetMapping("/popular")
  @Operation(summary = "Get popular news", description = "Returns up to five articles ordered by view count.")
  @ApiResponse(
      responseCode = "200",
      description = "Popular news retrieved",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))
  )
  public List<NewsDTO> getPopularNews() {
    return toResponses(newsService.getPopularNews());
  }

  @GetMapping("/category")
  @Operation(summary = "Get news by category", description = "Returns articles in the requested category, newest first.")
  @ApiResponse(
      responseCode = "200",
      description = "Category news retrieved",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))
  )
  public List<NewsDTO> getCategory(
      @Parameter(description = "Category enum value", example = "Technology")
      @RequestParam("category") Category category
  ) {
    return toResponses(newsService.getCategory(category));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @SecurityRequirement(name = "basicAuth")
  @Operation(summary = "Add news", description = "Creates a news article from the supplied request DTO.")
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "News article created",
          content = @Content(schema = @Schema(implementation = NewsDTO.class))
      ),
      @ApiResponse(responseCode = "400", description = "The request body is invalid"),
      @ApiResponse(responseCode = "401", description = "Authentication is required")
  })
  public NewsDTO addNews(@Valid @RequestBody CreateNewsRequest request) {
    NewsEntity article = newsMapper.toEntity(request);
    return newsMapper.toResponse(newsService.addNews(article));
  }

  private List<NewsDTO> toResponses(List<NewsEntity> articles) {
    return articles.stream().map(newsMapper::toResponse).toList();
  }
}
