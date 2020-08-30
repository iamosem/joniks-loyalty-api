package com.joniks.lotalty.api.config;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.joniks.lotalty.api.constants.JLAConstants;
import com.joniks.lotalty.api.utility.DateUtility;

public class PDFGenerator extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// try {
		// Rectangle pageSize = new Rectangle(2.0f * 72, 1.0f * 72);
		// Document document = new Document(pageSize);
		// document.setMargins(10f, 10f, 5f, 5f);
		// PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Michael Encarnacion\\Desktop\\test3.pdf"));
		//
		// document.open();
		// PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
		// System.out.println("THIS IS mmdd - " + DateUtility.getFormattedDate(new Date(), JLAConstants.DATE_FORMAT_MMdd));
		// for (int counter = 1; counter <= 3; counter++) {
		// Barcode128 barcode128 = new Barcode128();
		// barcode128.setCode("0619-999999-" + String.format("%04d", counter));
		// // barcode128.setFont(null); // to remove human readable text
		// barcode128.setCodeType(Barcode128.CODE128);
		// Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
		//
		// Rectangle barcodeSize = new Rectangle(pageSize.getWidth() - 30f, pageSize.getHeight() - 30f);
		// code128Image.scaleAbsolute(barcodeSize);
		// code128Image.setAlignment(Image.ALIGN_CENTER);
		// code128Image.setWidthPercentage(100f);
		// code128Image.setAbsolutePosition(10f, pageSize.getHeight() - 55f);
		// document.add(code128Image);
		//
		// document.newPage();
		// BarcodeQRCode barcodeQrcode = new BarcodeQRCode("0619-999999-" + String.format("%04d", counter), 5, 5, null);
		// Image qrcodeImage = barcodeQrcode.getImage();
		// qrcodeImage.setPaddingTop(0f);
		// Rectangle qrSize = new Rectangle(40f, 40f);
		// qrcodeImage.scaleAbsolute(qrSize);
		// qrcodeImage.setAlignment(Image.ALIGN_CENTER);
		// float xPosition = ((pageSize.getWidth() / 2) - qrcodeImage.getWidth());
		// qrcodeImage.setAbsolutePosition(xPosition, (pageSize.getHeight() / 2) - 10f);
		// document.add(qrcodeImage);
		// placeText(pdfWriter, "0619-999999-" + String.format("%04d", counter), 20f, 10f);
		// document.newPage();
		//
		// }
		// document.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		generateBarcode("", Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }), 3, "ALL");
	}

	public static void generateBarcode(String fileName, List<String> barcodesToPrint, int dataGroupId, String barcodeToPrint) {
		try {
			Rectangle pageSize = new Rectangle(2.0f * 72, 1.0f * 72);
			Document document = new Document(pageSize);
			document.setMargins(10f, 10f, 5f, 5f);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
			String datePrefix = DateUtility.getFormattedDate(new Date(), JLAConstants.DATE_FORMAT_MMdd);
			for (String barcode : barcodesToPrint) {
				if (barcodeToPrint.equals("ALL")) {
					generateBarcodeImage(true, barcode, datePrefix, document, pdfWriter, pageSize, pdfContentByte);
					document.newPage();
					generateBarcodeImage(true, barcode, datePrefix, document, pdfWriter, pageSize, pdfContentByte);
					document.newPage();
				} else if (barcodeToPrint.equals("SPECIMEN")) {
					generateBarcodeImage(true, barcode, datePrefix, document, pdfWriter, pageSize, pdfContentByte);
					document.newPage();
				} else if (barcodeToPrint.equals("DEMOGRAPHIC")) {
					generateBarcodeImage(false, barcode, datePrefix, document, pdfWriter, pageSize, pdfContentByte);
					document.newPage();
				}
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateBarcodeImage(boolean isBarcode, String code, String prefix, Document document, PdfWriter pdfWriter, Rectangle pageSize, PdfContentByte pdfContentByte) throws Exception {
		if (isBarcode) {
			Barcode128 barcode128 = new Barcode128();
			barcode128.setCode(code);
			// barcode128.setFont(null); // to remove human readable text
			barcode128.setCodeType(Barcode128.CODE128);
			barcode128.setFont(null);
			Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);

			Rectangle barcodeSize = new Rectangle(pageSize.getWidth() - 50f, pageSize.getHeight() - 50f);
			code128Image.scaleAbsolute(barcodeSize);
			code128Image.setAlignment(Image.ALIGN_CENTER);
			code128Image.setWidthPercentage(100f);
			code128Image.setAbsolutePosition(20f, pageSize.getHeight() - 35f);
			document.add(code128Image);
			placeText(pdfWriter, prefix + "-" + code, 20f, 10f);
		} else {
			BarcodeQRCode barcodeQrcode = new BarcodeQRCode(code, 4, 4, null);
			Image qrcodeImage = barcodeQrcode.getImage();
			qrcodeImage.setPaddingTop(0f);
			Rectangle qrSize = new Rectangle(40f, 40f);
			qrcodeImage.scaleAbsolute(qrSize);
			qrcodeImage.setAlignment(Image.ALIGN_CENTER);
			float xPosition = ((pageSize.getWidth() / 2) - qrcodeImage.getWidth());
			qrcodeImage.setAbsolutePosition(xPosition, (pageSize.getHeight() / 2) - 10f);
			document.add(qrcodeImage);
			placeText(pdfWriter, prefix + "-" + code, 20f, 10f);
		}

	}

	public static Image generateBarcodeImage(boolean isBarcode, String code, PdfContentByte pdfContentByte, float width, float height) throws Exception {
		if (isBarcode) {
			Barcode128 barcode128 = new Barcode128();
			barcode128.setCode(code);
			// barcode128.setFont(null); // to remove human readable text
			barcode128.setCodeType(Barcode128.CODE128);
			barcode128.setFont(null);
			Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);

			Rectangle barcodeSize = new Rectangle(width * 72, height * 72);
			code128Image.scaleAbsolute(barcodeSize);
			code128Image.setAlignment(Image.ALIGN_CENTER);
			code128Image.setWidthPercentage(100f);
			return code128Image;
		} else {
			BarcodeQRCode barcodeQrcode = new BarcodeQRCode(code, 1, 1, null);
			Image qrcodeImage = barcodeQrcode.getImage();
			qrcodeImage.setPaddingTop(0f);
			Rectangle qrSize = new Rectangle(5f, 5f);
			qrcodeImage.scaleAbsolute(qrSize);
			qrcodeImage.setAlignment(Image.ALIGN_CENTER);
			return qrcodeImage;
		}
	}

	private static void placeText(PdfWriter pdfWriter, String text, float x, float y) throws DocumentException, IOException {
		PdfContentByte cb = pdfWriter.getDirectContent();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		cb.saveState();
		cb.beginText();
		cb.moveText(x, y);
		cb.setFontAndSize(bf, 12);
		cb.showText(text);
		cb.endText();
		cb.restoreState();
	}

	public static void generateBarcode(String fileName, String qrCodeValue) {
		try {
			BarcodeQRCode barcodeQrcode = new BarcodeQRCode(qrCodeValue, 500, 500, null);
			java.awt.Image awtImage = barcodeQrcode.createAwtImage(Color.BLACK, Color.WHITE);

			BufferedImage bImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bImage.createGraphics();
			g.drawImage(awtImage, 0, 0, null);
			g.dispose();

			ImageIO.write(bImage, "png", new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}