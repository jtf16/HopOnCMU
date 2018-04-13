package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;

public class Server {
	
	private static final int PORT = 9090;

	public static void main(String[] args) throws Exception {
		CommandHandlerImpl chi = new CommandHandlerImpl();
		ServerSocket socket = new ServerSocket(PORT);
		Socket client = null;

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Server now closed.");
				try { socket.close(); }
				catch (Exception e) { }
			}
		});
		
		System.out.println("Server is accepting connections at " + PORT);
		
		while (true) {
			try {
			client = socket.accept();
			
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			Command cmd =  (Command) ois.readObject();
			Response rsp = cmd.handle(chi);
			
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(rsp);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (client != null) {
					try { client.close(); }
					catch (Exception e) {}
				}
			}
		}
	}
}
