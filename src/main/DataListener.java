package main;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import crawler.Post;

public class DataListener extends Thread {
	
	User user = null;
	Socket client = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	public DataListener(Socket client) {
		this.client = client;
		this.setName("client " + client.getInetAddress());
		System.out.println(this.getName() + "접속");
		
		try {
			out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String line;
		String result;
		Post post;
		Iterator<Post> it;
		try {
			while((line = in.readLine()) != null) {
				System.out.println(this.getName() + "/ " + line);
				
				String[] command = line.split("/#/");
				
				switch(command[0]) {
				case "addUser":
					// addUser/#/id
					if(FileIO.addUser(new User(command[1]))) {
						out.println("addUser/#/1");
					} else {
						out.println("addUser/#/0");
					}
					out.flush();
					break;
				case "login":
					// login/#/id
					user = FileIO.login(command[1]);
					if(user != null) {
						Main.getCurrentUserList().add(this);
						out.println("login/#/1");
					} else {
						System.out.println("로그인 실패");
						out.println("login/#/0");
					}
					out.flush();
					break;
				case "logout":
					FileIO.updateUser(user);
					Main.getCurrentUserList().remove(this);
					user = null;
					break;
				case "getMyPostList":
					result = "getMyPostList";
					post = null;
					it = user.getMyPostList().iterator();
					while(it.hasNext()) {
						post = it.next();
						result += "/#/" + post.getTitle() + "/#/" + post.getLink();
					}
					out.println(result);
					out.flush();
					break;
				case "getRemovedPostList":
					result = "getRemovedPostList";
					post = null;
					it = user.getRemovedPostList().iterator();
					while(it.hasNext()) {
						post = it.next();
						result += "/#/" + post.getTitle() + "/#/" + post.getLink();
					}
					out.println(result);
					out.flush();
					break;
				case "getCheckedPostList":
					result = "getCheckedPostList";
					post = null;
					it = user.getCheckedPostList().iterator();
					while(it.hasNext()) {
						post = it.next();
						result += "/#/" + post.getTitle() + "/#/" + post.getLink();
					}
					out.println(result);
					out.flush();
					break;
				case "addCheckedPost":
					// addCheckedPost/#/title/#/link
					user.addCheckedPost(new Post(command[1], command[2]));
					break;
				case "removePost":
					// removePost/#/title/#/link
					user.removePost(new Post(command[1], command[2]));
					break;
				}
				if(user != null) FileIO.updateUser(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
