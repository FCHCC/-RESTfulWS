package me.jmll.utm.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;

public class NotificationView implements View {
	private final String contentType;
	
	public NotificationView(String contentType){
		this.contentType = contentType;
	}
	
	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setStatus(HttpStatus.ACCEPTED.value());
	}

}
