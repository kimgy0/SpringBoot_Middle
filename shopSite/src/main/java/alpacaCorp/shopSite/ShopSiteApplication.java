package alpacaCorp.shopSite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"alpacaCorp"})
public class ShopSiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopSiteApplication.class, args);
	}
}
