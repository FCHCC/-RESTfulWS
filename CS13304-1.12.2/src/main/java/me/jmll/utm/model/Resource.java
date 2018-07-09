package me.jmll.utm.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Resource {
	private List<Link> links = new ArrayList<>();
	
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
}