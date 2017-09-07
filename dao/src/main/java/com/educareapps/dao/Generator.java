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
        Entity user = addUserTable(schema);
        Entity language = addLanguageTable(schema);
        Entity questionSet = addQuestionSetTable(schema, language);
        Entity CSVQuestion = addCSVQuestionTable(schema, questionSet);
        Entity test = addTestTable(schema, questionSet);
        Entity leaderBoard = addLeaderBoardTable(schema, user,test);

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
        csvQuestionToQuestionSet.setName("csvQuestionToQuestionSet");


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
        question.addStringProperty("status");
        question.addStringProperty("created_at").notNull();

        return question;
    }


    private static Entity addUserTable(Schema schema) {
        Entity user = schema.addEntity("UserTable");
        user.addIdProperty().primaryKey().autoincrement();

        user.addLongProperty("user_id").notNull();
        user.addStringProperty("user_name").notNull();
        user.addStringProperty("email").notNull();
        user.addStringProperty("address").notNull();
        user.addStringProperty("occupation").notNull();
        user.addStringProperty("contact_no").notNull();
        user.addStringProperty("created_at").notNull();
        user.addStringProperty("status");

        return user;
    }


    private static Entity addLeaderBoardTable(Schema schema, Entity user, Entity test) {
        Entity leaderBoard = schema.addEntity("LeaderBoardTable");
        leaderBoard.addIdProperty().primaryKey().autoincrement();

        leaderBoard.addLongProperty("board_id").notNull();
        leaderBoard.addLongProperty("score").notNull();
        leaderBoard.addStringProperty("total_duration").notNull();
        leaderBoard.addStringProperty("negative").notNull();
        leaderBoard.addBooleanProperty("isHighscore").notNull();

        Property userProperty = leaderBoard.addLongProperty("user_id").notNull().getProperty();
        ToMany leaderBoardToUser = user.addToMany(leaderBoard, userProperty);
        leaderBoardToUser.setName("leaderBoardToUser");

        Property testProperty = leaderBoard.addLongProperty("test_id").notNull().getProperty();
        ToMany leaderBoardToTest = test.addToMany(leaderBoard, testProperty);
        leaderBoardToTest.setName("leaderBoardToTest");

        return leaderBoard;
    }


    private static Entity addTestTable(Schema schema, Entity questionSet) {
        Entity test = schema.addEntity("TestTable");
        test.addIdProperty().primaryKey().autoincrement();

        test.addLongProperty("test_id").notNull();
        test.addStringProperty("question_start_from").notNull();
        test.addStringProperty("question_start_to").notNull();
        test.addStringProperty("status");
        test.addStringProperty("created_at");

        Property questionSetProperty = test.addLongProperty("question_set_id").notNull().getProperty();
        ToMany testToQuestionSet = questionSet.addToMany(test, questionSetProperty);
        testToQuestionSet.setName("testToQuestionSet");

        return test;
    }


}
