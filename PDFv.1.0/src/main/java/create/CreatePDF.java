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
 * <b> Класс реализующий создание и заполнение типового PDF файла. </b>
 * @author Kazantsev AV
 * @version 1.8
 * В классе один конструктор с 9 параметрами.
 * Библиотека основана на itextpdf.
 * 
 * В одном из следующих обновлений будут объединены некоторые параметры, и добавлены некоторые новые параметры.
 * Также будут реализованы некоторые дополнительные методы для удобства работы.
 * Новые параметры будут представляться структурой, которая направлена на позиционирование текста, таблиц и изображений в создаваемом PDF файле.
 * 
 */

public class CreatePDF {

/** Поле базового используемого шрифта */
private BaseFont times = null;
/** Поля значений столбцов в таблице в PDF файле */
private String name1,name2,name3,name4, Namefile;
/** Поле со значениями шапки таблицы*/
private String[] arrayHat;

private Document document;

/**
 * Конструктор - создание объекта с генерацией PDF
 * @param name1 - значение ячейки первого столбца
 * @param name2 - значение ячейки второго столбца
 * @param name3 - значение ячейки третьего столбца
 * @param name4 - значение ячейки четвертого столбца
 * @param arrayHat - массив со значениями для шапки таблицы
 * @param Texthat - текст для шапки страницы
 * @param Textgeneral - общий текст на странице
 * @param Namefile - имя выводимого файла
 * @param BaseFontPDF - шрифт для вывода
 */
	public CreatePDF(String name1, String name2, String name3, String name4, String[] arrayHat, String Texthat, String Textgeneral, String Namefile, BaseFont BaseFontPDF) { 
		this.name1=name1;
		this.name2=name2;
		this.name3=name3;
		this.name4=name4;
		this.arrayHat=arrayHat;
		this.Namefile=Namefile;
		this.times=BaseFontPDF;
				
		document = new Document(); //создание объекта Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //выходной поток для создания PDF, а внутри создается поток записи с конкретным именем
		} catch (FileNotFoundException | DocumentException e) { //Исключение когда файл не найден
			e.printStackTrace();
		}
			
		document.open(); //открытие для возможности записи
		
		Paragraph paragraph = new Paragraph(); //создание объекта "параграф" для возможности записи данных в файл
	    paragraph.add(new Paragraph(Texthat, new Font(times,20)));
	    
	    paragraph.add(new Paragraph(Textgeneral, new Font(times,14)));
	
	    try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		 //организация перехода на следующую строку
		 paragraph.clear();
		 String string_pdf3 = " ";
		 paragraph.add(new Paragraph(string_pdf3, new Font(times,14)));
		 
		 try {
				document.add(paragraph);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}

	    //добавление таблицы
		 PdfPTable table = new PdfPTable(4); //создание таблицы с 4 столбцами
		 setHeader(table); //задание заголовка (шапки таблицы)
		 addRows(table); // добавление строк
		 
		 try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//document.close(); //закрытие и сохранение документа
	}

	/** 
	 * Метод добавления строк в таблицу {@link CreatePDF}
	 * @param table - таблица для заполнения
	 */
	
private void addRows(PdfPTable table) {
      //установка значения и шрифта для выводимого текста в ячейки
        
		table.addCell(new Phrase(name1, new Font(times,14)));
	    table.addCell(new Phrase(name2, new Font(times,14)));;
	    table.addCell(new Phrase(name3, new Font(times,14)));
	    table.addCell(new Phrase(name4, new Font(times,14)));
	}

	/**
	 * Метод заполнения шапки таблицы {@link CreatePDF}
	 * @param table - таблица для заполнения
	 */
	
private void setHeader(PdfPTable table) { //метод для работы с шапкой таблицы
		Stream.of(arrayHat[0], arrayHat[1], arrayHat[2], arrayHat[3]) //поток с названиями для шапки
	      .forEach(columnTitle -> { //в цикле для всех данных в потоке выше создаем ячейки, заносим названия и устанавливаем свойства ячейки 
	        PdfPCell header = new PdfPCell(); //реализация ячейки в таблице
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
			//установка значения и шрифта для выводимого текста в ячейки	        
	        header.setPhrase(new Phrase(columnTitle,new Font(times,14)));
	        table.addCell(header); 
	    });
	}
	
/**
 * Метод для получения ссылка на создаваемый документ.
 * @return возваращет ссылку на создаваемый документ
 */
	
public Document getDocument() {
	return this.document;
}

/**
 * Метод для закрытия и сохранения PDF файла.
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
	//добавление изображения в pdf
	
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

}
