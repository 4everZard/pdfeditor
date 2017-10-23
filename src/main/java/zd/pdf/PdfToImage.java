package zd.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfToImage {

	 	  public static void PdfConverter(String pdfFilePath) throws InvalidPasswordException, IOException {
	 		  	 		  File pdfFile = new File(pdfFilePath);
	 		  	 		  
	 		  	 		  
	 		 
	 		//Loading an existing PDF document
		      //File file = new File("pdfExample.pdf");
		      PDDocument document = PDDocument.load(pdfFile);
		       
		      //Instantiating the PDFRenderer class
		      PDFRenderer renderer = new PDFRenderer(document);

		      //Rendering an image from the PDF document
		      BufferedImage image = renderer.renderImage(0);
		      
		      // initialize the location for the image file
		      File img = new File("/home/developer/eclipse-workspace/pdfeditor/src/main/resources/image/ImageSample.jpg");
		      
		      //store the path of the image to a string
		      String imgFilePath = img.getPath();
		   
		      //Writing the image to a file
		      ImageIO.write(image, "JPEG", new File(imgFilePath));
		      
		      System.out.println("Image created");
		       
		      //Closing the document
		      document.close();
	 		  
	 	  }
	 		
	      

	   
	}
