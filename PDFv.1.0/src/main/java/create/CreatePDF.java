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
 * <b> Класс реализующий создание и заполнение типового PDF файла.</b>
 * @author Kazantsev AV
 * @version 2.0
 * В классе один конструктор c 4 параметрами и 5 методами.
 * Класс основан на библиотеки itextpdf.
 * 
 * Для создания PDF файла необходимо создать экземпляр объекта CreatePDF и далее добавлять необходимые вставки (текст, рисунки и таблицы). После окончания добавления необходимо выполнить метод getClose, который закроет и сохранить PDF документ.
 * 
 */

public class CreatePDF {

/** Поле базового используемого шрифта */
private BaseFont times = null;
/** Поле с именем создаваемого PDF файла */
private String Namefile;
/** Поле со значениями шапки таблицы*/
private String[] arrayHat, NameCellHat;
/** Поле с сслыкой на создаваеый документ */
private Document document;
/** Поле с количеством столбцов в таблице */
private int Size;

/**
 * Конструктор - создание объекта с генерацией PDF
 * @param NameCellHat - массив с названиями в шапке таблицы
 * @param arrayHat - массив со значениями для шапки таблицы
 * @param Namefile - имя выводимого файла
 * @param BaseFontPDF - шрифт для вывода
 */
	public CreatePDF(String[] NameCellHat, String[] arrayHat, String Namefile, BaseFont BaseFontPDF) { 
		this.NameCellHat=NameCellHat;
		this.arrayHat=arrayHat;
		this.Namefile=Namefile;
		this.times=BaseFontPDF;
		this.Size=NameCellHat.length;
		this.document = new Document(); //создание объекта Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //выходной поток для создания PDF, а внутри создается поток записи с конкретным именем
		} catch (FileNotFoundException | DocumentException e) { //Исключение когда файл не найден
			e.printStackTrace();
		}
			
		document.open(); //открытие для возможности записи

	}

	/** 
	 * Метод добавления строк в таблицу {@link CreatePDF}
	 * @param table - таблица для заполнения
	 */
	
private void addRows(PdfPTable table) {
      //установка значения и шрифта для выводимого текста в ячейки
        
	for (int i=0; i<Size; i++) {
		table.addCell(new Phrase(arrayHat[i], new Font(times,14)));
	}
	}

	/**
	 * Метод заполнения шапки таблицы {@link CreatePDF}
	 * @param table - таблица для заполнения
	 */
	
private void setHeader(PdfPTable table) { //метод для работы с шапкой таблицы
	    	for (int i=0; i<Size; i++) {  
	        PdfPCell header = new PdfPCell(); //реализация ячейки в таблице
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
			//установка значения и шрифта для выводимого текста в ячейки	        
	        header.setPhrase(new Phrase(NameCellHat[i],new Font(times,14)));
	        table.addCell(header); 
	    }
	}
	
/**
 * Метод для получения ссылка на создаваемый документ {@link CreatePDF}
 * @return возваращет ссылку на создаваемый документ
 */
	
public Document getDocument() {
	return this.document;
}

/**
 * Метод для закрытия и сохранения PDF файла {@link CreatePDF}
 */

public void getClose() {
	this.document.close();
}

/**
 * Метод добавления картинки в PDF файл {@link CreatePDF}
 * @param url - ссылка на изображение
 * @param document - ссылка на создаваемый документ
 * @param position1 - абсолютная позиция по оси X
 * @param position2 - абсолютная позиция по оси Y
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
	img.setAbsolutePosition(position1, position2); //позиционирование изображения в PDF
	
	try {
			document.add(img);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
}

/**
 * Метод добавления текста в PDF документ. Добавление происходит с начала документа.
 * @param document - ссылка на создаваемый документ
 * @param Text - задаваемый текст
 * @param SizeFont - размер шрифта
 * @param Space - указание требуется ли перейти на новую строку
 */

public void addText(Document document, String Text, int SizeFont, boolean Space ) {
	Paragraph paragraph = new Paragraph(); //создание объекта "параграф" для возможности записи данных в файл
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
 * Метод добавления таблицы в PDF документ.
 * @param document - ссылка на создаваемый документ
 */

public void addTable (Document document) {
	
	PdfPTable table = new PdfPTable(Size);
	setHeader(table); //задание заголовка (шапки таблицы)
	addRows(table); // добавление строк
	 
	 try {
		document.add(table);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	
}

}
