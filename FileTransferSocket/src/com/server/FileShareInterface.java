package com.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileShareInterface extends Remote {
	/**
	 * @param source
	 * @return byteArray
	 * @throws RemoteException
	 */
	public byte[] downloadFile(String source) throws RemoteException;
	/**
	 * @param bytes
	 * @param serverPath
	 * @throws RemoteException
	 */
	public void uploadFile(byte[] bytes, String serverPath) throws RemoteException;
	/**
	 * @param path
	 * @return nothing
	 * @throws RemoteException
	 */
	public String removeFile(String path) throws RemoteException;
	/**
	 * @param path
	 * @return result
	 * @throws RemoteException
	 */
	public String makeDirectory(String path) throws RemoteException;
	/**
	 * @param path
	 * @return result
	 * @throws RemoteException
	 */
	public String listDirectory(String path) throws RemoteException;
	/**
	 * @param pathOfDirectory
	 * @return list of directory 
	 * @throws RemoteException
	 */
	public String removeDirectory(String pathOfDirectory) throws RemoteException;
	/**
	 * @param URL
	 * @return result
	 * @throws RemoteException
	 */
	public String shutDown(String URL) throws RemoteException;
}
