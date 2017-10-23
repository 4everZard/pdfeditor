package zd.pdf;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String pdfFilePath = "/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfExample.pdf";
		PdfToImage.PdfConverter(pdfFilePath);
	
        SpringApplication.run(Application.class, args);
    }

}
	