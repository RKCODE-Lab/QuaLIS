package com.agaramtech.qualis.restcontroller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class CorsFilter implements Filter {

	private static final Logger log = Logger.getAnonymousLogger();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.info("Adding Access Control Response Headers");
		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);	
		
//		if(!isMultipart){
//			ResettableStreamHttpServlet.ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServlet.ResettableStreamHttpServletRequest(
//					(HttpServletRequest) request);
//			String body = IOUtils.toString(wrappedRequest.getReader());
//			System.out.println(body);
//			wrappedRequest.resetInputStream();
//						
//			RequestDispatcher rd = request.getRequestDispatcher("/callService");
//			rd.include(wrappedRequest,response);
//			wrappedRequest.resetInputStream();		
//			filterChain.doFilter(wrappedRequest, response);
//		
//		}else{
			
			filterChain.doFilter(servletRequest, servletResponse);
//		}
		
	}

	@Override
	public void destroy() {

	}

//	private static class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {
//
//		private byte[] rawData;
//		private HttpServletRequest request;
//		private ResettableServletInputStream servletStream;
//
//		public ResettableStreamHttpServletRequest(HttpServletRequest request) {
//			super(request);
//			this.request = request;
//			this.servletStream = new ResettableServletInputStream();
//		}
//
//		public void resetInputStream() {
//			servletStream.stream = new ByteArrayInputStream(rawData);
//		}
//
//		@Override
//		public ServletInputStream getInputStream() throws IOException {
//			if (rawData == null) {
//				rawData = IOUtils.toByteArray(this.request.getReader());
//				servletStream.stream = new ByteArrayInputStream(rawData);
//			}
//			return servletStream;
//		}
//
//		@Override
//		public BufferedReader getReader() throws IOException {
//			if (rawData == null) {
//				rawData = IOUtils.toByteArray(this.request.getReader());
//				servletStream.stream = new ByteArrayInputStream(rawData);
//			}
//			return new BufferedReader(new InputStreamReader(servletStream));
//		}
//
//		private class ResettableServletInputStream extends ServletInputStream {
//
//			private InputStream stream;
//
//			@Override
//			public int read() throws IOException {
//				return stream.read();
//			}
//
//			@Override
//			public boolean isFinished() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean isReady() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public void setReadListener(ReadListener arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		}
//	}
}