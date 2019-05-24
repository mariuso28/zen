package org.zen.json;

import java.util.ArrayList;
import java.util.List;

public class PunterJson {
	private String id;
    private String text;
    private String imageUrl;
    private AccountJson account;
    private String parentId;
    private List<PunterJson> children = new ArrayList<PunterJson>();
    
    public PunterJson()
    {
    }
 
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public AccountJson getAccount() {
		return account;
	}

	public void setAccount(AccountJson account) {
		this.account = account;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
