package zd.pdf.controller;

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

import zd.pdf.PdfFormUtil;
import zd.pdf.FieldData;


@RestController
public class MyController<PdfField, PdfIterator>  {
	
	@RequestMapping(value = "/ImageSample", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException{

		ClassPathResource imgFile = new ClassPathResource("pdfForm.jpg");
		System.out.print(imgFile.getPath());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());	
	}


	@RequestMapping(value = "/pdf/fields", method = RequestMethod.GET)

	public @ResponseBody FieldData[] getCoordinates(HttpServletResponse response) throws IOException{
		return PdfFormUtil.PdfIterator("/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfForm.pdf");
	}
	
	
	@RequestMapping(value="/pdf/fields",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)

	
    public void saveFields(@RequestBody FieldData fieldData[], HttpServletRequest request) throws IOException{
		String value = fieldData[1].getValue();
		System.out.println(value);
	}
}
