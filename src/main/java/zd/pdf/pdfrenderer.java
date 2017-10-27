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
        File destinationFile = new File(fileClass.destinationImg);
        
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
        	// image dimensions 
            int width = 1200;
            int height = 1400;
            
            for(int i=1;i<=pageNumber;i++) {
            	page = pdf.getPage(i);
            

                // create the image
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                // width & height, // clip rect, // null for the ImageObserver, // fill background with white, // block until drawing is done
                Image image = page.getImage(width, height, rect, null, true, true );
                Graphics2D bufImageGraphics = bufferedImage.createGraphics();
                bufImageGraphics.drawImage(image, 0, 0, null);

                File imageFile = new File( fileClass.destinationDir + fileName +"_"+ i +".jpg" );// change file format here. Ex: .jpg, .jpg, .jpeg, .gif, .bmp

                ImageIO.write(bufferedImage, "jpg", imageFile);

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
