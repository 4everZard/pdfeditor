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
  
    
    public static  FieldData[][] PdfIterator(String dest) throws IOException {
    	String DEST = dest;
    	PdfReader reader = new PdfReader(DEST);
    	AcroFields fields = reader.getAcroFields();
    	Set<String> fldNames = fields.getFields().keySet();
    	Rectangle mediabox = reader.getPageSize(1);
    	int pageNumber = reader.getNumberOfPages();
    	float pdfWidth = mediabox.getWidth();
    	float pdfHeight = mediabox.getHeight();
    	float pdfTotalHeight = mediabox.getHeight()*pageNumber;
    	
    	
    	int fieldNums = fldNames.size();
    	
    	FieldData[][] FieldData = new FieldData[fieldNums][14];
    	for(int x=0;x<fldNames.size();x++) {
    		for(int y=0;y<14;y++) {
    			FieldData[x][y] = new FieldData();
    		}
    	}
    	
    	
 
    	fieldNums = 0;
    	for (String fldName : fldNames) {
    		  fieldNums++;
    		 // System.out.println(fieldNums);
    		  //System.out.println("fieldNums is: "+fieldNums);
    		  List<FieldPosition> positions = fields.getFieldPositions(fldName);  
    		/* FieldData[fieldNums][5] = new FieldData();*/
    		  //FieldData[fieldNums][0].size = positions.size();
    		  System.out.println(positions.size());
    		  int size = positions.size();
    		  //System.out.println(size);
    		  Rectangle[] rect = new Rectangle[size];
    		  
    		  
    		  
    			  for (int j=0;j<size;j++) {
    	    		  System.out.println("fieldNums is: "+fieldNums);

    	    		  FieldData[fieldNums-1][j].size = size;
    				  rect[j] = positions.get(j).position;
    				  int page = positions.get(0).page;
        			  FieldData[fieldNums-1][j].value ="";             
        			  FieldData[fieldNums-1][j].name = fldName;
        			  FieldData[fieldNums-1][j].left =  rect[j].getLeft();
        			  FieldData[fieldNums-1][j].top  = rect[j].getTop()+(pageNumber-page)*pdfHeight;
        			  FieldData[fieldNums-1][j].width = rect[j].getWidth();
        			  FieldData[fieldNums-1][j].height =  rect[j].getHeight();
        			  FieldData[fieldNums-1][j].xPercentage = rect[j].getLeft() / pdfWidth;
        			  FieldData[fieldNums-1][j].yPercentage = 1-(FieldData[fieldNums-1][j].top / pdfTotalHeight);
        			  FieldData[fieldNums-1][j].widthPercentage = rect[j].getWidth() / pdfWidth;
        			  FieldData[fieldNums-1][j].heightPercentage = rect[j].getHeight() / pdfTotalHeight;
        			  FieldData[fieldNums-1][j].fieldType = fields.getFieldType(fldName);   	        	      
        			  FieldData[fieldNums-1][j].fieldName =""; 
    				  
    			  } 
    			
    			  
    	}
		return FieldData;
    }
}



/*  switch(size) {
	case 1:
	  Rectangle rect = positions.get(size-1).position;
  int page = positions.get(0).page;
  FieldData[fieldNums].size = positions.size();
  
  FieldData[fieldNums].value ="";             
  FieldData[fieldNums].name = fldName;
  FieldData[fieldNums].left =  rect.getLeft();
  FieldData[fieldNums].top  = rect.getTop()+(pageNumber-page)*pdfHeight;
  FieldData[fieldNums].width = rect.getWidth();
  FieldData[fieldNums].height =  rect.getHeight();
  FieldData[fieldNums].xPercentage = rect.getLeft() / pdfWidth;
  FieldData[fieldNums].yPercentage = 1-(FieldData[fieldNums].top / pdfTotalHeight);
  FieldData[fieldNums].widthPercentage = rect.getWidth() / pdfWidth;
  FieldData[fieldNums].heightPercentage = rect.getHeight() / pdfTotalHeight;
  FieldData[fieldNums].fieldType = fields.getFieldType(fldName);   	        	      
  FieldData[fieldNums].fieldName ="";   		  	
	break;
	
	case 2:
		
	
	break;
	
	case 3:
		
	break;
	
	case 4:
		
	break;
	
	case 5:
		
	break;


}*/
    