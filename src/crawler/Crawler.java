package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Crawler {

	static String cse = "https://computer.cnu.ac.kr/computer/notice/";
	static String cseNotice = "bachelor.do";
	static String cseNews = "notice.do";
	static String cseProjectNews = "project.do";
	
	public static String getCseNotice() {
		StringBuffer response = null;
		try {
			URLConnection urlc = (new URL(cse + cseNotice)).openConnection();
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
								urlc.getInputStream()));
			response = new StringBuffer();
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public static String getCseNews() {
		StringBuffer response = null;
		try {
			URLConnection urlc = (new URL(cse + cseNews)).openConnection();
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
								urlc.getInputStream()));
			response = new StringBuffer();
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public static String getCseProjectNews() {
		StringBuffer response = null;
		try {
			URLConnection urlc = (new URL(cse + cseProjectNews)).openConnection();
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
								urlc.getInputStream()));
			response = new StringBuffer();
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
}
