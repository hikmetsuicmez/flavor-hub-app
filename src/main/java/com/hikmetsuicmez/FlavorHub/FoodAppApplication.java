package com.hikmetsuicmez.FlavorHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@RequiredArgsConstructor
public class FoodAppApplication {

//	private final NotificationService notificationService;


	public static void main(String[] args) {
		SpringApplication.run(FoodAppApplication.class, args);
	}
//
//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			NotificationDTO notificationDTO = NotificationDTO.builder()
//					.recipient("aleynasanlibayrak@outlook.com")
//					.subject("Selam Hayatım")
//					.body("Bu bir test mailidir. Ve sana bayıldığımı bu test ile belirtmek istedim.")
//					.type(NotificationType.EMAIL)
//					.build();
//
//			notificationService.sendEmail(notificationDTO);
//		};
//	}

}
