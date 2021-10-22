/**
 * ����� ����������� �������� � ���������� �������� PDF �����.
 * @author Kazantsev
 * @version 1.0
 * � ������ ���� ����������� � 10 �����������.
 * 
 * ����������� ��� ������ ��� ���������� ����� ������� � ����� ������� (� ����������� �������).
 * 
 */

package create;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePDF {

private BaseFont times = null;
private String name1,name2,name3,name4, Namefile;
private String[] arrayHat;

/**
 * ����������� �������� 10 ����������.
 * @param name1 - �������� ������ ������� �������
 * @param name2 - �������� ������ ������� �������
 * @param name3 - �������� ������ �������� �������
 * @param name4 - �������� ������ ���������� �������
 * @param arrayHat - ������ �� ���������� ��� ����� �������
 * @param Texthat - ����� ��� ����� ��������
 * @param Textgeneral - ����� ����� �� ��������
 * @param Imagelink - ������ �� �������
 * @param Namefile - ��� ���������� �����
 * @param BaseFont - ����� ��� ������
 * 
 * 
 * ������������ ����������� � ������������ ���� ���������, ��� ������ �� ��� ���� ������� ����.
 * 
 * � ����� �� ��������� ���������� ����� ���������� ��������� ���������, � ��������� ��������� ����� ���������.
 * ����� ��������� ����� �������������� ����������, ������� ���������� �� ���������������� ������, ������ � ����������� � ����������� PDF �����.
 * 
 */
	public CreatePDF(String name1, String name2, String name3, String name4, String[] arrayHat, String Texthat, String Textgeneral, URL Imagelink, String Namefile, BaseFont BaseFontPDF) { 
		this.name1=name1;
		this.name2=name2;
		this.name3=name3;
		this.name4=name4;
		this.arrayHat=arrayHat;
		this.Namefile=Namefile;
		this.times=BaseFontPDF;
				
		Document document = new Document(); //�������� ������� Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //�������� ����� ��� �������� PDF, � ������ ��������� ����� ������ � ���������� ������
		} catch (FileNotFoundException | DocumentException e) { //���������� ����� ���� �� ������
			e.printStackTrace();
		}
			
		document.open(); //�������� ��� ����������� ������
		
		String string_pdf = Texthat;
		Paragraph paragraph = new Paragraph(); //�������� ������� "��������" ��� ����������� ������ ������ � ����
	    paragraph.add(new Paragraph(string_pdf, new Font(times,14)));
	    
	    String string_pdf2 =Textgeneral;
	    paragraph.add(new Paragraph(string_pdf2, new Font(times,14)));
	
	    try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	    	
	    //���������� ����������� � pdf
	    Image img = addPicture(Imagelink);
		
		try {
				document.add(img);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	    
		 //����������� �������� �� ��������� ������
		 paragraph.clear();
		 String string_pdf3 = " ";
		 paragraph.add(new Paragraph(string_pdf3, new Font(times,14)));
		 
		 try {
				document.add(paragraph);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}

	    //���������� �������
		 PdfPTable table = new PdfPTable(4); //�������� ������� � 4 ���������
		 addHeader(table); //���������� ��������� (����� �������)
		 addRows(table); // ���������� �����
		 
		 try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close(); //�������� � ���������� ���������
	}

	private void addRows(PdfPTable table) {
      //��������� �������� � ������ ��� ���������� ������ � ������
        
		table.addCell(new Phrase(name1, new Font(times,14)));
	    table.addCell(new Phrase(name2, new Font(times,14)));;
	    table.addCell(new Phrase(name3, new Font(times,14)));
	    table.addCell(new Phrase(name4, new Font(times,14)));
	}

	private void addHeader(PdfPTable table) { //����� ��� ������ � ������ �������
		Stream.of(arrayHat[0], arrayHat[1], arrayHat[2], arrayHat[3]) //����� � ���������� ��� �����
	      .forEach(columnTitle -> { //� ����� ��� ���� ������ � ������ ���� ������� ������, ������� �������� � ������������� �������� ������ 
	        PdfPCell header = new PdfPCell(); //���������� ������ � �������
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);

			//��������� �������� � ������ ��� ���������� ������ � ������	        
	        header.setPhrase(new Phrase(columnTitle,new Font(times,14)));
	        table.addCell(header); 
	    });
		
	}
	
	private Image addPicture(URL url) {
		//���������� ����������� � pdf
	    
	    Image img = null;

			try {
				img = Image.getInstance(url.toString());
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		img.setAbsolutePosition(90, 500); //���������������� ����������� � PDF
		return img;

	}
	
}
