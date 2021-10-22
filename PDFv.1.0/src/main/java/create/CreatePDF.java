/**
 * Класс реализующий создание и заполнение типового PDF файла.
 * @author Kazantsev
 * @version 1.0
 * В классе один конструктор с 10 параметрами.
 * 
 * Реализовано два метода для добавления шапки таблицы и строк таблицы (с заполнением данными).
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
 * Конструктор включает 10 параметров.
 * @param name1 - значение ячейки первого столбца
 * @param name2 - значение ячейки второго столбца
 * @param name3 - значение ячейки третьего столбца
 * @param name4 - значение ячейки четвертого столбца
 * @param arrayHat - массив со значениями для шапки таблицы
 * @param Texthat - текст для шапки страницы
 * @param Textgeneral - общий текст на странице
 * @param Imagelink - ссылка на рисунок
 * @param Namefile - имя выводимого файла
 * @param BaseFont - шрифт для вывода
 * 
 * 
 * ОДНОСТРОЧНЫЕ КОММЕНТАРИИ в конструкторе дают понимание, что делают те или иные строчки кода.
 * 
 * В одном из следующих обновлений будут объединены некоторые параметры, и добавлены некоторые новые параметры.
 * Новые параметры будут представляться структурой, которая направлена на позиционирование текста, таблиц и изображений в создаваемом PDF файле.
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
				
		Document document = new Document(); //создание объекта Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //выходной поток для создания PDF, а внутри создается поток записи с конкретным именем
		} catch (FileNotFoundException | DocumentException e) { //Исключение когда файл не найден
			e.printStackTrace();
		}
			
		document.open(); //открытие для возможности записи
		
		String string_pdf = Texthat;
		Paragraph paragraph = new Paragraph(); //создание объекта "параграф" для возможности записи данных в файл
	    paragraph.add(new Paragraph(string_pdf, new Font(times,14)));
	    
	    String string_pdf2 =Textgeneral;
	    paragraph.add(new Paragraph(string_pdf2, new Font(times,14)));
	
	    try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	    	
	    //добавление изображения в pdf
	    Image img = addPicture(Imagelink);
		
		try {
				document.add(img);
			} catch (DocumentException e) {
				e.printStackTrace();
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
		 addHeader(table); //добавление заголовка (шапки таблицы)
		 addRows(table); // добавление строк
		 
		 try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close(); //закрытие и сохранение документа
	}

	private void addRows(PdfPTable table) {
      //установка значения и шрифта для выводимого текста в ячейки
        
		table.addCell(new Phrase(name1, new Font(times,14)));
	    table.addCell(new Phrase(name2, new Font(times,14)));;
	    table.addCell(new Phrase(name3, new Font(times,14)));
	    table.addCell(new Phrase(name4, new Font(times,14)));
	}

	private void addHeader(PdfPTable table) { //метод для работы с шапкой таблицы
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
	
	private Image addPicture(URL url) {
		//добавление изображения в pdf
	    
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
		
		
		img.setAbsolutePosition(90, 500); //позиционирование изображения в PDF
		return img;

	}
	
}
