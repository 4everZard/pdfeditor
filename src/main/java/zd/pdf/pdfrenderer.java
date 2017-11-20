package zd.pdf;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class pdfrenderer {
    public static void PdfConverter(String source, String destination) throws InvalidPasswordException, IOException{
        try {
//        String sourceDir = "/home/developer/eclipse-workspace/pdfeditor/src/main/resources/pdfForm.pdf";// PDF file must be placed in DataGet folder
//        String destinationDir = "/home/developer/eclipse-workspace/pdfeditor/src/main/resources/";//Converted PDF page saved in this folder

        File sourceFile = new File(fileClass.sourcePdf);
        File destinationFile = new File(fileClass.destinationDir);
        
        String fileName = sourceFile.getName().replace(".pdf", "");
        if (sourceFile.exists()) {
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Folder created in: "+ destinationFile.getCanonicalPath());
            }

            RandomAccessFile raf = new RandomAccessFile(sourceFile, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdf = new PDFFile(buf);
            
//            int pageNumber = 1;// which PDF page to be convert
//            PDFPage page = pdf.getPage(pageNumber);
            int pageNumber;
            PDFPage page = null;
            pageNumber = pdf.getNumPages();
        	// image dimnsions 
            int width = 1200;
            int height = 1400;
            int totalHeight = height * pageNumber;
            //edit
            
            BufferedImage images[] = new BufferedImage[pageNumber];
          
            int heightCurr = 0;
            
            
            BufferedImage concatImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
            
            //
            for(int i=1;i<=pageNumber;i++) {
            	page = pdf.getPage(i);
                // create the image
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
                //BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);               
                //edit              
                Image image = page.getImage(width, height, rect, null, true, true );
                images[i-1] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = images[i-1].createGraphics();
                g2d.drawImage(image, 0, 0, null);
                Graphics2D concat = concatImage.createGraphics();
                concat.drawImage(images[i-1],0, heightCurr,null);
                heightCurr += images[i-1].getHeight();
                File imageFile = new File( fileClass.destinationDir + fileName +".jpg" );// change file format here. Ex: .jpg, .jpg, .jpeg, .gif, .bmp
                System.out.println(imageFile.getName() +" File created in: "+ destinationFile.getCanonicalPath());       
            }
        } else {
            System.err.println(sourceFile.getName() +" File not exists");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


































