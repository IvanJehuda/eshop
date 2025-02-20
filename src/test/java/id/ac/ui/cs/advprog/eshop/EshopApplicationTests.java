package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
		assertThat(true).isTrue();
	}
	@Test
	void mainMethodRunsSpringApplication() {
		try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
			EshopApplication.main(new String[]{});
			mocked.verify(() -> SpringApplication.run(EshopApplication.class, new String[]{}));
		}
	}
}
