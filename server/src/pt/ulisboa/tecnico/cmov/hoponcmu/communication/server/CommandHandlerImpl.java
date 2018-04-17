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
		if (ServerArgs.getUser(suc.getUser().getUsername()) != null) {
			isSignUpValid = false;
			isUsernameValid = false;
		}
		if (!ServerArgs.getPasswords().contains(suc.getUser().getPassword())) {
			isSignUpValid = false;
			isPasswordValid = false;
		}
		if (isSignUpValid) {
			ServerArgs.removePassword(suc.getUser().getPassword());
			ServerArgs.addUser(suc.getUser());
			ServerArgs.sortUsers();
		}
		return new SignUpResponse(suc.getUser(), isSignUpValid, isUsernameValid, isPasswordValid);
	}

	@Override
	public Response handle(LoginCommand lc) {
		boolean isLoginValid = true;
		boolean isUsernameValid = true;
		boolean isPasswordValid = true;
		long sessionId = -1;
		System.out.println("Received: " + lc.getUser().getUsername());
		User user = ServerArgs.getUser(lc.getUser().getUsername());
		if (user == null) {
			isLoginValid = false;
			isUsernameValid = false;
			System.out.println("No such username!");	
		}
		else if (!user.getPassword().equals(lc.getUser().getPassword())) {
			isLoginValid = false;
			isPasswordValid = false;
		}
		else {
			sessionId = ServerArgs.addSessionId(user.getUsername());
		}
		boolean[] errors = new boolean[]{isLoginValid, isUsernameValid, isPasswordValid};
		System.out.println(sessionId + "");
		return new LoginResponse(user, sessionId, errors);
	}

	@Override
	public Response handle(MonumentCommand mc) {
		return new MonumentResponse(ServerArgs.getMonuments().values().toArray(new Monument[ServerArgs.getMonuments().size()]));
	}

	@Override
	public Response handle(DownloadQuizCommand dqc) {
		List<Quiz> quizzes = ServerArgs.getQuizzes().get(dqc.getMonument().getId());
		if (quizzes != null && quizzes.size() > 0) {
			// TODO: if needed select the quiz by id instead of always 0
			Quiz quiz = quizzes.get(0);
			//List<Question> questions = ServerArgs.getQuestions().get(quiz.getId());
			List<Question> questions = new ArrayList<Question>();
			if (ServerArgs.getQuestions().get(quiz.getId()) != null) {
				for (Question q : ServerArgs.getQuestions().get(quiz.getId())) {
					Question question = new Question(q.getQuestion(), q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD());
					questions.add(question);
				}
			}
			if (questions.size() > 0) {
				return new DownloadQuizResponse(quiz, questions.toArray(new Question[questions.size()]));
			}
		}
		return new HelloResponse("Hi from Server!");
	}

	@Override
	public Response handle(RankingCommand rc) {
		ServerArgs.sortUsers();
		return new RankingResponse(ServerArgs.getUsers().toArray(new User[ServerArgs.getUsers().size()]));
	}

	@Override
	public Response handle(SubmitQuizCommand sqc) {
		List<Question> serverQuestions = ServerArgs.getQuestions().get(sqc.getQuestions().get(0).getQuizID());
		User user = ServerArgs.getUser(sqc.getUsername());
		if (serverQuestions != null && user != null && 
			ServerArgs.isCorrectSessionID(sqc.getUsername(), sqc.getSessionID())) {
			if (ServerArgs.isUserInQuizAnswers(sqc.getQuestions().get(0).getQuizID(), sqc.getUsername())) {
				return new HelloResponse("Already answered this quiz");
			}
			int i = 0;
			int rightAnswers = 0;
			for (Question q : serverQuestions) {
				if (q.getAnswer().equals(sqc.getQuestions().get(i).getAnswer())) {
					rightAnswers++;
				}
				i++;
			}
			user.setScore(user.getScore() + rightAnswers);
			user.setTime(user.getTime() + (sqc.getQuiz().getSubmitTime().getTime() - sqc.getQuiz().getOpenTime().getTime()));
			sqc.getQuiz().setScore(rightAnswers);
			ServerArgs.addUsersAnswers(sqc.getQuestions().get(0).getQuizID(), sqc.getUsername());
			ServerArgs.sortUsers();
			System.out.println("Received: " + sqc.getQuiz().getScore());
			return new SubmitQuizResponse(sqc.getQuiz());
		}
		return new HelloResponse("Hi from Server!");
	}
}
