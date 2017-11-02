package zd.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfReader;

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
  
    
    public static  FieldData[] PdfIterator(String dest) throws IOException {
    	String DEST = dest;
    	PdfReader reader = new PdfReader(DEST);
    	AcroFields fields = reader.getAcroFields();
    	//edit
 
    	
    	//
    	Set<String> fldNames = fields.getFields().keySet();
    	//List list = new ArrayList();
    	
    	
    	Rectangle mediabox = reader.getPageSize(1);
    	int pageNumber = reader.getNumberOfPages();
    	float pdfWidth = mediabox.getWidth();
    	float pdfHeight = mediabox.getHeight();
    	float pdfTotalHeight = mediabox.getHeight()*pageNumber;
    	//System.out.println(pdfWidth+" "+pdfHeight+" "+pdfTotalHeight);
    	//System.out.println(pdfWidth+" "+pdfHeight);
    	
    	
    	int fieldNums =1;
    	for(String fldName : fldNames) {
    		fieldNums++;
    	}
    	//System.out.println(fieldNums);
    	FieldData[] FieldData = new FieldData[fieldNums];
    	
    	
    	fieldNums = 0;
    	for (String fldName : fldNames) {
    		  fieldNums++;
    		  FieldData[fieldNums] = new FieldData();
    	
    		  //System.out.print(pageIndex);
    		  List<FieldPosition> positions = fields.getFieldPositions(fldName);
    		  
    	      Rectangle rect = positions.get(0).position;
    	      // get the page index of each data field
    	      int page = positions.get(0).page;
    	      
    	      //System.out.println(page);
    	      FieldData[fieldNums].name = fldName;
    	      FieldData[fieldNums].left =  rect.getLeft();
    	      FieldData[fieldNums].top  = rect.getTop()+(pageNumber-page)*pdfHeight;
    	      FieldData[fieldNums].width = rect.getWidth();
    	      FieldData[fieldNums].height =  rect.getHeight();
    	      FieldData[fieldNums].xPercentage = rect.getLeft() / pdfWidth;
    	      FieldData[fieldNums].yPercentage = 1-(FieldData[fieldNums].top / pdfTotalHeight);
    	      FieldData[fieldNums].widthPercentage = rect.getWidth() / pdfWidth;
    	      FieldData[fieldNums].heightPercentage = rect.getHeight() / pdfTotalHeight;
    	      
    	   
    	      //list.add(FieldData.left);
    	      //list.add(FieldData.top);
    	     
//    	      list.add(xPercentage);
//    	      list.add(yPercentage);
//    	      list.add(widthPercentage);
//    	      list.add(heightPercentage);
    	      
    	      
    	      
    	      //System.out.println( fldName + " "+fields.getFieldType(fldName)+" " +xPercentage+" "+ yPercentage+" "+widthPercentage+" "+heightPercentage);
    	      
    	}
    	//System.out.println(Arrays.toString(FieldData));
    	//list.add(0,fieldNums);
		return FieldData;
    	
    	

    	
    	
    	
    }
    
    
    
    
    
}
    