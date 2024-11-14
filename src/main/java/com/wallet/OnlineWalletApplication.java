package com.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wallet.controller.UserDetailsController;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class OnlineWalletApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(OnlineWalletApplication.class, args);
//	 ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		UserDetailsController bean = run.getBean(UserDetailsController.class);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.wallet"))
				.build();
	}

	// http://localhost:8081/swagger-ui.html#/

//	@Bean
//	public BCryptPasswordEncoder getEncode() {
//		return new BCryptPasswordEncoder();
//	}

}
