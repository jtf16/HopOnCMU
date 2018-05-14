package pt.ulisboa.tecnico.cmov.hoponcmu.communication.server;

import java.io.*;

import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.util.*;
import java.nio.ByteBuffer;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.*;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.*;

public class CommandHandlerImpl implements CommandHandler {

	@Override
	public Response handle(DownloadQuizCommand dqc) {
		System.out.println("DownloadQuizCommand");
		return downloadQuiz(dqc);
	}

	@Override
	public Response handle(DownloadQuizSealedCommand dqsc) {
		System.out.println("DownloadQuizSealedCommand");
		return performSealed(dqsc);
	}

	@Override
	public Response handle(HelloCommand hc) {
		System.out.println("HelloCommand");
		return hello(hc);
	}

	@Override
	public Response handle(HelloSealedCommand hsc) {
		System.out.println("HelloSealedCommand");
		return performSealed(hsc);
	}

	@Override
	public Response handle(LoginCommand lc) {
		System.out.println("LoginCommand");
		return login(lc);
	}

	@Override
	public Response handle(LoginSealedCommand lsc) {
		System.out.println("LoginSealedCommand");
		return performSealed(lsc);
	}
	
	@Override
	public Response handle(MonumentCommand mc) {
		System.out.println("MonumentCommand");
		return monuments(mc);
	}

	@Override
	public Response handle(MonumentSealedCommand msc) {
		System.out.println("MonumentSealedCommand");
		return performSealed(msc);
	}

	@Override
	public Response handle(PubKeyExchangeCommand pkec) {
		System.out.println("PubKeyExchangeCommand");
		User user = ServerArgs.getUser(pkec.getUsername());
		if (user == null) {
			return new UsernameExceptionResponse("No such username");
		}
		try {
			KeyPair keyPair = SecurityManager.getNewKeyPair();
			SecretKey key = SecurityManager.generateSharedSecret(keyPair.getPrivate(), pkec.getPublicKey());
			user.setSharedSecret(key);
			return new PubKeyExchangeResponse(keyPair.getPublic().getEncoded());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return new SecurityExceptionResponse("Security issue");
		}
	}

	@Override
	public Response handle(PubKeySignUpExchangeCommand pksuec) {
		System.out.println("PubKeySignUpExchangeCommand");
		try {
			KeyPair keyPair = SecurityManager.getNewKeyPair();
			SecretKey key = SecurityManager.generateSharedSecret(keyPair.getPrivate(), pksuec.getPublicKey());
			ServerArgs.addUserKey(pksuec.getUsername(), key);
			return new PubKeyExchangeResponse(keyPair.getPublic().getEncoded());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return new SecurityExceptionResponse("Security issue");
		}
	}
	
	@Override
	public Response handle(RankingCommand rc) {
		System.out.println("RankingCommand");
		return ranking(rc);
	}

	@Override
	public Response handle(RankingSealedCommand rsc) {
		System.out.println("RankingSealedCommand");
		return performSealed(rsc);
	}

	@Override
	public Response handle(SetUpCommand suc) {
		System.out.println("SetUpCommand");
		return new SetUpResponse(getQuizzes(), getMonuments(), getRanking());
	}

	@Override
	public Response handle(SignUpCommand suc) {
		System.out.println("SignUpCommand");
		return signUp(suc);
	}

	@Override
	public Response handle(SignUpSealedCommand susc) {
		System.out.println("SignUpSealedCommand");
		SecretKey key = ServerArgs.getUserKey(susc.getUsername());
		SignUpCommand suc = (SignUpCommand) getObject(susc.getSealedContent(), susc.getDigest(), key);
		if (suc != null) {
			return signUp(suc);
		}
		return new SecurityExceptionResponse("Security issue");
	}

	@Override
	public Response handle(SubmitQuizCommand sqc) {
		System.out.println("SubmitQuizCommand");
		return submitQuiz(sqc);
	}

	@Override
	public Response handle(SubmitQuizSealedCommand sqsc) {
		System.out.println("SubmitQuizSealedCommand");
		return performSealed(sqsc);
	}

	private Response performSealed(SealedCommand sc) {
		User user = ServerArgs.getUser(sc.getUsername());
		if (user == null) {
			return new UsernameExceptionResponse("No such username");
		}
		Command c = (Command) getObject(sc.getSealedContent(), sc.getDigest(), user.getSharedSecret());
		if (c instanceof DownloadQuizCommand) {
			return downloadQuiz((DownloadQuizCommand) c);
		} else if (c instanceof HelloCommand) {
			return hello((HelloCommand) c);
		} else if (c instanceof LoginCommand) {
			return login((LoginCommand) c);
		} else if (c instanceof MonumentCommand) {
			return monuments((MonumentCommand) c);
		} else if (c instanceof RankingCommand) {
			return ranking((RankingCommand) c);
		} else if (c instanceof SubmitQuizCommand) {
			return submitQuiz((SubmitQuizCommand) c);
		}
		return new SecurityExceptionResponse("Security issue!");
	}

	private Response downloadQuiz(DownloadQuizCommand dqc) {
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

	private Response hello(HelloCommand hc) {
		System.out.println("Received: " + hc.getMessage());
		return new HelloResponse("Hi from Server!");
	}

	private Response login(LoginCommand lc) {
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

	private Response monuments(MonumentCommand mc) {
		return new MonumentResponse(getMonuments());
	}

	private Response ranking(RankingCommand rc) {
		ServerArgs.sortUsers();
		return new RankingResponse(getRanking());
	}

	private Response signUp(SignUpCommand suc) {
		if (ServerArgs.getUser(suc.getUser().getUsername()) != null) {
			return new UsernameExceptionResponse("Username already in use");
		} else if (!ServerArgs.getPasswords().contains(suc.getUser().getPassword())) {
			return new PasswordExceptionResponse("Not a valid code");
		}
		ServerArgs.removePassword(suc.getUser().getPassword());
		ServerArgs.addUser(suc.getUser());
		return new SignUpResponse(suc.getUser());
	}

	private Response submitQuiz(SubmitQuizCommand sqc) {
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

	private Object getObject(SealedObject sealedObject, byte[] digestObject, SecretKey key) {
		Object o = null;
		try {
			o = SecurityManager.decryptObject(sealedObject, key);
		} catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException | 
			InvalidKeyException | IOException e) {
			e.printStackTrace();
			return null;
		}
		if (SecurityManager.verifyDigest(o, digestObject, key)) {
			return o;
		}
		return null;
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
