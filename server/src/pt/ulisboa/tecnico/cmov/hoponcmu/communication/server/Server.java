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

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class Server {
	
	private static final int PORT = 9090;
	private static String EXPRESSION = "^(?<deg>[-+0-9]+)[^0-9]+(?<min>[0-9]+)[^0-9]+(?<sec>[0-9.,]+)[^0-9.,ENSW]+(?<pos>[ENSW]*)$";

	public static HashMap<String, User> users = new HashMap<String, User>();
	static
	{
		users.put("a", new User("a", "a", "a", "a", "a", 1, 0));
	}
	public static List<String> passwords = new ArrayList<String>(Arrays.asList("b", "c", "d", "e", "f"));
	public static final HashMap<String, Monument> monuments;
	static
    {
        monuments = new HashMap<String, Monument>();
        monuments.put("Mosteiro dos Jerónimos", new Monument("Mosteiro dos Jerónimos", 
        	parseLatLonValue("38º41'50.79\"N"), parseLatLonValue("9º12'20.97\"W")));
        monuments.put("Mosteiro da Batalha", new Monument("Mosteiro da Batalha", 
        	parseLatLonValue("39º39'36.41\"N"), parseLatLonValue("8º49'31.60\"W")));
        monuments.put("Torre de Belém", new Monument("Torre de Belém", 
        	parseLatLonValue("38°41'17.39\"N"), parseLatLonValue("9°12'34.20\"W")));
        monuments.put("Igreja de São Francisco", new Monument("Igreja de São Francisco", 
        	parseLatLonValue("38°34'8.40\"N"), parseLatLonValue("7°54'32.40\"W")));
        monuments.put("Palácio Nacional de Queluz", new Monument("Palácio Nacional de Queluz", 
        	parseLatLonValue("38º45'01.81\"N"), parseLatLonValue("9º15'28.55\"W")));
        monuments.put("Panteão Nacional", new Monument("Panteão Nacional", 
        	parseLatLonValue("38º42'53.12\"N"), parseLatLonValue("9º07'30.55\"W")));
        monuments.put("Padrão dos Descobrimentos", new Monument("Padrão dos Descobrimentos", 
        	parseLatLonValue("38º41'37.69\"N"), parseLatLonValue("9º12'20.95\"W")));
        monuments.put("Basílica da Estrela", new Monument("Basílica da Estrela", 
        	parseLatLonValue("38º42'48.7\"N"), parseLatLonValue("9º09'38.21\"W")));
        monuments.put("Cristo Rei", new Monument("Cristo Rei", 
        	parseLatLonValue("38°40'25.79\"N"), parseLatLonValue("9°10'10.20\"W")));
        monuments.put("Menir de São Paio de Antas", new Monument("Menir de São Paio de Antas", 
        	parseLatLonValue("41°33'21\"N"), parseLatLonValue("8°45'42\"W")));
        monuments.put("Fonte Mourisca", new Monument("Fonte Mourisca", 
        	parseLatLonValue("40°47'16\"N"), parseLatLonValue("7°30'24\"W")));
        monuments.put("Castelo de Vinhais", new Monument("Castelo de Vinhais", 
        	parseLatLonValue("41°50'8\"N"), parseLatLonValue("7°0'5\"W")));
        monuments.put("Santuário de Panóias", new Monument("Santuário de Panóias", 
        	parseLatLonValue("41°16'59.1\"N"), parseLatLonValue("7°40'57.6\"W")));
        monuments.put("Monumento aos Combatentes do Ultramar", new Monument("Monumento aos Combatentes do Ultramar", 
        	parseLatLonValue("38°41'32.21\"N"), parseLatLonValue("9°12'54.59\"W")));
        monuments.put("Muralha Primitiva", new Monument("Muralha Primitiva", 
        	parseLatLonValue("41°8'34.8\"N"), parseLatLonValue("8°36'32.7\"W")));
        monuments.put("Feitoria Inglesa", new Monument("Feitoria Inglesa", 
        	parseLatLonValue("41°8'29.2\"N"), parseLatLonValue("8°36'49.4\"W")));
        monuments.put("Forte de São Pedro do Estoril", new Monument("Forte de São Pedro do Estoril", 
        	parseLatLonValue("38°42'15\"N"), parseLatLonValue("9°23'44\"W")));
        monuments.put("Teatro Nacional São João", new Monument("Teatro Nacional São João", 
        	parseLatLonValue("41°08'51\"N"), parseLatLonValue("8°36'34\"W")));
        monuments.put("Castelo do Sabugal", new Monument("Castelo do Sabugal", 
        	parseLatLonValue("40°21'7\"N"), parseLatLonValue("7°5'22\"W")));
        monuments.put("Ponte da Ribeira de Cobres", new Monument("Ponte da Ribeira de Cobres", 
        	parseLatLonValue("37°30'42.1\"N"), parseLatLonValue("8°3'17.6\"W")));
        monuments.put("Capelas Imperfeitas", new Monument("Capelas Imperfeitas", 
        	parseLatLonValue("39º39'36.41\"N"), parseLatLonValue("8º49'31.60\"W")));
        monuments.put("Monumento aos Mortos da Grande Guerra", new Monument("Monumento aos Mortos da Grande Guerra", 
        	parseLatLonValue("39º45'04.449\"N"), parseLatLonValue("8º55'48.782\"W")));
        monuments.put("Capela dos Ossos", new Monument("Capela dos Ossos", 
        	parseLatLonValue("38º34'08.03\"N"), parseLatLonValue("7º54'29.31\"W")));
    }

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

	public static HashMap<String, User> getUsers() {
		return users;
	}

	public static void addUser(User user) {
		users.put(user.getUsername(), user);
	}

	public static List<String> getPasswords() {
		return passwords;
	}

	public static void removePassword(String password) {
		passwords.remove(password);
	}

	public static HashMap<String, Monument> getMonuments() {
		return monuments;
	}

	public static double parseLatLonValue(String value) {
		double result = Double.NaN;
		if (value.startsWith("\"") && value.endsWith("\"")) {
			value = value.substring(1, value.length() - 2).replace("\"\"", "\"");
		}
		Pattern pattern = Pattern.compile(EXPRESSION);
		Matcher matcher = pattern.matcher(value);
		double deg = Double.NaN;
		double min = Double.NaN;
		double sec = Double.NaN;
		char pos = '\0';
		if (matcher.matches()) {
			deg = Double.parseDouble(matcher.group("deg"));
			min = Double.parseDouble(matcher.group("min"));
			sec = Double.parseDouble(matcher.group("sec"));
			pos = matcher.group("pos").charAt(0);
			result = deg + (min / 60) + (sec / 3600);
			result = ((pos == 'S') || (pos == 'W')) ? -result : result;
		}
		return result;
	}
}
