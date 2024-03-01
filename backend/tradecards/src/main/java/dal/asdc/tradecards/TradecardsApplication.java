package dal.asdc.tradecards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TradecardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradecardsApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configure(){
		return new WebMvcConfigurer() {
			public void addCorsMapping(CorsRegistry reg){
				reg.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
