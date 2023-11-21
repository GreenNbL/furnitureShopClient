package client;

import GUI.LogIn;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket;
    private static ObjectOutputStream coos;
    private static ObjectInputStream cois;
    public static void main(String[] arg)
    {
        try {
            System.out.println("server connecting....");
            clientSocket = new Socket("127.0.0.1",2525);//установление //соединения между локальной машиной и указанным портом узла сети
            System.out.println("connection established....");
           // BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));//создание//буферизированного символьного потока ввода
            coos = new ObjectOutputStream(clientSocket.getOutputStream());//создание//потока вывода
            cois = new ObjectInputStream(clientSocket.getInputStream());//создание//потока ввода
            LogIn logIn=new LogIn(coos,cois);
        }catch(Exception e)	{
            e.printStackTrace();//выполнение метода исключения е
        }
    }
    public static void closeApp()
    {
        try {
            coos.close();//закрытие потока вывода
            cois.close();//закрытие потока ввода
            clientSocket.close();//закрытие сокета
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
