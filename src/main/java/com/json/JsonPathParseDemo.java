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
	 * 获取JsonPath
	 */
	private static void jsonPath() {
		
		String json = jsonData();

		// 输出book[0]的author值
		String author = JsonPath.read(json, "$.store.book[0].author");
		print(author);

		// 输出全部author的值，使用Iterator迭代
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		list(authors);

		// 输出book[*]中category == 'reference'的book
		List<Object> books = JsonPath.read(json, "$.store.book[?(@.category == 'reference')]");
		listObj(books);
		
		// 输出book[*]中price>10的book 
		books = JsonPath.read(json, "$.store.book[?(@.price>10)]");
		listObj(books);
		
		// 输出book[*]中含有isbn元素的book
		books = JsonPath.read(json, "$.store.book[?(@.isbn)]");
		listObj(books);
		
		// 输出该json中所有price的值 
		List<Double> prices = JsonPath.read(json, "$..price");
		for (int i = 0; i < prices.size(); i++) {
			System.out.println("price:" + prices.get(i));
		}
		
		// 可以提前编辑一个路径，并多次使用它 
		JsonPath path = JsonPath.compile("$.store.book[*]");
		books = path.read(json);
		listObj(books);
		
	}

	
	/**
	 * 读取Json数据，并构建Json数据
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
	 * 打印字符串
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(str);
	}
	
	/**
	 * 打印list
	 * @param list
	 */
	public static void list(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	/**
	 * 打印list
	 * @param list
	 */
	public static void listObj(List<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
