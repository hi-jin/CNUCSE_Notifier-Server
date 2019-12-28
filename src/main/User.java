package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import crawler.Parser;
import crawler.Post;

public class User implements Serializable {

	private String id;
	private ArrayList<Post> postList = new ArrayList<>();
	private ArrayList<Post> myPostList = new ArrayList<>();
	private ArrayList<Post> removedPostList = new ArrayList<>();
	private ArrayList<Post> checkedPostList = new ArrayList<>();
	
	public User(String id) {
		this.id = id;
		this.postList = Parser.getPostList();
		myPostList = postList;
	}
	
	public void refreshPostList() {
		this.postList = Parser.getPostList();
		myPostList = postList;
		
		Iterator<Post> it = removedPostList.iterator();
		while(it.hasNext()) {
			Post post = it.next();
			if(!postList.contains(post)) {
				removedPostList.remove(post);
			}
		}
		myPostList.removeAll(removedPostList);
		myPostList.removeAll(checkedPostList);
	}
	
	public void addCheckedPost(Post post) {
		removedPostList.remove(post);
		myPostList.remove(post);
		if(!checkedPostList.contains(post)) {
			checkedPostList.add(post);
		}
	}
	
	public void removePost(Post post) {
		myPostList.remove(post);
		checkedPostList.remove(post);
		if(!removedPostList.contains(post)) {
			removedPostList.add(post);
		}
	}
	
	public void removeAll() {
		removedPostList.addAll(myPostList);
		myPostList.clear();
	}
	
	public String getId() {
		return this.id;
	}

	public ArrayList<Post> getMyPostList() {
		return this.myPostList;
	}
	
	public ArrayList<Post> getRemovedPostList() {
		return this.removedPostList;
	}
	
	public ArrayList<Post> getCheckedPostList() {
		return this.checkedPostList;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.id.equals(((User) o).id)) {
			return true;
		}
		return false;
	}
}
