package pt.ulisboa.tecnico.cmov.hoponcmu.data;

import android.content.Context;

import java.util.Random;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.MonumentRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.TransactionRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;

public class DatabaseCreator {

    public static String[] monumentNames = new String[]{
            "Mosteiro dos Jerónimos", "Mosteiro da Batalha",
            "Torre de Belém", "Igreja de São Francisco",
            "Palácio Nacional de Queluz", "Panteão Nacional",
            "Padrão dos Descobrimentos", "Basílica da Estrela",
            "Convento de Santa Clara", "Chã de Parada",
            "Cristo Rei", "Menir de São Paio de Antas",
            "Fonte Mourisca", "Castelo de Vinhais",
            "Igreja da Venerável Ordem Terceira de São Francisco", "Santuário de Panóias",
            "Anta da Estria", "Igreja de Santa Maria",
            "Monumento aos Combatentes do Ultramar", "Muralha Primitiva",
            "Feitoria Inglesa", "Forte de São Pedro do Estoril",
            "Teatro Nacional São João", "Miradouro da Senhora do Monte",
            "Castelo do Sabugal", "Ponte da Ribeira de Cobres",
            "Capelas Imperfeitas", "Monumento aos Mortos da Grande Guerra",
            "Capela dos Ossos"
    };
    public static String[] usernames = new String[]{
            "sample1", "sample2", "sample3", "sample4", "sample5",
            "sample6", "sample7", "sample8", "sample9", "sample10",
            "sample11", "sample12", "sample13", "sample14", "sample15",
            "sample16", "sample17", "sample18", "sample19", "sample20"

    };
    public static String[] quizNames = new String[]{
            "Quiz 1", "Quiz 2", "Quiz 3", "Quiz 4", "Quiz 5", "Quiz 6",
            "Quiz 7", "Quiz 8", "Quiz 9", "Quiz 10", "Quiz 11", "Quiz 12",
            "Quiz 13", "Quiz 14", "Quiz 15", "Quiz 16", "Quiz 17", "Quiz 18",
            "Quiz 19", "Quiz 20", "Quiz 21", "Quiz 22", "Quiz 23", "Quiz 24",
            "Quiz 25", "Quiz 26", "Quiz 27", "Quiz 28", "Quiz 29", "Quiz 30"
    };
    public static Question[] questions = new Question[]{
            new Question("In what year was the monument built?",
                    "1989", "1985", "1976", "1982"),
            new Question("Who built the monument?",
                    "Jonh", "Peter", "James", "Carlos"),
            new Question("What kind of monument is it?",
                    "Church", "Castle", "Palace", "Fort")
    };
    private UserRepository userRepository;
    private MonumentRepository monumentRepository;
    private TransactionRepository transactionRepository;

    public DatabaseCreator(Context context, int numUsers, int numMonuments) {
        userRepository = new UserRepository(context);
        monumentRepository = new MonumentRepository(context);
        transactionRepository = new TransactionRepository(context);
        createRandomUsers(numUsers);
        createRandomMonumentQuizzes(numMonuments);
    }

    private void createRandomUsers(int numUsers) {
        User tempUser;
        int usernamesRange = usernames.length;
        Random random = new Random();
        for (int i = 0; i < numUsers; i++) {
            tempUser = new User();
            tempUser.setUsername(usernames[random.nextInt(usernamesRange)]);
            tempUser.setScore(50 * (numUsers - i));
            tempUser.setRanking(i + 1);
            userRepository.insertUser(tempUser);
        }
    }

    private void createRandomMonuments(int numMonuments) {
        Monument tempMonument;
        int monumentsRange = monumentNames.length;
        Random random = new Random();

        for (int i = 0; i < numMonuments; i++) {
            tempMonument = new Monument();
            tempMonument.setName(monumentNames[random.nextInt(monumentsRange)]);
            tempMonument.setLatitude(50 * random.nextInt(100));
            monumentRepository.insertMonument(tempMonument);
        }
    }

    private void createRandomMonumentQuizzes(int numMonuments) {
        Monument tempMonument;
        int monumentsRange = monumentNames.length;
        Quiz tempQuiz;
        int quizzesRange = quizNames.length;
        Random random = new Random();

        for (int i = 0; i < numMonuments; i++) {
            tempMonument = new Monument();
            tempMonument.setName(monumentNames[random.nextInt(monumentsRange)]);
            tempMonument.setLatitude(50 * random.nextInt(100));
            Quiz[] quizzes = new Quiz[4];
            int numberOfQuizzes = random.nextInt(4);
            for (int j = 0; j < numberOfQuizzes; j++) {
                tempQuiz = new Quiz();
                tempQuiz.setName(quizNames[random.nextInt(quizzesRange)]);
                quizzes[j] = tempQuiz;
            }
            transactionRepository.insertMonumentAndQuizzes(tempMonument, quizzes);
        }
    }
}
