package pt.ulisboa.tecnico.cmov.hoponcmu.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static AnswerOption fromAnswerOptionValue(int value) {
        if (value == AnswerOption.NULL.getValue()) {
            return null;
        } else if (value == AnswerOption.OPTION_A.getValue()) {
            return AnswerOption.OPTION_A;
        } else if (value == AnswerOption.OPTION_B.getValue()) {
            return AnswerOption.OPTION_B;
        } else if (value == AnswerOption.OPTION_C.getValue()) {
            return AnswerOption.OPTION_C;
        } else if (value == AnswerOption.OPTION_D.getValue()) {
            return AnswerOption.OPTION_D;
        } else {
            throw new IllegalArgumentException("Could not recognize option");
        }
    }

    @TypeConverter
    public static int answerOptionToValue(AnswerOption answerOption) {
        return answerOption == null ? AnswerOption.NULL.getValue() : answerOption.getValue();
    }
}
