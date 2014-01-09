package uk.co.kyleharrison.pim.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

/**
 * 
 * @author Kyle Harrison
 * @source http://java.dzone.com/articles/java-barcode-api
 * Build jars with mvn 
 */
public class HelloWorld {

	public static void main(String[] args) {

		//generateBarcode("Hello World!","C:/Users/apoclyps/workspace/Java/Zxing-Test/img/hello.jpg");
		readBarcode();
	}
	
	public static void readBarcode(){
		InputStream barCodeInputStream = null;
		try {
			barCodeInputStream = new FileInputStream("C:/Users/apoclyps/workspace/Java/Zxing-Test/img/hello.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage barCodeBufferedImage = null;
		try {
			barCodeBufferedImage = ImageIO.read(barCodeInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		Result result = null;
		try {
			result = reader.decode(bitmap);
		} catch (NotFoundException | ChecksumException | FormatException e) {
			e.printStackTrace();
		}

		System.out.println("Barcode text is " + result.getText());
	}
	
	public static void generateBarcode(String encodeString,String outputDirectory){
		int width = 440;
		int height = 48;

		BitMatrix bitMatrix;
		try {
			bitMatrix = new Code128Writer().encode(encodeString, BarcodeFormat.CODE_128, width, height, null);
			MatrixToImageWriter.writeToStream(bitMatrix,"jpg",new FileOutputStream(new File(outputDirectory)));
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Barcode Generated : "+encodeString);
		System.out.println("Barcode Location  : "+outputDirectory);
	}

}