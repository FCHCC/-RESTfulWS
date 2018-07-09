package me.jmll.utm.model;

import javax.xml.bind.annotation.XmlAttribute;

public class ErrorInfo {
    private String href;
    private String message;
    private String status;

    public ErrorInfo(String url, String code, Exception ex) {
        this.setHref(url);
        this.setMessage(ex.getLocalizedMessage());
        this.setStatus(code);
    }
    @XmlAttribute
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	@XmlAttribute
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@XmlAttribute
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}