package zd.pdf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

import zd.pdf.PdfFormUtil;
import zd.pdf.fileClass;
import zd.pdf.FieldData;


@RestController
public class MyController<PdfField, PdfIterator>  {
	
	@RequestMapping(value = "/ImageSample", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException{

		ClassPathResource imgFile = new ClassPathResource("pdfForm.jpg");
		//System.out.print(imgFile.getPath());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());	
	}


	@RequestMapping(value = "/pdf/fields", method = RequestMethod.GET)

	public @ResponseBody FieldData[][] getCoordinates(HttpServletResponse response) throws IOException{
		return PdfFormUtil.PdfIterator("/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfForm.pdf");
	}
	
	
	@RequestMapping(value="/pdf/fields",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)

	
    public void saveFields(@RequestBody FieldData fieldData[], HttpServletRequest request) throws IOException, DocumentException{
		
		PdfReader reader = new PdfReader(fileClass.sourcePdf);
		PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(fileClass.destPdf));
    	AcroFields fields = stamper.getAcroFields();
    
		
    	for(int i=1;i<494;i++) {
		String value = fieldData[i].getValue();
		String name = fieldData[i].getName();
    	if(value != null) {
		fields.setField(name,value);
    	stamper.setFormFlattening(true);
    	}
		System.out.println(value);
		System.out.println(name);
		
    	}
    	stamper.close();
    	reader.close();
	}
}
