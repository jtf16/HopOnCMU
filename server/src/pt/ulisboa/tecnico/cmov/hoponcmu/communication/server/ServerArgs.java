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

import javax.crypto.*;
import javax.crypto.spec.*;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;

public class ServerArgs {
	
	private static String EXPRESSION = "^(?<deg>[-+0-9]+)[^0-9]+(?<min>[0-9]+)[^0-9]+(?<sec>[0-9.,]+)[^0-9.,ENSW]+(?<pos>[ENSW]*)$";

	// USERS
	static User user1 = new User("a", "a", "a", "a", "a", 0, 0);
	static User user2 = new User("b", "b", "b", "b", "b", 0, 0);
	static User user3 = new User("c", "c", "c", "c", "c", 0, 0);

	// MONUMENTS
    static Monument m1 = new Monument("M1", "Mosteiro dos Jerónimos", 
    	parseLatLonValue("38º41'50.79\"N"), parseLatLonValue("9º12'20.97\"W"));
    static Monument m2 = new Monument("M2", "Mosteiro da Batalha", 
    	parseLatLonValue("39º39'36.41\"N"), parseLatLonValue("8º49'31.60\"W"));
    static Monument m3 = new Monument("M3", "Torre de Belém", 
    	parseLatLonValue("38°41'17.39\"N"), parseLatLonValue("9°12'34.20\"W"));
    static Monument m4 = new Monument("M4", "Igreja de São Francisco", 
    	parseLatLonValue("38°34'8.40\"N"), parseLatLonValue("7°54'32.40\"W"));
    static Monument m5 = new Monument("M5", "Palácio Nacional de Queluz", 
    	parseLatLonValue("38º45'01.81\"N"), parseLatLonValue("9º15'28.55\"W"));
    static Monument m6 = new Monument("M6", "Panteão Nacional", 
    	parseLatLonValue("38º42'53.12\"N"), parseLatLonValue("9º07'30.55\"W"));
    static Monument m7 = new Monument("M7", "Padrão dos Descobrimentos", 
    	parseLatLonValue("38º41'37.69\"N"), parseLatLonValue("9º12'20.95\"W"));
    static Monument m8 = new Monument("M8", "Basílica da Estrela", 
    	parseLatLonValue("38º42'48.7\"N"), parseLatLonValue("9º09'38.21\"W"));
    static Monument m9 = new Monument("M9", "Cristo Rei", 
    	parseLatLonValue("38°40'25.79\"N"), parseLatLonValue("9°10'10.20\"W"));
    static Monument m10 = new Monument("M10", "Menir de São Paio de Antas", 
    	parseLatLonValue("41°33'21\"N"), parseLatLonValue("8°45'42\"W"));
    static Monument m11 = new Monument("M11", "Fonte Mourisca", 
    	parseLatLonValue("40°47'16\"N"), parseLatLonValue("7°30'24\"W"));
    static Monument m12 = new Monument("M12", "Castelo de Vinhais", 
    	parseLatLonValue("41°50'8\"N"), parseLatLonValue("7°0'5\"W"));
    static Monument m13 = new Monument("M13", "Santuário de Panóias", 
    	parseLatLonValue("41°16'59.1\"N"), parseLatLonValue("7°40'57.6\"W"));
    static Monument m14 = new Monument("M14", "Monumento aos Combatentes do Ultramar", 
    	parseLatLonValue("38°41'32.21\"N"), parseLatLonValue("9°12'54.59\"W"));
    static Monument m15 = new Monument("M15", "Muralha Primitiva", 
    	parseLatLonValue("41°8'34.8\"N"), parseLatLonValue("8°36'32.7\"W"));
    static Monument m16 = new Monument("M16", "Feitoria Inglesa", 
    	parseLatLonValue("41°8'29.2\"N"), parseLatLonValue("8°36'49.4\"W"));
    static Monument m17 = new Monument("M17", "Forte de São Pedro do Estoril", 
    	parseLatLonValue("38°42'15\"N"), parseLatLonValue("9°23'44\"W"));
    static Monument m18 = new Monument("M18", "Teatro Nacional São João", 
    	parseLatLonValue("41°08'51\"N"), parseLatLonValue("8°36'34\"W"));
    static Monument m19 = new Monument("M19", "Castelo do Sabugal", 
    	parseLatLonValue("40°21'7\"N"), parseLatLonValue("7°5'22\"W"));
    static Monument m20 = new Monument("M20", "Ponte da Ribeira de Cobres", 
    	parseLatLonValue("37°30'42.1\"N"), parseLatLonValue("8°3'17.6\"W"));
    static Monument m21 = new Monument("M21", "Capelas Imperfeitas", 
    	parseLatLonValue("39º39'36.41\"N"), parseLatLonValue("8º49'31.60\"W"));
    static Monument m22 = new Monument("M22", "Monumento aos Mortos da Grande Guerra", 
    	parseLatLonValue("39º45'04.449\"N"), parseLatLonValue("8º55'48.782\"W"));
    static Monument m23 = new Monument("M23", "Capela dos Ossos", 
    	parseLatLonValue("38º34'08.03\"N"), parseLatLonValue("7º54'29.31\"W"));

    // QUIZZES
	static Quiz quiz1 = new Quiz(1, m1.getId(), "Quiz1");
	static Quiz quiz2 = new Quiz(2, m1.getId(), "Quiz2");
	static Quiz quiz3 = new Quiz(3, m1.getId(), "Quiz3");
	static Quiz quiz4 = new Quiz(4, m2.getId(), "Quiz1");
	static Quiz quiz5 = new Quiz(5, m2.getId(), "Quiz2");
	static Quiz quiz6 = new Quiz(6, m2.getId(), "Quiz3");
	static Quiz quiz7 = new Quiz(7, m3.getId(), "Quiz1");
	static Quiz quiz8 = new Quiz(8, m3.getId(), "Quiz2");
	static Quiz quiz9 = new Quiz(9, m3.getId(), "Quiz3");
	static Quiz quiz10 = new Quiz(10, m4.getId(), "Quiz1");
	static Quiz quiz11 = new Quiz(11, m4.getId(), "Quiz2");
	static Quiz quiz12 = new Quiz(12, m4.getId(), "Quiz3");
	static Quiz quiz13 = new Quiz(13, m5.getId(), "Quiz1");
	static Quiz quiz14 = new Quiz(14, m5.getId(), "Quiz2");
	static Quiz quiz15 = new Quiz(15, m5.getId(), "Quiz3");
	static Quiz quiz16 = new Quiz(16, m6.getId(), "Quiz1");
	static Quiz quiz17 = new Quiz(17, m6.getId(), "Quiz2");
	static Quiz quiz18 = new Quiz(18, m6.getId(), "Quiz3");
	static Quiz quiz19 = new Quiz(19, m7.getId(), "Quiz1");
	static Quiz quiz20 = new Quiz(20, m7.getId(), "Quiz2");
	static Quiz quiz21 = new Quiz(21, m7.getId(), "Quiz3");

	// QUESTIONS
	// Torre de belem
	static Question q7question1 = new Question(
			"In what year was the beginning of it's construction?",
			"1511", "1512", "1514", "1517", AnswerOption.OPTION_C);
	static Question q7question2 = new Question(
			"In what year was the end of it's construction?",
			"1519", "1522", "1523", "1525", AnswerOption.OPTION_A);
	static Question q7question3 = new Question(
			"Which King ordered the \"making of a strong fort\"?",
			"John II", "Afonso V", "John III", "Manuel I", AnswerOption.OPTION_A);
	static Question q7question4 = new Question(
			"Who was the King when the construction was initiated?",
			"John II", "Afonso V", "John III", "Manuel I", AnswerOption.OPTION_D);

	public static List<String> passwords = new ArrayList<String>(Arrays.asList("d", "e", "f"));
	public static List<User> users = new ArrayList<User>(){{
			add(user1);
			add(user2);
			add(user3);
		}};

	public static final HashMap<String, Monument> monuments;
	static
    {
        monuments = new HashMap<String, Monument>();
        monuments.put(m1.getId(), m1);
        monuments.put(m2.getId(), m2);
        monuments.put(m3.getId(), m3);
        monuments.put(m4.getId(), m4);
        monuments.put(m5.getId(), m5);
        monuments.put(m6.getId(), m6);
        monuments.put(m7.getId(), m7);
        monuments.put(m8.getId(), m8);
        monuments.put(m9.getId(), m9);
        monuments.put(m10.getId(), m10);
        monuments.put(m11.getId(), m11);
        monuments.put(m12.getId(), m12);
        monuments.put(m13.getId(), m13);
        monuments.put(m14.getId(), m14);
        monuments.put(m15.getId(), m15);
        monuments.put(m16.getId(), m16);
        monuments.put(m17.getId(), m17);
        monuments.put(m18.getId(), m18);
        monuments.put(m19.getId(), m19);
        monuments.put(m20.getId(), m20);
        monuments.put(m21.getId(), m21);
        monuments.put(m22.getId(), m22);
        monuments.put(m23.getId(), m23);
    }
	public static final HashMap<String, List<Quiz>> quizzes;
	static
	{
		quizzes = new HashMap<String, List<Quiz>>();
		quizzes.put(m1.getId(), new ArrayList<Quiz>(){{
			add(quiz1);
			add(quiz2);
			add(quiz3);
		}});
		quizzes.put(m2.getId(), new ArrayList<Quiz>(){{
			add(quiz4);
			add(quiz5);
			add(quiz6);
		}});
		quizzes.put(m3.getId(), new ArrayList<Quiz>(){{
			add(quiz7);
			add(quiz8);
			add(quiz9);
		}});
		quizzes.put(m4.getId(), new ArrayList<Quiz>(){{
			add(quiz10);
			add(quiz11);
			add(quiz12);
		}});
		quizzes.put(m5.getId(), new ArrayList<Quiz>(){{
			add(quiz13);
			add(quiz14);
			add(quiz15);
		}});
	}
	public static final HashMap<Long, List<Question>> questions;
	static
	{
		questions = new HashMap<Long, List<Question>>();
		questions.put(quiz7.getId(), new ArrayList<Question>(){{
			add(q7question1);
			add(q7question2);
			add(q7question3);
			add(q7question4);
		}});
	}
    public static final HashMap<Long, List<String>> usersAnswers = new HashMap<Long, List<String>>();
    public static long sessionId = 0;
    public static HashMap<Long, String> sessionIds = new HashMap<Long, String>();
    public static HashMap<String, SecretKey> userKeys = new HashMap<String, SecretKey>();

	public static List<User> getUsers() {
		return users;
	}

	public static User getUser(String name) {
		for (User user : users){
			if (user.getUsername().equals(name)) {
				return user;
			}
		}
		return null;
	}

	public static void addUser(User user) {
		users.add(user);
        sortUsers();
	}

	public static void sortUsers() {
        users.sort(Comparator.comparingInt(User::getScore).reversed().thenComparing(User::getTime));
		int previousRank = 1, rank = 1, previousScore = -1;
        long previousTime = -1;
		for (User user : users){
			if (user.getScore() == previousScore && user.getTime() == previousTime) {
				user.setRanking(previousRank);
			} else {
				user.setRanking(rank);
				previousScore = user.getScore();
                previousTime = user.getTime();
				previousRank = rank;
			}
			rank++;
		}
	}

    public static SecretKey getUserKey(String name) {
        return userKeys.get(name);
    }

    public static void addUserKey(String username, SecretKey key) {
        userKeys.put(username, key);
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

	public static HashMap<String, List<Quiz>> getQuizzes() {
		return quizzes;
	}

	public static HashMap<Long, List<Question>> getQuestions() {
		return questions;
	}

    public static HashMap<Long, List<String>> getUsersAnswers() {
        return usersAnswers;
    }

    public static HashMap<Long, String> getSessionIds() {
        return sessionIds;
    }

    public static User getUserBySessionId(long sessionId) {
        return getUser(sessionIds.get(sessionId));
    }

    public static boolean isCorrectSessionID(String username, long sessionId) {
        return sessionIds.get(sessionId) != null && sessionIds.get(sessionId).equals(username);
    }

    public static Long addSessionId(String username) {
        sessionIds.put(++sessionId, username);
        return sessionId;
    }

    public static boolean isUserInQuizAnswers(Long id, String username) {
        if (usersAnswers.get(id) == null) {
            return false;
        } else if (usersAnswers.get(id).contains(username)) {
            return true;
        } else {
            return false;
        }
    }

    public static void addUsersAnswers(Long id, String username) {
        if (usersAnswers.get(id) == null) {
            usersAnswers.put(id, new ArrayList<String>(){{add(username);}});
        } else {
            usersAnswers.get(id).add(username);
        }
        sortUsers();
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
