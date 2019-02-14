package com.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

public class JsonPathParseDemo {
	
	private static final String JSON_PATH = "src\\main\\resources\\book.json";

	public static void main(String[] args) {
		jsonPath();
	}

	/**
	 * ��ȡJsonPath
	 */
	private static void jsonPath() {
		
		String json = jsonData();

		// ���book[0]��authorֵ
		String author = JsonPath.read(json, "$.store.book[0].author");
		print(author);

		// ���ȫ��author��ֵ��ʹ��Iterator����
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		list(authors);

		// ���book[*]��category == 'reference'��book
		List<Object> books = JsonPath.read(json, "$.store.book[?(@.category == 'reference')]");
		listObj(books);
		
		// ���book[*]��price>10��book 
		books = JsonPath.read(json, "$.store.book[?(@.price>10)]");
		listObj(books);
		
		// ���book[*]�к���isbnԪ�ص�book
		books = JsonPath.read(json, "$.store.book[?(@.isbn)]");
		listObj(books);
		
		// �����json������price��ֵ 
		List<Double> prices = JsonPath.read(json, "$..price");
		for (int i = 0; i < prices.size(); i++) {
			System.out.println("price:" + prices.get(i));
		}
		
		// ������ǰ�༭һ��·���������ʹ���� 
		JsonPath path = JsonPath.compile("$.store.book[*]");
		books = path.read(json);
		listObj(books);
		
	}

	
	/**
	 * ��ȡJson���ݣ�������Json����
	 * @return
	 */
	public static String jsonData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
			StringBuffer sb = new StringBuffer();
			String line = null;  
            while ((line = br.readLine()) != null) {  
               sb.append(line);
            }
            br.close();
            return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ӡ�ַ���
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(str);
	}
	
	/**
	 * ��ӡlist
	 * @param list
	 */
	public static void list(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	/**
	 * ��ӡlist
	 * @param list
	 */
	public static void listObj(List<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
