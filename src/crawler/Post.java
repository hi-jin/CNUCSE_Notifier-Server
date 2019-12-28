package crawler;

import java.io.Serializable;

public class Post implements Serializable {

	private String title;
	private String link;
	
	public Post(String title, String link) {
		this.title = title;
		this.link = link;
	}
	
	public String getTitle() {
		return this.title;
	}
	public String getLink() {
		return this.link;
	}
	
	public String toString() {
		return "title : " + title + "\nlink : " + link;
	}
	
	@Override
	public boolean equals(Object o) {
		Post other = (Post) o;
		if(this.title.equals(other.title) && this.link.equals(other.link)) {
			return true;
		}
		return false;
	}
}
