package ch08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//DB없이 샘플 데이터를 제공하기 위한 클래스
public class ProductService {
	Map<String, Product> products = new HashMap<>();
	
	//객체 생성
	public ProductService() {
		Product p = new Product("101", "애플폰 12", "애플 전자", 120000, "2020.12.12");
		products.put("101", p);
		
		p = new Product("102", "삼성폰 21", "삼성 전자", 130000, "2021.02.02");
		products.put("102", p);
		
		p = new Product("103", "엘지 듀얼폰", "엘지 전자", 150000, "2021.03.02");
		products.put("103", p);
	}
	
	//List에 value값 넣기?
	public List<Product> findAll() {
		return new ArrayList<>(products.values());
	}
	//key 값 찾기
	public Product find(String id) {
		return products.get(id);
	}
}
