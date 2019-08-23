package br.com.consultemed.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterLoggin
 */
@WebFilter(filterName = "/filterLoggin", urlPatterns = {"/pages/*"})
public class FilterLogin implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {	
		  HttpServletRequest req = (HttpServletRequest) request;
		   HttpSession session = req.getSession();
		   HttpServletResponse res = (HttpServletResponse) response;
		   String nome = (String) session.getAttribute("usuario");
		   if(!session.isNew()){
		        if(nome != null || req.getRequestURI().endsWith("login.xhtml")){
		             chain.doFilter(request, response);
		        }else{            
		        	res.sendRedirect("login.xhtml");
		       }
		  }else
		      res.sendRedirect("login.xhtml");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {		
	}
	
}
