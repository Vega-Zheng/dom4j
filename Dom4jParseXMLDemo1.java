package dom4jDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jParseXMLDemo1 {
	Document document;

	public void read(String path) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void showInfo(String path) {
		Element root = document.getRootElement();
		Iterator it = root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			System.out.println("category: " + element.attributeValue("category"));
			System.out.println("title: " + element.elementText("title"));
			System.out.println("lang: " + element.element("title").attributeValue("lang"));
			System.out.println("author: " + element.elementText("author"));
			System.out.println("year: " + element.elementText("year"));
			System.out.println("price: " + element.elementText("price"));
			System.out.println();
		}
	}

	public void save(String path) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(path)), format);
			writer.write(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void addInfo(String path) {
		Element root = document.getRootElement();
		Element newBook = root.addElement("book");
		newBook.addAttribute("category", "JAVA");
		Element newTitle = newBook.addElement("title");
		newTitle.addAttribute("lang", "en");
		newTitle.setText("Java编程");
		Element newAuthor = newBook.addElement("author");
		newAuthor.setText("张三");
		Element newPrice = newBook.addElement("price");
		newPrice.setText("55");
		Element newYear = newBook.addElement("year");
		newYear.setText("2020");

		save(path);

	}

	public void delInfo(String path) {
		//获取根节点
		Element root = document.getRootElement();
		//找到要删除的父节点位置
		Element element = root.elements("book").get(3);
		//找到要删除的子节点
		Element del = element.element("good");
		element.remove(del);
		
		save(path);

	}

	public void updateInfo(String path) {
		// 获取根节点
		Element root = document.getRootElement();
		// 选则待添加属性节点
		Element element = root.elements("book").get(3);
		// 添加属性
		element.addElement("good").setText("好");
		save(path);
	}
	
	

	public static void main(String[] args) {
		String path = "books.xml";
		Dom4jParseXMLDemo1 demo = new Dom4jParseXMLDemo1();
		demo.read(path);
		// demo.addInfo(path);
		//demo.updateInfo(path);
		//demo.delInfo(path);
		demo.showInfo(path);

	}

}
