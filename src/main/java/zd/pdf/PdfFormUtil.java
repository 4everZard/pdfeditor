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

import zd.pdf.FieldData.position;

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
    	Set<String> fldNames = fields.getFields().keySet();
    	Rectangle mediabox = reader.getPageSize(1);
    	int pageNumber = reader.getNumberOfPages();
    	float pdfWidth = mediabox.getWidth();
    	float pdfHeight = mediabox.getHeight();
    	float pdfTotalHeight = mediabox.getHeight()*pageNumber;
    	
    	
    	int fieldNums = fldNames.size();
    	
    	FieldData[] FieldData = new FieldData[fieldNums];
    	for(int x=0;x<fldNames.size();x++) {
    	
    			FieldData[x] = new FieldData();
    		
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
    		  
    		  
    		  FieldData[fieldNums-1].size = size;
    		  FieldData[fieldNums-1].value ="";             
			  FieldData[fieldNums-1].name = fldName;
			  FieldData[fieldNums-1].fieldType = fields.getFieldType(fldName);   	        	      
			  FieldData[fieldNums-1].fieldName =""; 
			  FieldData[fieldNums-1].isChecked=false;
			  FieldData[fieldNums-1].positions = new position[14];
			  for(int i=0;i<14;i++) {
				  FieldData[fieldNums-1].positions[i] = new position();
			  }
			  for (int j=0;j<size;j++) {
    	    		  System.out.println("fieldNums is: "+fieldNums);

    	    		 
    				  rect[j] = positions.get(j).position;
    				  int page = positions.get(0).page;
        			 
        			  FieldData[fieldNums-1].positions[j].left =  rect[j].getLeft();
        			  FieldData[fieldNums-1].positions[j].top  = rect[j].getTop()+(pageNumber-page)*pdfHeight;
        			  FieldData[fieldNums-1].positions[j].width = rect[j].getWidth();
        			  FieldData[fieldNums-1].positions[j].height =  rect[j].getHeight();
        			  FieldData[fieldNums-1].positions[j].xPercentage = rect[j].getLeft() / pdfWidth;
        			  FieldData[fieldNums-1].positions[j].yPercentage = 1-(FieldData[fieldNums-1].positions[j].top / pdfTotalHeight);
        			  FieldData[fieldNums-1].positions[j].widthPercentage = rect[j].getWidth() / pdfWidth;
        			  FieldData[fieldNums-1].positions[j].heightPercentage = rect[j].getHeight() / pdfTotalHeight;
 			  } 
	  
    	}
		return FieldData;
    }
}

    