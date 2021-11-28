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

/**
 * <b> ����� ����������� �������� � ���������� �������� PDF �����.</b>
 * @author Kazantsev AV
 * @version 2.0
 * � ������ ���� ����������� c 4 ����������� � 5 ��������.
 * ����� ������� �� ���������� itextpdf.
 * 
 * ��� �������� PDF ����� ���������� ������� ��������� ������� CreatePDF � ����� ��������� ����������� ������� (�����, ������� � �������). ����� ��������� ���������� ���������� ��������� ����� getClose, ������� ������� � ��������� PDF ��������.
 * 
 */

public class CreatePDF {

/** ���� �������� ������������� ������ */
private BaseFont times = null;
/** ���� � ������ ������������ PDF ����� */
private String Namefile;
/** ���� �� ���������� ����� �������*/
private String[] arrayHat, NameCellHat;
/** ���� � ������� �� ���������� �������� */
private Document document;
/** ���� � ����������� �������� � ������� */
private int Size;

/**
 * ����������� - �������� ������� � ���������� PDF
 * @param NameCellHat - ������ � ���������� � ����� �������
 * @param arrayHat - ������ �� ���������� ��� ����� �������
 * @param Namefile - ��� ���������� �����
 * @param BaseFontPDF - ����� ��� ������
 */
	public CreatePDF(String[] NameCellHat, String[] arrayHat, String Namefile, BaseFont BaseFontPDF) { 
		this.NameCellHat=NameCellHat;
		this.arrayHat=arrayHat;
		this.Namefile=Namefile;
		this.times=BaseFontPDF;
		this.Size=NameCellHat.length;
		this.document = new Document(); //�������� ������� Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //�������� ����� ��� �������� PDF, � ������ ��������� ����� ������ � ���������� ������
		} catch (FileNotFoundException | DocumentException e) { //���������� ����� ���� �� ������
			e.printStackTrace();
		}
			
		document.open(); //�������� ��� ����������� ������

	}

	/** 
	 * ����� ���������� ����� � ������� {@link CreatePDF}
	 * @param table - ������� ��� ����������
	 */
	
private void addRows(PdfPTable table) {
      //��������� �������� � ������ ��� ���������� ������ � ������
        
	for (int i=0; i<Size; i++) {
		table.addCell(new Phrase(arrayHat[i], new Font(times,14)));
	}
	}

	/**
	 * ����� ���������� ����� ������� {@link CreatePDF}
	 * @param table - ������� ��� ����������
	 */
	
private void setHeader(PdfPTable table) { //����� ��� ������ � ������ �������
	    	for (int i=0; i<Size; i++) {  
	        PdfPCell header = new PdfPCell(); //���������� ������ � �������
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
			//��������� �������� � ������ ��� ���������� ������ � ������	        
	        header.setPhrase(new Phrase(NameCellHat[i],new Font(times,14)));
	        table.addCell(header); 
	    }
	}
	
/**
 * ����� ��� ��������� ������ �� ����������� �������� {@link CreatePDF}
 * @return ���������� ������ �� ����������� ��������
 */
	
public Document getDocument() {
	return this.document;
}

/**
 * ����� ��� �������� � ���������� PDF ����� {@link CreatePDF}
 */

public void getClose() {
	this.document.close();
}

/**
 * ����� ���������� �������� � PDF ���� {@link CreatePDF}
 * @param url - ������ �� �����������
 * @param document - ������ �� ����������� ��������
 * @param position1 - ���������� ������� �� ��� X
 * @param position2 - ���������� ������� �� ��� Y
 */

public void addPicture(URL url, Document document, int position1, int position2) {
    Image img = null;
		try {
			img = Image.getInstance(url.toString());
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	img.setAbsolutePosition(position1, position2); //���������������� ����������� � PDF
	
	try {
			document.add(img);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
}

/**
 * ����� ���������� ������ � PDF ��������. ���������� ���������� � ������ ���������.
 * @param document - ������ �� ����������� ��������
 * @param Text - ���������� �����
 * @param SizeFont - ������ ������
 * @param Space - �������� ��������� �� ������� �� ����� ������
 */

public void addText(Document document, String Text, int SizeFont, boolean Space ) {
	Paragraph paragraph = new Paragraph(); //�������� ������� "��������" ��� ����������� ������ ������ � ����
    paragraph.add(new Paragraph(Text, new Font(times,SizeFont)));
    
    try {
		document.add(paragraph);
	} catch (DocumentException e1) {
		e1.printStackTrace();
	}
    paragraph.clear();
    
    if (Space) {
    
    String string_pdf3 = " ";
	 paragraph.add(new Paragraph(string_pdf3, new Font(times,14)));
	 
	 try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
    }
    
    paragraph=null;
}

/**
 * ����� ���������� ������� � PDF ��������.
 * @param document - ������ �� ����������� ��������
 */

public void addTable (Document document) {
	
	PdfPTable table = new PdfPTable(Size);
	setHeader(table); //������� ��������� (����� �������)
	addRows(table); // ���������� �����
	 
	 try {
		document.add(table);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	
}

}
