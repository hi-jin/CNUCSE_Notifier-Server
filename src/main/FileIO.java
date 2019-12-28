package main;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class FileIO {

	private static final File file = new File("user.obj");
	private static Vector<User> userList = new Vector<>();
	
	public static void read() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object objList = in.readObject();
			if(objList != null) {
				userList = (Vector<User>) objList;
			}
			in.close();
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EOFException e) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void write() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			out.writeObject(userList);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User login(String id) {
		for(int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getId().equals(id)) {
				return userList.get(i);
			}
		}
		return null;
	}
	
	public static void updateUser(User user) {
		for(int i = 0; i < userList.size(); i++) {
			if(userList.get(i).equals(user)) {
				userList.set(i, user);
			}
		}
	}
	
	public synchronized static boolean addUser(User user) {
		if(!userList.contains(user)) {
			userList.add(user);
			return true;
		} else {
			return false;
		}
	}
}
