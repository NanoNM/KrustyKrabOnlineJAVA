package top.sleepnano.krustykrabonline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.sleepnano.krustykrabonline.mapper")
public class KrustyKrabOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrustyKrabOnlineApplication.class, args);
	}

}
