package me.jmll.utm.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpMethod;

@XmlRootElement
public class OptionsDoc {

	private Map<HttpMethod, String> methods = new HashMap<>() ;
	
	@XmlElement(name = "method")
	public Map<HttpMethod,String> getMethods(){
		return methods;
	}
	
	public void setMethods(Map<HttpMethod, String> methods) {
		this.methods = methods; 
	}
	
	
    
	
}
