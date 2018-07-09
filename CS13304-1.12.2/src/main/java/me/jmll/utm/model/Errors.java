package me.jmll.utm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Errors {
	private List<ErrorInfo> errors = new ArrayList<>();
	@XmlElement(name = "error")
	public List<ErrorInfo> getErrors(){
		return errors;
	}
	public void setErrors(List<ErrorInfo> errors){
		this.errors = errors;
	}
	
	public void addError(ErrorInfo error){
		this.errors.add(error);
	}
}
