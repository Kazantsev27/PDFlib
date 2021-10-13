/**
 *  ласс реализующий создание и заполнение типового PDF файла.
 * @author Kazantsev
 * @version 1.0
 * ¬ классе один конструктор с 4 параметрами.
 * 
 * –еализовано два метода дл€ добавлени€ шапки таблицы и строк таблицы (с заполнением данными).
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
 *  онструктор включает 4 параметра.
 * @param name1 - значение €чейки первого столбца
 * @param name2 - значение €чейки второго столбца
 * @param name3 - значение €чейки третьего столбца
 * @param name4 - значение €чейки четвертого столбца
 * @param arrayHat - массив со значени€ми дл€ шапки таблицы
 * @param Texthat - текст дл€ шапки страницы
 * @param Textgeneral - общий текст на странице
 * @param Imagelink - ссылка на рисунок
 * @param Namefile - им€ выводимого файла
 * @param BaseFont - шрифт дл€ вывода
 * 
 * 
 * ќƒЌќ—“–ќ„Ќџ≈  ќћћ≈Ќ“ј–»» в конструкторе дают понимание, что делают те или иные строчки кода.
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
			PdfWriter.getInstance(document, new FileOutputStream(this.Namefile)); //выходной поток дл€ создани€ PDF, а внутри создаетс€ поток записи с конкретным именем
		} catch (FileNotFoundException | DocumentException e) { //»сключение когда файл не найден
			e.printStackTrace();
		}
			
		document.open(); //открытие дл€ возможности записи
		
		String string_pdf = Texthat;
		Paragraph paragraph = new Paragraph(); //создание объекта "параграф" дл€ возможности записи данных в файл
	    paragraph.add(new Paragraph(string_pdf, new Font(times,14)));
	    
	    String string_pdf2 =Textgeneral;
	    paragraph.add(new Paragraph(string_pdf2, new Font(times,14)));
	
	    try {
			document.add(paragraph);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	    	
	    //добавление изображени€ в pdf
	    URL url = Imagelink;
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
		
		img.setAbsolutePosition(90, 500); //позиционирование изображени€ в PDF
		
		try {
				document.add(img);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	    
		 //организаци€ перехода на следующую строку
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
      //установка значени€ и шрифта дл€ выводимого текста в €чейки
        
		table.addCell(new Phrase(name1, new Font(times,14)));
	    table.addCell(new Phrase(name2, new Font(times,14)));;
	    table.addCell(new Phrase(name3, new Font(times,14)));
	    table.addCell(new Phrase(name4, new Font(times,14)));
	}

	private void addHeader(PdfPTable table) { //метод дл€ работы с шапкой таблицы
		Stream.of(arrayHat[0], arrayHat[1], arrayHat[2], arrayHat[3]) //поток с названи€ми дл€ шапки
	      .forEach(columnTitle -> { //в цикле дл€ всех данных в потоке выше создаем €чейки, заносим названи€ и устанавливаем свойства €чейки 
	        PdfPCell header = new PdfPCell(); //реализаци€ €чейки в таблице
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);

			//установка значени€ и шрифта дл€ выводимого текста в €чейки	        
	        header.setPhrase(new Phrase(columnTitle,new Font(times,14)));
	        table.addCell(header); 
	    });
		
	}
	
}
