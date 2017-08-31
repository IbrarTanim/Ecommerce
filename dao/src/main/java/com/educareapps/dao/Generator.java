package com.educareapps.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class Generator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.educareapps.quiz.dao");
        addTables(schema);
        new DaoGenerator().generateAll(schema, OUT_DIR);
    }

    private static void addTables(Schema schema) {
        /* entities */
        Entity language = addLanguageTable(schema);
        Entity questionSet = addQuestionSetTable(schema, language);
        Entity CSVQuestion = addCSVQuestionTable(schema, questionSet);


    }


    private static Entity addCSVQuestionTable(Schema schema, Entity questionSet) {
        Entity csvQuestionTble = schema.addEntity("CSVQuestionTable");
        csvQuestionTble.addIdProperty().primaryKey().autoincrement();

        csvQuestionTble.addLongProperty("question_id").notNull();
        csvQuestionTble.addStringProperty("question").notNull();
        csvQuestionTble.addStringProperty("option_one").notNull();
        csvQuestionTble.addStringProperty("option_two").notNull();
        csvQuestionTble.addStringProperty("option_three").notNull();
        csvQuestionTble.addStringProperty("option_four").notNull();
        csvQuestionTble.addStringProperty("answer").notNull();

        Property questionSetProperty = csvQuestionTble.addLongProperty("question_set_id").notNull().getProperty();
        ToMany csvQuestionToQuestionSet = questionSet.addToMany(csvQuestionTble, questionSetProperty);
        csvQuestionToQuestionSet.setName("questionSetToLanguage");


        return csvQuestionTble;
    }


    private static Entity addQuestionSetTable(Schema schema, Entity language) {
        Entity questionSet = schema.addEntity("QuestionSetTable");
        questionSet.addIdProperty().primaryKey().autoincrement();

        questionSet.addLongProperty("question_set_id").notNull();
        questionSet.addStringProperty("question_set").notNull();
        questionSet.addStringProperty("title").notNull();
        questionSet.addStringProperty("photo").notNull();
        questionSet.addStringProperty("created_at").notNull();


        Property questionSetProperty = questionSet.addLongProperty("lang_id").notNull().getProperty();
        ToMany questionSetToLanguage = language.addToMany(questionSet, questionSetProperty);
        questionSetToLanguage.setName("questionSetToLanguage");


        return questionSet;
    }

    private static Entity addLanguageTable(Schema schema) {
        Entity question = schema.addEntity("LanguageTable");
        question.addIdProperty().primaryKey().autoincrement();

        question.addLongProperty("lang_id").notNull();
        question.addStringProperty("lang_name").notNull();
        question.addStringProperty("status").notNull();
        question.addStringProperty("created_at").notNull();

       /* Property languageProperty = question.addLongProperty("question_set_id").notNull().getProperty();
        ToMany languageToquestionSet = questionSet.addToMany(question, languageProperty);
        languageToquestionSet.setName("questoinSetToLanguage");*/

        return question;
    }


}
