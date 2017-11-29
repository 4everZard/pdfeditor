package zd.pdf;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// convert the source pdf to a image using PdfRenderer
		PdfRenderer.PdfConverter(FileClass.sourcePdf,FileClass.destinationImg);	
        SpringApplication.run(Application.class, args);
    }
	
	
}
	




