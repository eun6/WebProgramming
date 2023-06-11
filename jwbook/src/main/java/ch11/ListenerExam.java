package ch11;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ListenerExam implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener {

  
	//리스너 생성자
    public ListenerExam() {
    }
    
    //ServletContext 시작
    public void contextInitialized(ServletContextEvent sce)  { 
    	sce.getServletContext().log("ServletContext 시작됨!");
    }
    
    //ServletContext 종료
    public void contextDestroyed(ServletContextEvent sce)  { 
    	sce.getServletContext().log("ServletContext 종료됨!");
    }


    //ServletContext 속성 추가
    public void attributeAdded(ServletContextAttributeEvent scae)  { 
    	scae.getServletContext().log("ServletContext 속성 추가 : " + scae.getValue());
    }

    //ServletContext 속성 변경
    public void attributeReplaced(ServletContextAttributeEvent scae)  { 
    }
    

    //ServletContext 속성 삭제
    public void attributeRemoved(ServletContextAttributeEvent scae)  { 
    }

	
    //세션, 클라이언트 마다 고유 아이디 생성 -> 해당 아이디 값 함께 출력되도록 설계.
    public void sessionCreated(HttpSessionEvent se)  { 
    	se.getSession().getServletContext().log("Session 생성 : " + se.getSession().getId());
    
    }
    
    public void sessionDestroyed(HttpSessionEvent se)  { 
    }

	
    public void attributeAdded(HttpSessionBindingEvent se)  { 
    	se.getSession().getServletContext().log("Session 속성 추가 : " + se.getValue());
    }

    public void attributeRemoved(HttpSessionBindingEvent se)  { 
    }

	
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
    }

	
   
	
}
