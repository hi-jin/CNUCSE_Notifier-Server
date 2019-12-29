package main;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

import crawler.Parser;

public class Main {

	private static ServerSocket server = null;
	private static Vector<DataListener> currentUserList = new Vector<>();
	
	public static void main(String[] args) {
		FileIO.read();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			FileIO.write();
			System.out.println("파일이 성공적으로 저장되었습니다.");
		}));
		
		Thread autoUpdating = new Thread(() -> {
			while(true) {
				System.out.println("autoUpdating on");
				Parser.parseAll();
				Iterator<DataListener> it = currentUserList.iterator();
				while(it.hasNext()) {
					DataListener client = it.next();
					try {
						PrintWriter out = new PrintWriter(new BufferedOutputStream(client.client.getOutputStream()));
						client.user.refreshPostList();
						out.println("refresh");
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(600000); // 10분에 1번씩
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		});
		autoUpdating.setDaemon(true);
		autoUpdating.start();
		
		Socket client = null;
		try {
			server = new ServerSocket(50000);
			while(true) {
				client = server.accept();
				(new DataListener(client)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Vector<DataListener> getCurrentUserList() {
		return currentUserList;
	}
}
