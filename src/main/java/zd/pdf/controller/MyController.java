package zd.pdf.controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import zd.pdf.PdfFormUtil;
import zd.pdf.FileClass;
import zd.pdf.FieldData;


@RestController
public class MyController<PdfIterator>  {
	/*get the pdf file from local*/
	@RequestMapping(value = "/ImageSample", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException{
		ClassPathResource imgFile = new ClassPathResource("pdfForm.jpg");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());	
	}
	/*	get the fields coordinates using PdfIterator from pdf file*/
	@RequestMapping(value = "/pdf/fields", method = RequestMethod.GET)
	public @ResponseBody FieldData[] getCoordinates(HttpServletResponse response) throws IOException, DocumentException{
		return PdfFormUtil.PdfIterator("/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfForm.pdf");
	}
	/*get post data from the web page image */
	@RequestMapping(value="/pdf/fields",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	
	/*save the changes made by user in the front end to local pdf*/ 
    public void saveFields(@RequestBody FieldData fieldData[], HttpServletRequest request) throws IOException, DocumentException{
		
		PdfReader reader = new PdfReader(FileClass.sourcePdf);
		/*initialize PdfStamper in order to make changes to the pdf*/
		PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(FileClass.destPdf));
    	AcroFields fields = stamper.getAcroFields();
    	/*iterate through all the acrofield*/
    	for(int i=0;i<fieldData.length;i++) { 		
    		String value = fieldData[i].getValue(); 			 
    		String name = fieldData[i].getName();
    		boolean checkStatus = fieldData[i].getCheckStatus();    			
    		int fieldType = fieldData[i].getFieldType();
    		/* if fieldType equals to 4 or 7 that means the acrofield is textfield or signature field */
    		if(value != null && (fieldType ==4 || fieldType == 7)) {
	    		fields.setField(name,value);
	    		stamper.setFormFlattening(true);	
	    	}
    		/* if fieldType equals to 2 and value is 0, which means it is a single checkbox*/
    		else if (fieldType == 2 && checkStatus == true && value == "0") {
    			fields.setField(name, value);
    			stamper.setFormFlattening(true);
    		}
    		/* if fieldType equals to 2 and value is not 0, which means they are multiple checkboxes*/
    		else if(fieldType == 2 && checkStatus == true) {
    			String index = Integer.toString(Integer.parseInt(value) + 1);
    			System.out.println(name +"   " + index);
    			String status[] = fields.getAppearanceStates(name);
    			fields.setField(name, index); 
    			System.out.println(Arrays.toString(status));
    			stamper.setFormFlattening(true);  				
    		}
    	}
    	stamper.close();
    	reader.close();
	}
}




