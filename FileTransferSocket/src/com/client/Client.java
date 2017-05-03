package com.client;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;

import com.server.FileShareInterface;

/**
 * Client Class Implementation
 * 
 * @author Bisheswor Devkota
 */
public class Client {

	public static void main(String[] args) throws Exception {
		if (isEnvironmentSet() && args.length >= 1) {
			try {

				String portAndServer = getEnvironment();

				String url = "rmi://" + portAndServer + "/FILESERVER";
				FileShareInterface server = (FileShareInterface) Naming.lookup(url);
				switch (args[0].toLowerCase()) {
				case "upload":
					String fullPathClient = Paths.get(".").toAbsolutePath().normalize().toString() + args[1];
					File file = new File(fullPathClient);
					if (!file.exists()) {
						System.out.println("File doesn't exist");
					}
					Path fileToUpload = Paths.get(fullPathClient);
					byte[] data = null;
					try {
						data = Files.readAllBytes(fileToUpload);
						server.uploadFile(data, args[2]);
						System.out.println("File uploaded.");

					} catch (Exception ex) {
						System.out.println("Unable to upload");
					}
					break;

				case "download":
					String fullPath = Paths.get(".").toAbsolutePath().normalize().toString() + args[2];
					byte[] arrayFromServer = server.downloadFile(args[1]);
					try {
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fullPath));
						out.write(arrayFromServer);
						out.flush();
						out.close();
						System.out.println("File Downloaded.");

					} catch (Exception ex) {
						System.out.println("Error downloading file");
					}
					break;
				case "mkdir":
					System.out.println(server.makeDirectory(args[1]));
					break;
				case "rmdir":
					System.out.println(server.removeDirectory(args[1]));
					break;
				case "dir":
					System.out.println(server.listDirectory(args[1]));
					break;
				case "rm":
					System.out.println(server.removeFile(args[1]));
					break;
				case "shutdown":
					System.out.println(server.shutDown(url));
					break;
				default:
					System.out.println("Please enter a right command-(REFER TO THE README FILE) and try again");
					break;
				}
			} catch (Exception ex) {
				System.out.println("File Client Exception: \n" + ex.getMessage());
				System.exit(1);
			}
		} else {
			System.out.println("Please set the enviroment variable PA2_SERVER");
		}

	}

	/** 
	 * @return value set by the environment variable
	 */
	public static String getEnvironment() {

		return System.getenv("PA2_SERVER");
	}

	/**
	 * @return true if the Environment variable is set otherwise false
	 */
	public static boolean isEnvironmentSet() {
		return System.getenv("PA2_SERVER") != null;
	}
}
