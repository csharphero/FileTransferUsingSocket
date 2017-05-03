package com.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**Implementation class
 * @author Bisheswor Devkota
 *
 */
public class FileImplementor extends UnicastRemoteObject implements FileShareInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected FileImplementor() throws RemoteException {
		super();
	}

	/* (non-Javadoc)
	 * @see FileShareInterface#downloadFile(java.lang.String)
	 */
	public byte[] downloadFile(String serverPath) throws RemoteException {
		String fullPath = getFullPath(serverPath);
		File file = new File(fullPath);
		if (!file.exists()) {
			return null;
		}
		Path filetoDownload = Paths.get(fullPath);
		byte[] data = null;
		try {
			data = Files.readAllBytes(filetoDownload);
			
		} catch (Exception ex) {
			System.out.println("Unable to download");
		}
		return data;
	}
	
	/* (non-Javadoc)
	 * @see FileShareInterface#uploadFile(byte[], java.lang.String)
	 */
	public void uploadFile(byte[] bytes, String serverPath) throws RemoteException {
		BufferedOutputStream output;
		try {
			String fullPathServer =getFullPath(serverPath);
			File file = new File(fullPathServer);
			output = new BufferedOutputStream(new FileOutputStream(file.getName()));
			output.write(bytes, 0, bytes.length);
			output.write(bytes, 0, bytes.length);
			output.flush();
			output.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see FileShareInterface#removeFile(java.lang.String)
	 */
	public String removeFile(String path) throws RemoteException {
		String fullPath = getFullPath(path);
		File file = new File(fullPath);
		if (file.exists()) {
			if (file.isFile()) {
				if (file.delete()) {
					return "FILE-" + file.getName() + " IS REMOVED";
				} else {
					return "FILE-" + file.getName() + " COULD NOT BE REMOVED";
				}
			} else {
				return "NOT A FILE TYPE. IF IT IS A DIRECTORY, USE rmdir COMMAND";
			}
		} else {
			return "FILE NOT FOUND";
		}
	}
	/* (non-Javadoc)
	 * @see FileShareInterface#makeDirectory(java.lang.String)
	 */
	public String makeDirectory(String path) throws RemoteException {
		String fullPath = getFullPath(path);
		File file = new File(fullPath);
		if (file.isDirectory()) {
			return "DIRECTORY ALREADY EXISTS";
		} else if (file.mkdir()) {
			return "DIRECTORY:- " + file.getName() + " IS CREATED";
		} else {
			return "FAILED TO CREATE DIRECTORY";
		}
	}
	/* (non-Javadoc)
	 * @see FileShareInterface#listDirectory(java.lang.String)
	 */
	public String listDirectory(String path) throws RemoteException {
		String fullPath = getFullPath(path);
		File file = new File(fullPath);
		if (file.exists()) {
		File[] listOfAll = file.listFiles();
		ArrayList<String> list = new ArrayList<>();
		list.add("PARENT FOLDER: " + file.getName());
		for (File f : listOfAll) {
			if (!f.isHidden()) {
				list.add(f.getName());
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s + "\n");
		}
		return sb.toString();
		}
		else{
			return "Directory not found.";
		}
	}
	/* (non-Javadoc)
	 * @see FileShareInterface#removeDirectory(java.lang.String)
	 */
	public String removeDirectory(String pathOfDirectory) throws RemoteException {
		String fullPathDirectory = getFullPath(pathOfDirectory);
		
		File file = new File(fullPathDirectory);
		if (file.exists()) {
			if (file.isDirectory()) {
				if (file.delete()) {
					return "DIRECTORY: " + file.getName() + " IS REMOVED";
				} else {
					return "DIRECTORY MUST BE EMPTY";
				}
			} else {
				return "NOT A DIRECTORY- USE rm command TO DELETE FILE";
			}
		} else {
			return "DIRECTORY NOT FOUND";
		}
	}

	/* (non-Javadoc)
	 * @see FileShareInterface#shutDown(java.lang.String)
	 */
	public String shutDown(String URL) throws RemoteException {
		try {
			Naming.unbind(URL);
			UnicastRemoteObject.unexportObject(this, true);
			Thread.sleep(1000);
			return "Successfully shut down";
		} catch (Exception e) {
			return "Error shutting down";
		}
	}
	/**
	 * @param path
	 * @return Absolute path of the path specified by the user
	 */
	public static String getFullPath(String path) {
		
		String filter=path.startsWith("/") ? path :  "/" + path;
		String finalPath=Paths.get(".").toAbsolutePath().normalize().toString() + filter;
		return finalPath;
	}

}
