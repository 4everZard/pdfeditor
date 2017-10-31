package zd.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPageLabels;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import zd.pdf.PdfField.PdfFieldData;

import org.json.simple.*;

public class PdfFormUtil<PdfField> {
	
   // public static final String SRC = "/home/developer/Downloads/PDFform.pdf";    // set location for source pdf 
   // public static final String DEST = "/home/developer/Downloads/example.pdf";   // set location for destination pdf
    
    
    // In main function, open DEST and call manipulatePdf function.
    public static void main(String[] args) throws IOException, DocumentException {    
        File file = new File(fileClass.sourcePdf);
        file.getParentFile().mkdirs();
        //new PdfFormUtil().manipulatePdf(SRC, DEST);
        PdfIterator(fileClass.sourcePdf);
    }
  
    
    public static List<PdfFieldData> PdfIterator(String dest) throws IOException {
    	String DEST = dest;
    	PdfReader reader = new PdfReader(DEST);
    	AcroFields fields = reader.getAcroFields();
    	//edit
 
    	
    	//
    	Set<String> fldNames = fields.getFields().keySet();
    	List list = new ArrayList();
    	PdfFieldData pdfFieldData = new PdfFieldData();
    	
    	Rectangle mediabox = reader.getPageSize(1);
    	int pageNumber = reader.getNumberOfPages();
    	float pdfWidth = mediabox.getWidth();
    	float pdfHeight = mediabox.getHeight()*pageNumber;
    	
    	//System.out.println(pdfWidth+" "+pdfHeight);
    	int fieldNums =0;
    	for (String fldName : fldNames) {
    		
    	
    		  //System.out.print(pageIndex);
    		  List<FieldPosition> positions = fields.getFieldPositions(fldName);
    		  
    	      Rectangle rect = positions.get(0).position;
    	      pdfFieldData.left   =  rect.getLeft();
    	      pdfFieldData.top   = rect.getTop();
    	      pdfFieldData.width = rect.getWidth();
    	      pdfFieldData.height =  rect.getHeight();
    	      float xPercentage = pdfFieldData.left / pdfWidth;
    	      float yPercentage = 1-(pdfFieldData.top / pdfHeight);
    	      float widthPercentage = pdfFieldData.width / pdfWidth;
    	      float heightPercentage = pdfFieldData.height / pdfHeight;
    	      
    	      //list.add(pdfFieldData.left);
    	      //list.add(pdfFieldData.top);
    	     
    	      list.add(xPercentage);
    	      list.add(yPercentage);
    	      list.add(widthPercentage);
    	      list.add(heightPercentage);
    	      
    	      fieldNums++;
    	      
    	      System.out.println( fldName + " "+fields.getFieldType(fldName)+" " +pdfFieldData.left+" "+ pdfFieldData.top+" "+pdfFieldData.width+" "+pdfFieldData.height);
    		
    	}
    	list.add(0,fieldNums);
    	System.out.println(list);
    	
		return list;
    	
    	
    	
    	
    }
    
    
    
    
    
}
    