/* This library is built in order to render pdf file to image without resolution loss 
 * using sun.pdfview, and Graphics2D.  
 *
 */
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
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PdfRenderer {
    public static void PdfConverter(String source, String destination) throws InvalidPasswordException, IOException{
        File sourceFile = new File(FileClass.sourcePdf);
        File destinationFile = new File(FileClass.destinationDir);
        
        String fileName = sourceFile.getName().replace(".pdf", "");// get the name of the pdf file without ".pdf"
        if (sourceFile.exists()) {
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Folder created in: "+ destinationFile.getCanonicalPath());
            }
            
            RandomAccessFile raf = new RandomAccessFile(sourceFile, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdf = new PDFFile(buf);
            /*the number of pages in this pdf file*/
            int pageNumber;
            PDFPage page = null;
            pageNumber = pdf.getNumPages();
            /*image dimensions */
            int width = 1200;
            int height = 1400;
            int totalHeight = height * pageNumber;
            int heightCurr = 0;
            /*image buffer*/
            BufferedImage images[] = new BufferedImage[pageNumber]; // create an array of buffered images, the capacity is the page number
            BufferedImage concatImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB); // create a concatenated image buffer
            
            
            for(int i=1;i<=pageNumber;i++) {
            	page = pdf.getPage(i);
                /*create a rectangle which left-top coordinate locates in (0,0), and have the same width and height with the corresponding image*/
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());            
                Image image = page.getImage(width, height, rect, null, true, true );
                images[i-1] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = images[i-1].createGraphics();
                g2d.drawImage(image, 0, 0, null);
                Graphics2D concat = concatImage.createGraphics();
                concat.drawImage(images[i-1],0, heightCurr,null);
                heightCurr += images[i-1].getHeight();
                File imageFile = new File( FileClass.destinationDir + fileName +".jpg" );// change file format here. Ex: .jpg, .jpg, .jpeg, .gif, .bmp
                //System.out.println(imageFile.getName() +" File created in: "+ destinationFile.getCanonicalPath());       
            }
        } else {
            System.err.println(sourceFile.getName() +" File not exists");   // if no such pdf exists, then print "File not exists"
        }
   
}
}


