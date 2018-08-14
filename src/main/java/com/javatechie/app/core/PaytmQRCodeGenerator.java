package com.javatechie.app.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

public class PaytmQRCodeGenerator {
	public static String writeQR(User user) throws WriterException, IOException {
		String imagePath = "C:\\Users\\basantkumar.h\\Desktop\\Paytm-QRCODE-DATABASE\\" + user.getName()
				+ "-QRCode.png";
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(
				"JavaTechie" + "\n" + user.getName() + "\n" + user.getBankAccount() + "\n" + user.getBankName() + "\n"
						+ user.getIFSC() + "\n" + user.getMobile() + "\n" + user.getPwd(),
				BarcodeFormat.QR_CODE, 350, 350); // width x height
		Path path = FileSystems.getDefault().getPath(imagePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		return imagePath;
	}

	public static void readQR(String qrcodeImage) throws IOException, NotFoundException {
		File QRfile = new File(qrcodeImage);

		BufferedImage bufferedImg = ImageIO.read(QRfile);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Result result = new MultiFormatReader().decode(bitmap);

		System.out.println("Barcode Format: " + result.getBarcodeFormat());
		System.out.println("Content: " + result.getText());
	}

	public static void main(String[] args) throws WriterException, IOException, NotFoundException {
		String imagePath = writeQR(
				new User("Santosh Hota", "HDFCXXXXXX2018", "8867992929", "HDFC", "HDFC00000", "Password2"));
		readQR(imagePath);
	}
}
