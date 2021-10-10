/**
 * Класс реализующий создание и заполнение типового PDF файла.
 * @author Kazantsev
 * @version 1.0
 * В классе один конструктор с 4 параметрами.
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

BaseFont times = null;
String name1,name2,name3,name4;

/**
 * Конструктор включает 4 параметра.
 * @param name1 - значение ячейки первого столбца
 * @param name2 - значение ячейки второго столбца
 * @param name3 - значение ячейки третьего столбца
 * @param name4 - значение ячейки четвертого столбца
 * 
 * @exception Шапка имеет стандартное заполнение
 * 
 * ОДНОСТРОЧНЫЕ КОММЕНТАРИИ в конструкторе дают понимание, что делают те или иные строчки кода.
 * 
 */

	public CreatePDF(String name1, String name2, String name3, String name4) { 
		this.name1=name1;
		this.name2=name2;
		this.name3=name3;
		this.name4=name4;
		
		Document document = new Document(); //создание объекта Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Check.pdf")); //выходной поток для создания PDF, а внутри создается поток записи с конкретным именем
		} catch (FileNotFoundException | DocumentException e) { //Исключение когда файл не найден
			e.printStackTrace();
		}
			
		document.open(); //открытие для возможности записи
		
		
		try {
			times = BaseFont.createFont("/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		
		String string_pdf = "Добрый день!";
		Paragraph paragraph = new Paragraph(); //создание объекта "параграф" для возможности записи данных в файл
	    paragraph.add(new Paragraph(string_pdf, new Font(times,14)));
	    
	    String string_pdf2 = "Дополнительный текст, который выводится в PDF. При этом нужно понимать, что можно указывать значения, которые будут выводится в файл PDF.";
	    paragraph.add(new Paragraph(string_pdf2, new Font(times,14)));
	
	    try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	    	
	    
	    //добавление изображения в pdf
	    URL url = getClass().getResource("/picture/ugatu.png");
	    Image img = null;
		try {
			img = Image.getInstance(url.toString());
			
			
		} catch (BadElementException e2) {
			
			e2.printStackTrace();
		} catch (MalformedURLException e2) {
			
			e2.printStackTrace();
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		
		img.setAbsolutePosition(90, 500); //позиционирование изображения в PDF
		
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
		
		 //ниже дублирование кода (это можно устранить)
        try {
			times = BaseFont.createFont("/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
      //установка значения и шрифта для выводимого текста в ячейки
        
		
		table.addCell(new Phrase(name1, new Font(times,14)));
	    table.addCell(new Phrase(name2, new Font(times,14)));;
	    table.addCell(new Phrase(name3, new Font(times,14)));
	    table.addCell(new Phrase(name4, new Font(times,14)));
		
	    //выше должен быть текст на русском языке, как его вывести можно посмотреть в справке.
	}

	private void addHeader(PdfPTable table) { //метод для работы с шапкой таблицы
		Stream.of("Номер", "Группа", "ФИО", "Оценка") //поток с названиями для шапки
	      .forEach(columnTitle -> { //в цикле для всех данных в потоке выше создаем ячейки, заносим названия и устанавливаем свойства ячейки 
	        PdfPCell header = new PdfPCell(); //реализация ячейки в таблице
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        //ниже дублирование кода (это можно устранить)
	        try {
				times = BaseFont.createFont("/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			//установка значения и шрифта для выводимого текста в ячейки	        
	        header.setPhrase(new Phrase(columnTitle,new Font(times,14)));
	        table.addCell(header); 
	    });
		
	}
	
}
