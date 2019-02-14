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
	 * ��ȡJson�ı�
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
		// The authors of all books��
		// ��ȡjson��store��book�µ�����authorֵ
		List<String> authors1 = JsonPath.read(json, "$.store.book[*].author");

		// All authors��
		// ��ȡ����json������author��ֵ
		List<String> authors2 = JsonPath.read(json, "$..author");

		// All things, both books and bicycles
		// ��ȡjson��store�µ�����valueֵ
		List<Object> authors3 = JsonPath.read(json, "$.store.*");

		// The price of everything��
		// ��ȡjson��store������price��ֵ
		List<Object> authors4 = JsonPath.read(json, "$.store..price");

		// The third book��
		// ��ȡjson��book����ĵ�3��ֵ
		List<Object> authors5 = JsonPath.read(json, "$..book[2]");

		// The first two books��
		// ��ȡjson��book����ĵ�1�͵�2������ֵ
		List<Object> authors6 = JsonPath.read(json, "$..book[0,1]");

		// All books from index 0 (inclusive) until index 2
		// (exclusive)����ȡjson��book�����ǰ��������ֵ
		List<Object> authors7 = JsonPath.read(json, "$..book[:2]");

		// All books from index 1 (inclusive) until index 2
		// (exclusive)����ȡjson��book����ĵ�2��ֵ
		List<Object> authors8 = JsonPath.read(json, "$..book[1:2]");

		// Last two books��
		// ��ȡjson��book������������ֵ
		List<Object> authors9 = JsonPath.read(json, "$..book[-2:]");

		// Book number two from tail��
		// ��ȡjson��book����ĵ�3�������һ��������ֵ
		List<Object> authors10 = JsonPath.read(json, "$..book[2:]");

		// All books with an ISBN number��
		// ��ȡjson��book�����а���isbn������ֵ
		List<Object> authors11 = JsonPath.read(json, "$..book[?(@.isbn)]");

		// All books in store cheaper than 10��
		// ��ȡjson��book������price<10������ֵ
		List<Object> authors12 = JsonPath.read(json, "$.store.book[?(@.price < 10)]");

		// All books in store that are not
		// "expensive"����ȡjson��book������price<=expensive������ֵ
		List<Object> authors13 = JsonPath.read(json, "$..book[?(@.price <= $['expensive'])]");

		// All books matching regex (ignore
		// case)����ȡjson��book�����е�������REES��β������ֵ��REES�����ִ�Сд��
		List<Object> authors14 = JsonPath.read(json, "$..book[?(@.author =~ /.*REES/i)]");

		// Give me every thing��
		// ����г�json�е�����ֵ���㼶���⵽��
		List<Object> authors15 = JsonPath.read(json, "$..*");

		// The number of books��
		// ��ȡjson��book����ĳ���
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
	 * �õ�ƥ����ʽ������ֵ
	 */
	public static void getJsonValue0(String json) {
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		print(authors);
	}

	/**
	 * �õ�ĳ������ֵ
	 */
	public static void getJsonValue1(String json) {
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		String author1 = JsonPath.read(document, "$.store.book[1].author");
		print(author0);
		print(author1);
	}

	/**
	 * ��ȡjson��һ��д��
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
	 * ��ȡjson��һ��д�� �õ���ֵ��һ��String�����Բ�����List�洢
	 */
	public static void getJsonValue3(String json) {
		// Will throw an java.lang.ClassCastException
		// List<String> list = JsonPath.parse(json).read("$.store.book[0].author");
		// ���ڻ����쳣����ʱע������һ�У�Ҫ�õĻ���Ӧʹ������ĸ�ʽ

		// Works fine
		String author = JsonPath.parse(json).read("$.store.book[0].author");
		print(author);
	}

	/**
	 * ��ȡjson��һ��д�� ֧���߼����ʽ��&&��||
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
	 * ��ӡ�ַ���
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