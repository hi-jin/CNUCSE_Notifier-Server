package crawler;

import java.util.ArrayList;

public class Parser {

	private static ArrayList<Post> postList = new ArrayList<>();
	
	public static ArrayList<Post> parseAll() {
		postList.clear();
		parse("cseNotice");
		parse("cseNews");
		parse("cseProjectNews");
		
		return postList;
	}
	
	public static ArrayList<Post> getPostList() {
		return postList;
	}
	
	public static void parse(String type) {
		
		////////// get info //////////
		String news = "";
		
		if(type.equals("cseNotice")) {
			news = Crawler.getCseNotice().replace("  ", "").replace("	", "").replace("&lt;", "<").replace("&gt;", ">");
		} else if(type.equals("cseNews")) {
			news = Crawler.getCseNews().replace("  ", "").replace("	", "").replace("&lt;", "<").replace("&gt;", ">");
		} else if(type.equals("cseProjectNews")) {
			news = Crawler.getCseProjectNews().replace("  ", "").replace("	", "").replace("&lt;", "<").replace("&gt;", ">");
		}
		
		String[] notices = news.split("<td class=\"b-td-left\">");
		int count = notices.length;
		
		for(int i = 1; i < count; i++) {
			notices[i] = notices[i].split("<a href=\"")[1];
		}
		//////////////////////////////
		
		////////// parse link //////////
		String[] links = new String[count];
		
		for(int i = 1; i < count; i++) {
			links[i] = notices[i].split("title=\"")[0].replace("\"", "").replace(" ", "").replace("&amp;", "&");
			if(type.equals("cseNotice")) {
				links[i] = Crawler.cse + Crawler.cseNotice + links[i];
			} else if(type.equals("cseNews")) {
				links[i] = Crawler.cse + Crawler.cseNews + links[i];
			} else if(type.equals("cseProjectNews")) {
				links[i] = Crawler.cse + Crawler.cseProjectNews + links[i];
			}
		}
		////////////////////////////////
		
		////////// parse title && add to postList //////////
		String[] titles = new String[count];
		
		for(int i = 1; i < count; i++) {
			titles[i] = notices[i].split("title=\"")[1].split("자세히 보기")[0];
			postList.add(new Post(titles[i], links[i]));
		}
		////////////////////////////////////////////////////
	}
}
