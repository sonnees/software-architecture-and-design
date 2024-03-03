package vn.edu.iuh.fit;

import jdepend.swingui.JDepend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@SpringBootApplication
public class Lab04Application {

	public static void main(String[] args) throws IOException {

//		SpringApplication.run(Lab04Application.class, args);

		JDepend depend =new JDepend();
		depend.addDirectory("T:\\Library-Assistant");
		depend.analyze();
		System.out.println("DONE");
	}

//	@Bean
	CommandLineRunner commandLineRunner_(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				log.info("*** commandLineRunner_");
				String path = "T:\\Library-Assistant";
				jdepend.xmlui.JDepend depend =new jdepend.xmlui.JDepend(new PrintWriter("reports/report.xml"));
				depend.addDirectory(path);
				depend.analyze();
				System.out.println("DONE");
			}
		};
	}

}
