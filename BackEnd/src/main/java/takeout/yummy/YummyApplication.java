package takeout.yummy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class YummyApplication {


	public static void main(String[] args) {
		SpringApplication.run(YummyApplication.class, args);
		System.out.println("sprint boot");
	}


}
