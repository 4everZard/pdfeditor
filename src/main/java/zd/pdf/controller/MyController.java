package zd.pdf.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zd.pdf.PdfField.PdfFieldData;
import zd.pdf.PdfFormUtil;


@RestController
public class MyController<PdfField, PdfIterator>  {

	@RequestMapping(value = "/ImageSample", method = RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response) throws IOException{

		ClassPathResource imgFile = new ClassPathResource("image/ImageSample.jpg");
		System.out.print(imgFile.getPath());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());

	}


	@RequestMapping(value = "/pdf/fields", method = RequestMethod.GET)

	public @ResponseBody List<PdfFieldData> getCoordinates(HttpServletResponse response) throws IOException{
		return PdfFormUtil.PdfIterator("/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfExample.pdf");
	}
}