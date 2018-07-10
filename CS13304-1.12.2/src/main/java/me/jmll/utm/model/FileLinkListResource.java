package me.jmll.utm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileLinkListResource extends Resource {
	
	private List<Link> links = new ArrayList<>();
	private List<File> fileLinks = new ArrayList<>();
	
	@XmlElement(name = "link")
	public List<Link> getLinks() {
		return links;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link) {
		this.links.add(link);
	}
	
	@XmlElement(name="file")
	public List<File> getFileLinks() {
		return fileLinks;
	}
	
	public void setFiles(List<File> fileLinks) {
		this.fileLinks = fileLinks;
	}
	
	public void addFileLink(File link){
		this.fileLinks.add(link);
	}
}
