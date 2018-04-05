package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.HelloCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.LoginCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.MonumentCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SignUpCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.HelloResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.LoginResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.MonumentResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.SignUpResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response handle(SignUpCommand suc) {
		boolean isSignUpValid = true;
		boolean isUsernameValid = true;
		boolean isPasswordValid = true;
		if (Server.getUsers().get(suc.getUser().getUsername()) != null) {
			isSignUpValid = false;
			isUsernameValid = false;
		}
		if (!Server.getPasswords().contains(suc.getUser().getPassword())) {
			isSignUpValid = false;
			isPasswordValid = false;
		}
		if (isSignUpValid) {
			Server.removePassword(suc.getUser().getPassword());
			Server.addUser(suc.getUser());
		}
		return new SignUpResponse(suc.getUser(), isSignUpValid, isUsernameValid, isPasswordValid);
	}

	@Override
	public Response handle(LoginCommand lc) {
		boolean isLoginValid = true;
		boolean isUsernameValid = true;
		boolean isPasswordValid = true;
		System.out.println("Received: " + lc.getUser().getUsername());
		User user = Server.getUsers().get(lc.getUser().getUsername());
		if (user == null) {
			isLoginValid = false;
			isUsernameValid = false;
			System.out.println("No such username!");	
		}
		else if (!user.getPassword().equals(lc.getUser().getPassword())) {
			isLoginValid = false;
			isPasswordValid = false;
		}
		return new LoginResponse(user, isLoginValid, isUsernameValid, isPasswordValid);
	}

	@Override
	public Response handle(MonumentCommand mc) {
		return new MonumentResponse(Server.getMonuments().values().toArray(new Monument[Server.getMonuments().size()]));
	}
}
