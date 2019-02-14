package com.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/**
 * JsonPathDemo.java
 * 
 * @author yanhengtao
 *
 */
public class JsonPathDemo {

	private static final String JSON_PATH = "src\\main\\resources\\book.json";

	public static void main(String[] args) {
		String sjson = readTxt();

		print("--------------------------------------getJsonValue0--------------------------------------");
		getJsonValue0(sjson);
		print("--------------------------------------getJsonValue1--------------------------------------");
		getJsonValue1(sjson);
		print("--------------------------------------getJsonValue2--------------------------------------");
		getJsonValue2(sjson);
		print("--------------------------------------getJsonValue3--------------------------------------");
		getJsonValue3(sjson);
		print("--------------------------------------getJsonValue4--------------------------------------");
		getJsonValue4(sjson);

		print("--------------------------------------getJsonValue--------------------------------------");
		getJsonValue(sjson);
	}

	/**
	 * 读取Json文本
	 * 
	 * @return
	 */
	private static String readTxt() {
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(JSON_PATH);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static void getJsonValue(String json) {
		// The authors of all books：
		// 获取json中store下book下的所有author值
		List<String> authors1 = JsonPath.read(json, "$.store.book[*].author");

		// All authors：
		// 获取所有json中所有author的值
		List<String> authors2 = JsonPath.read(json, "$..author");

		// All things, both books and bicycles
		// 获取json中store下的所有value值
		List<Object> authors3 = JsonPath.read(json, "$.store.*");

		// The price of everything：
		// 获取json中store下所有price的值
		List<Object> authors4 = JsonPath.read(json, "$.store..price");

		// The third book：
		// 获取json中book数组的第3个值
		List<Object> authors5 = JsonPath.read(json, "$..book[2]");

		// The first two books：
		// 获取json中book数组的第1和第2两个个值
		List<Object> authors6 = JsonPath.read(json, "$..book[0,1]");

		// All books from index 0 (inclusive) until index 2
		// (exclusive)：获取json中book数组的前两个区间值
		List<Object> authors7 = JsonPath.read(json, "$..book[:2]");

		// All books from index 1 (inclusive) until index 2
		// (exclusive)：获取json中book数组的第2个值
		List<Object> authors8 = JsonPath.read(json, "$..book[1:2]");

		// Last two books：
		// 获取json中book数组的最后两个值
		List<Object> authors9 = JsonPath.read(json, "$..book[-2:]");

		// Book number two from tail：
		// 获取json中book数组的第3个到最后一个的区间值
		List<Object> authors10 = JsonPath.read(json, "$..book[2:]");

		// All books with an ISBN number：
		// 获取json中book数组中包含isbn的所有值
		List<Object> authors11 = JsonPath.read(json, "$..book[?(@.isbn)]");

		// All books in store cheaper than 10：
		// 获取json中book数组中price<10的所有值
		List<Object> authors12 = JsonPath.read(json, "$.store.book[?(@.price < 10)]");

		// All books in store that are not
		// "expensive"：获取json中book数组中price<=expensive的所有值
		List<Object> authors13 = JsonPath.read(json, "$..book[?(@.price <= $['expensive'])]");

		// All books matching regex (ignore
		// case)：获取json中book数组中的作者以REES结尾的所有值（REES不区分大小写）
		List<Object> authors14 = JsonPath.read(json, "$..book[?(@.author =~ /.*REES/i)]");

		// Give me every thing：
		// 逐层列出json中的所有值，层级由外到内
		List<Object> authors15 = JsonPath.read(json, "$..*");

		// The number of books：
		// 获取json中book数组的长度
		List<Object> authors16 = JsonPath.read(json, "$..book.length()");

		print("**************authors1**************");
		print(authors1);
		print("**************authors2**************");
		print(authors2);
		print("**************authors3**************");
		list(authors3);
		print("**************authors4**************");
		list(authors4);
		print("**************authors5**************");
		list(authors5);
		print("**************authors6**************");
		list(authors6);
		print("**************authors7**************");
		list(authors7);
		print("**************authors8**************");
		list(authors8);
		print("**************authors9**************");
		list(authors9);
		print("**************authors10**************");
		list(authors10);
		print("**************authors11**************");
		list(authors11);
		print("**************authors12**************");
		list(authors12);
		print("**************authors13**************");
		list(authors13);
		print("**************authors14**************");
		list(authors14);
		print("**************authors15**************");
		list(authors15);
		print("**************authors16**************");
		list(authors16);

	}

	/**
	 * 得到匹配表达式的所有值
	 */
	public static void getJsonValue0(String json) {
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		print(authors);
	}

	/**
	 * 得到某个具体值
	 */
	public static void getJsonValue1(String json) {
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		String author1 = JsonPath.read(document, "$.store.book[1].author");
		print(author0);
		print(author1);
	}

	/**
	 * 读取json的一种写法
	 */
	@SuppressWarnings("unchecked")
	public static void getJsonValue2(String json) {
		ReadContext ctx = JsonPath.parse(json);

		List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");

		List<Map<String, Object>> expensiveBooks = JsonPath.using(Configuration.defaultConfiguration()).parse(json)
				.read("$.store.book[?(@.price > 10)]", List.class);
		print(authorsOfBooksWithISBN);
		print("****************Map****************");
		printListMap(expensiveBooks);
	}

	/**
	 * 读取json的一种写法 得到的值是一个String，所以不能用List存储
	 */
	public static void getJsonValue3(String json) {
		// Will throw an java.lang.ClassCastException
		// List<String> list = JsonPath.parse(json).read("$.store.book[0].author");
		// 由于会抛异常，暂时注释上面一行，要用的话，应使用下面的格式

		// Works fine
		String author = JsonPath.parse(json).read("$.store.book[0].author");
		print(author);
	}

	/**
	 * 读取json的一种写法 支持逻辑表达式，&&和||
	 */
	public static void getJsonValue4(String json) {
		List<Map<String, Object>> books1 = JsonPath.parse(json)
				.read("$.store.book[?(@.price < 10 && @.category == 'fiction')]");
		List<Map<String, Object>> books2 = JsonPath.parse(json)
				.read("$.store.book[?(@.category == 'reference' || @.price > 10)]");
		print("****************books1****************");
		printListMap(books1);
		print("****************books2****************");
		printListMap(books2);
	}

	private static void print(List<String> list) {
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	@SuppressWarnings("rawtypes")
	private static void printListMap(List<Map<String, Object>> list) {
		for (Iterator<Map<String, Object>> it = list.iterator(); it.hasNext();) {
			Map<String, Object> map = it.next();
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				System.out.println(iterator.next());
			}
		}
	}

	/**
	 * 打印字符串
	 * 
	 * @param str
	 */
	private static void print(String str) {
		System.out.println(str);
	}

	private static void list(List<Object> list) {
		for (Iterator<Object> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}