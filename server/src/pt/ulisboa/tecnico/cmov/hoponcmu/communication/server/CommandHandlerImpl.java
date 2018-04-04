package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.HelloCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.HelloResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}
}
