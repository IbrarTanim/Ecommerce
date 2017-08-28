package com.educareapps.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

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
        Entity user = addUser(schema);
        Entity CSVQuestion = addCSVQuestionTable(schema);

    }

    private static Entity addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("name").notNull();
        user.addStringProperty("Age").notNull();
        user.addStringProperty("proPic").notNull();
        user.addIntProperty("active").notNull();
        user.addIntProperty("userId").notNull();
        return user;
    }


     private static Entity addCSVQuestionTable(Schema schema) {
        Entity csvQuestionTble = schema.addEntity("CSVQuestionTable");
         csvQuestionTble.addIdProperty().primaryKey().autoincrement();

         csvQuestionTble.addStringProperty("csv_id").notNull();
         csvQuestionTble.addStringProperty("ques_set_id").notNull();
         csvQuestionTble.addStringProperty("question").notNull();

         csvQuestionTble.addStringProperty("option_1").notNull();
         csvQuestionTble.addStringProperty("option_2").notNull();
         csvQuestionTble.addStringProperty("option_3").notNull();
         csvQuestionTble.addStringProperty("option_4").notNull();

         csvQuestionTble.addStringProperty("answer").notNull();

        return csvQuestionTble;
    }

}
