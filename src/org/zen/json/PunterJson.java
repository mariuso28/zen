package org.zen.json;

import java.util.ArrayList;
import java.util.List;

public class PunterJson {
	private long id;
    private String text;
    private String imageUrl;
    private List<PunterJson> children = new ArrayList<PunterJson>();
    
    public PunterJson()
    {
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<PunterJson> getChildren() {
		return children;
	}

	public void setChildren(List<PunterJson> children) {
		this.children = children;
	}
	
    
    
    
}
