package practie;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;


@WebServlet("/ch10/newsController")
@MultipartConfig(maxFileSize=1024*1024*2, location="c:/Temp")
public class ch10_newsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ch10_newsDAO dao;
	ServletContext ctx;
	
	private final String START_PAGE = "practice/ch10_newsList.jsp";
    
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new ch10_newsDAO();
		ctx = getServletContext();
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("action");
		
		dao = new ch10_newsDAO();
		
		//자바 리플렉션 활용
		Method m;
		String view = null;
		
		if (action == null) action="listNews";
		try {
			// 현재 클래스에서 action 이름과 HttpServletRequest 를 파라미터로 하는 메서드 찾음
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			
			view = (String) m.invoke(this, request);
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			ctx.log("요청 action 없음");
			request.setAttribute("error", "파라미터 잘못 줌.");
			view = START_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// POST 요청 후 리디렉션 방법으로 이동.
		if (view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
	public String addNews(HttpServletRequest request) {
		ch10_news n = new ch10_news();
		try {
			//이미지 저장
			Part part = request.getPart("file");
			String fileName = getFilename(part);
			if (fileName != null && !fileName.isEmpty()) {
				part.write(fileName);
			}
			BeanUtils.populate(n, request.getParameterMap());
			n.setImg("/img/" + fileName);
			
			dao.insert(n);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("뉴스 추가 오류");
			request.setAttribute("error", "등록 안됨");
			return listNews(request);
		}
		return "redirect:/newsController?action=listNews";
	}
	
	public String listNews(HttpServletRequest request) {
		List<ch10_news> list;
		try {
			list = dao.getAll();
			request.setAttribute("newslist", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/practice/ch10_newsList.jsp";
	}
	
	public String getNews(HttpServletRequest request) {
		int aid = Integer.parseInt(request.getParameter("aid"));
		try {
			ch10_news n = dao.getNews(aid);
			request.setAttribute("news", n);
		} catch(Exception e) {
			e.printStackTrace();
			ctx.log("뉴스를 가져오는 과정에서 오류");
			request.setAttribute("error", "뉴스를 가져오지 못함.");
		}
		return "/practice/ch10_newsView.jsp";
	}
	
	 // multipart 헤더에서 파일이름 추출
	private String getFilename(Part part) {
		String fileName = null;   
	    // 파일이름이 들어있는 헤더 영역을 가지고 옴
	    String header = part.getHeader("content-disposition");
	    //part.getHeader -> form-data; name="img"; filename="사진5.jpg"
	    System.out.println("Header => "+header);
	    // 파일 이름이 들어있는 속성 부분의 시작위치를 가져와 쌍따옴표 사이의 값 부분만 가지고옴
	    int start = header.indexOf("filename=");
	    fileName = header.substring(start+10,header.length()-1);        
	    ctx.log("파일명:"+fileName);        
	    return fileName; 
	}

}
