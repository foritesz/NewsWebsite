package com.example.newsbackend;
import com.example.newsbackend.service.NewsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsBackendApplication implements CommandLineRunner {

  private final NewsService newsService;

  public NewsBackendApplication(NewsService newsService) {
    this.newsService = newsService;
  }

  public static void main(String[] args) {
    SpringApplication.run(NewsBackendApplication.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println("Weekly top 5:");
    newsService.GetWeeklyNews().forEach(System.out::println);

    System.out.println("Fresh top 5:");
    newsService.GetFreshNews().forEach(System.out::println);

    System.out.println("Popular top 5:");
    newsService.GetPopularNews().forEach(System.out::println);

    System.out.println("Category:");
    newsService.GetCategory("Közélet").forEach(System.out::println);

    System.out.println("Search");
    newsService.GetBySearch("Valami").forEach(System.out::println);
  }

}
