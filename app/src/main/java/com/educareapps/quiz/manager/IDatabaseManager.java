package com.educareapps.quiz.manager;


import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;

import java.util.ArrayList;

/**
 * Interface that provides methods for managing the database inside the Application.
 *
 * @author Octa
 */
public interface IDatabaseManager {

    /**
     * Closing available connections
     */
    void closeDbConnections();

    /**
     * Delete all tables and content from our database
     */
    void dropDatabase();



    /*
    * insertCSVQuestionTable
    * */

    CSVQuestionTable insertCSVQuestionTable(CSVQuestionTable csvQuestionTable);

    /*
* updateCsvQuestionTable
* */
    Long updateCsvQuestionTable(CSVQuestionTable csvQuestionTable);

    /*
   * listCSVQuestionTable
   * */
    ArrayList<CSVQuestionTable> listCSVQuestionTable();

    /*
* getCsvQuestionTableById
* */
    CSVQuestionTable getCsvQuestionTableById(long id);


      /*
    * insertQuestionSetTable
    * */

    QuestionSetTable insertQuestionSetTable(QuestionSetTable questionSetTable);

    /*
* updateQuestionSetTable
* */
    Long updateQuestionSetTable(QuestionSetTable questionSetTable);

    /*
   * listQuestionSetTable
   * */
    ArrayList<QuestionSetTable> listQuestionSetTable();

    /*
* getQuestionSetTableById
* */
    QuestionSetTable getQuestionSetTableById(long id);


      /*
    * insertLanguageTable
    * */

    LanguageTable insertLanguageTable(LanguageTable languageTable);

    /*
* updateLanguageTable
* */
    Long updateLanguageTable(LanguageTable languageTable);

    /*
   * listLanguageTable
   * */
    ArrayList<LanguageTable> listLanguageTable();

    /*
* getLanguageTableById
* */
    LanguageTable getLanguageTableById(long id);


}
