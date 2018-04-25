package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.*;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.*;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response handle(DownloadQuizCommand dqc) {
		System.out.println("DownloadQuizCommand");
		if (ServerArgs.isCorrectSessionID(dqc.getUsername(), dqc.getSessionID())) {
			List<Question> questions = ServerArgs.getQuestions().get(dqc.getQuiz().getId());
			if (questions != null && questions.size() > 0) {
				// Cycle so the user doesn't have access to answer
				Question[] questionsArray = new Question[questions.size()];
				int i = 0;
				for (Question q : questions) {
					Question question = new Question(q.getQuestion(), q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD());
					questionsArray[i] = question;
					i++;
				}
				return new DownloadQuizResponse(dqc.getUsername(), dqc.getQuiz(), questionsArray);
			}
			return new InvalidQuizExceptionResponse("Quiz is currently unavailable\nStay tuned for the updates");
		}
		return new SessionExceptionResponse("Invalid session");
	}

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response handle(LoginCommand lc) {
		System.out.println("LoginCommand");
		System.out.println("Received: " + lc.getUser().getUsername());
		User user = ServerArgs.getUser(lc.getUser().getUsername());
		if (user == null) {
			return new UsernameExceptionResponse("No such username");
		}
		else if (!user.getPassword().equals(lc.getUser().getPassword())) {
			return new PasswordExceptionResponse("Incorrect password");
		}
		return new LoginResponse(user, ServerArgs.addSessionId(user.getUsername()));
	}
	
	@Override
	public Response handle(MonumentCommand mc) {
		System.out.println("MonumentCommand");
		return new MonumentResponse(getMonuments());
	}
	
	@Override
	public Response handle(RankingCommand rc) {
		System.out.println("RankingCommand");
		ServerArgs.sortUsers();
		return new RankingResponse(getRanking());
	}

	@Override
	public Response handle(SetUpCommand suc) {
		System.out.println("SetUpCommand");
		return new SetUpResponse(getQuizzes(), getMonuments(), getRanking());
	}

	@Override
	public Response handle(SignUpCommand suc) {
		System.out.println("SignUpCommand");
		if (ServerArgs.getUser(suc.getUser().getUsername()) != null) {
			return new UsernameExceptionResponse("Username already in use");
		} else if (!ServerArgs.getPasswords().contains(suc.getUser().getPassword())) {
			return new PasswordExceptionResponse("Not a valid code");
		}
		ServerArgs.removePassword(suc.getUser().getPassword());
		ServerArgs.addUser(suc.getUser());
		return new SignUpResponse(suc.getUser());
	}

	@Override
	public Response handle(SubmitQuizCommand sqc) {
		System.out.println("SubmitQuizCommand");
		if (ServerArgs.isCorrectSessionID(sqc.getUserQuiz().getUsername(), sqc.getSessionID())) {
			if (ServerArgs.isUserInQuizAnswers(sqc.getUserQuiz().getQuizID(), sqc.getUserQuiz().getUsername())) {
				return new InvalidQuizExceptionResponse("Already answered this quiz");
			}
			List<Question> serverQuestions = ServerArgs.getQuestions().get(sqc.getUserQuiz().getQuizID());
			User user = ServerArgs.getUser(sqc.getUserQuiz().getUsername());
			int rightAnswers = 0, i = 0;
			for (Question q : serverQuestions) {
				if (q.getAnswer().equals(sqc.getQuestions().get(i).getAnswer())) {
					rightAnswers++;
				}
				i++;
			}
			user.setScore(user.getScore() + rightAnswers);
			user.setTime(user.getTime() + sqc.getUserQuiz().getSubmitTime().getTime() - sqc.getUserQuiz().getOpenTime().getTime());
			sqc.getUserQuiz().setScore(rightAnswers);
			ServerArgs.addUsersAnswers(sqc.getUserQuiz().getQuizID(), sqc.getUserQuiz().getUsername());
			System.out.println("Score: " + sqc.getUserQuiz().getScore());
			return new SubmitQuizResponse(sqc.getUserQuiz());
		}
		return new SessionExceptionResponse("Invalid session");
	}

	private User[] getRanking() {
		return ServerArgs.getUsers().toArray(new User[ServerArgs.getUsers().size()]);
	}

	private Monument[] getMonuments() {
		return ServerArgs.getMonuments().values().toArray(new Monument[ServerArgs.getMonuments().size()]);
	}

	private Quiz[] getQuizzes() {
		List<Quiz> quizzes = new ArrayList<Quiz>();
		for (List<Quiz> value : ServerArgs.getQuizzes().values()) {
		    quizzes.addAll(value);
		}
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
}
