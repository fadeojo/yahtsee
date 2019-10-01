package com.carleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;




public class GameServer implements Serializable{
	private ServerSocket ss;
	private int numPlayers;
	private int rounds;
	private Player1[] players;


	private serverSideConnection player1,player2,player3;
	public GameServer() {
		System.out.println("---Game Server---");
		this.numPlayers=0;
		try {
			ss=new ServerSocket(5000);
		} catch (IOException e) {
			System.out.println("IOexception from gameserver constoctor");
		}
		rounds=0;
	}

	public GameServer(Player1 p1,Player1 p2,Player1 p3){
		players[0]=p1;
		players[1]=p2;
		players[2]=p3;

	}
	public void sendTurn() {
		try {
			while (rounds<13) {


			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void acceptConnections() {
		try {
			System.out.println("waiting for players to join Game lobby...");
			Socket s=ss.accept();
			while (numPlayers<3) {
				numPlayers++;
				System.out.println("player #"+numPlayers+" has connected");

				serverSideConnection ssConnection=new serverSideConnection(s, false);

				switch (numPlayers) {
				case 1:
					 ssConnection=new serverSideConnection(s,true);
					player1=ssConnection;
					break;
				case 2:
					player2=ssConnection;
					break;
				case 3:
					player3=ssConnection;
					break;
				default:
					break;
				}
				Thread t=new Thread(ssConnection);
				t.run();
			}
			System.out.println("Game lobby full. please wait your turn");
		} catch (Exception e) {
			System.out.println("IOexception from accept connections");
			// TODO: handle exception
		}
	}

	private class serverSideConnection implements Runnable{
		private Socket socket;
//		private DataInputStream dataIn;
//		private  DataOutputStream dataOut;
		private DataInputStream DataIn;
		private DataOutputStream DataOut;
		 Boolean play;
		public serverSideConnection(Socket s,Boolean play) {
			socket=s;
			this.play=play ;
			try {
				DataIn=new DataInputStream(socket.getInputStream());
				DataOut=new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				System.out.println("IOexception from SSC constructor");
			}
		}
		@Override
		public void run() {
			try {
				DataOut.writeBoolean(play);

				DataOut.flush();
//				while (true) {
//
//				}
			} catch (Exception e) {
				System.out.println("IOexception from run in ssc");
			}
		}

	}
	public static void main(String[] args) {
		GameServer gs=new GameServer();
		gs.acceptConnections();
	}

}
