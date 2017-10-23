package zd.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import zd.pdf.PdfField.PdfFieldData;

import org.json.simple.*;

public class PdfFormUtil<PdfField> {
	
    public static final String SRC = "/home/developer/Downloads/PDFform.pdf";    // set location for source pdf 
    public static final String DEST = "/home/developer/Downloads/example.pdf";   // set location for destination pdf
    
    
    // In main function, open DEST and call manipulatePdf function.
    public static void main(String[] args) throws IOException, DocumentException {    
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PdfFormUtil().manipulatePdf(SRC, DEST);
        PdfIterator(DEST);
    }
    
  
    // manipulate pdf file
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        // set coordinates for the first rectangle (x lower left, y lower left, x upper right, y upper right)
    	int xll1 = 36;
    	int yll1 = 800;
    	int xur1 = 144;
    	int yur1 = 830;
    	
    	// set coordinates for the second rectangle
    	int xll2 = 150;
    	int yll2 = 800;
    	int xur2 = 450;
    	int yur2 = 830;
    	
    	
    	
    	
    	// Initialize rectangle1 and rectangle2
    	Rectangle rect1 = new Rectangle(xll1,yll1,xur1,yur1);
    	Rectangle rect2 = new Rectangle(xll2,yll2,xur2,yur2);
    	
    	// Initialize PdfReader reader and set source file to src
    	PdfReader reader = new PdfReader(src);
    	// Initialize stamper and set output file to dest
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        // Initialize writer
        PdfWriter writer = stamper.getWriter();
        // Initialize a hashset of string, which stores the field names of data fields
//        Set<String> FieldNames = new HashSet<String>();
//        ArrayList Coord = new ArrayList();
        HashMap<Rectangle,String> fields = new HashMap<Rectangle,String>();
        
   
//        // get current mouse position
//        Point point = MouseInfo.getPointerInfo().getLocation();
//        double x = point.getX();
//        String CoordX = Double.toString(x);
//        double y = point.getY();
//        String CoordY = Double.toString(y);
//        System.out.println("The CoordX is: "+CoordX +" ,"+"the CoodY is: "+CoordY);
         
        
        
        //initialize a pdfFormField called  personal and set field name to personal
        PdfFormField personal = PdfFormField.createEmpty(writer);
        personal.setFieldName("personal");
        // initialize the first text field
        TextField name = new TextField(writer,rect1, "name");
//        FieldNames.add(name.getFieldName());
//        Coord.add(rect1.getLeft());
//        Coord.add(rect1.getTop());
//        String x1 = Double.toString((double)rect1.getLeft());
//        String y1 = Double.toString((double)rect1.getTop());
        fields.put(rect1,name.getFieldName());
        PdfFormField personal_name = name.getTextField();
        personal.addKid(personal_name);
        
        
        
        // initialize the second text field 
        TextField password = new TextField(writer,rect2, "password");
//        FieldNames.add(password.getFieldName());
//        Coord.add(rect2.getLeft());
//        Coord.add(rect2.getTop());
//        String x2 = Double.toString((double)rect2.getLeft());
//        String y2 = Double.toString((double)rect2.getTop());
        fields.put(rect2,password.getFieldName());
        PdfFormField personal_password = password.getTextField();
        personal.addKid(personal_password);

        
        // iterate through the hashmap
        for(Entry <Rectangle,String> entry: fields.entrySet()) {
        	entry.getKey();
        	entry.getValue();
        	System.out.println("CoordX is: "+entry.getKey().getLeft()+" "+"CoordY is: "+entry.getKey().getTop()+" "+"Field name is: "+ entry.getValue());
        }
    

        // add annotation to personal
        stamper.addAnnotation(personal, 1);
        // close stamper and reader
        stamper.close();
        reader.close();
    }
    
    
    
    
    
    public static List<PdfFieldData> PdfIterator(String dest) throws IOException  {
    	String DEST = dest;
    	PdfReader reader = new PdfReader(DEST);
    	AcroFields fields = reader.getAcroFields();
    	
    	Set<String> fldNames = fields.getFields().keySet();
    	List list = new ArrayList();
    	PdfFieldData pdfFieldData = new PdfFieldData();
    	
    	Rectangle mediabox = reader.getPageSize(1);
    	float pdfWidth = mediabox.getWidth();
    	float pdfHeight = mediabox.getHeight();
    	int fieldNums =0;
    	for (String fldName : fldNames) {
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
    