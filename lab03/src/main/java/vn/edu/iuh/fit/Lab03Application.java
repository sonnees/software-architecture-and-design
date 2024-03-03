package vn.edu.iuh.fit;

import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.jdepend.parser.XMLParser;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class Lab03Application {

	XMLParser xmlParser;


	public static void main(String[] args) {
		SpringApplication.run(Lab03Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				jdepend.framework.JDepend d = new jdepend.framework.JDepend();
				d.addDirectory("../lab03/build/classes/java/main/vn/edu/iuh/fit/jdepend");
				Collection<JavaPackage> cols = d.analyze();
				cols.forEach(pkg -> {
					System.out.printf("Package %s, Ca= %d; Ce=%d;\n", pkg.getName(),
							pkg.getAfferents().size(), pkg.getEfferents().size());
				});

				String path = "../lab03/build/classes/java/main/vn/edu/iuh/fit/jdepend";
				xmlParser.writeXML(path);
			}
		};
	}
}
