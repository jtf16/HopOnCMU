package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.*;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.*;

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
		if (Server.getUser(suc.getUser().getUsername()) != null) {
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
		User user = Server.getUser(lc.getUser().getUsername());
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

	@Override
	public Response handle(DownloadQuizCommand dqc) {
		List<Quiz> quizzes = Server.getQuizzes().get(dqc.getMonument().getId());
		if (quizzes != null && quizzes.size() > 0) {
			// TODO: if needed select the quiz by id instead of always 0
			Quiz quiz = quizzes.get(0);
			List<Question> questions = Server.getQuestions().get(quiz.getId());
			if (questions != null && questions.size() > 0) {
				return new DownloadQuizResponse(quiz, questions.toArray(new Question[questions.size()]));
			}
		}
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response handle(RankingCommand rc) {
		return new RankingResponse(Server.getUsers().toArray(new User[Server.getUsers().size()]));
	}
}
