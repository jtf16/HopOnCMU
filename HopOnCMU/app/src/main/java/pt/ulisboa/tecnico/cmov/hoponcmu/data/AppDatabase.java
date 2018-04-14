package pt.ulisboa.tecnico.cmov.hoponcmu.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.AnswerDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.MonumentDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.QuestionDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.QuizDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.TransactionDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.daos.UserDAO;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

@Database(version = 1, entities = {User.class, Monument.class, Quiz.class,
        Question.class, Answer.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Server";

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                AppDatabase.class).build();
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    // UserDAO is a class annotated with @Dao.
    public abstract UserDAO userDAO();

    // MonumentDAO is a class annotated with @Dao.
    public abstract MonumentDAO monumentDAO();

    // QuizDAO is a class annotated with @Dao.
    public abstract QuizDAO quizDAO();

    // QuestionDAO is a class annotated with @Dao.
    public abstract QuestionDAO questionDAO();

    // AnswerDAO is a class annotated with @Dao.
    public abstract AnswerDAO answerDAO();

    // TransactionDAO is a class annotated with @Dao.
    public abstract TransactionDAO transactionDAO();
}
