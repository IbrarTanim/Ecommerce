package com.educareapps.quiz.manager;


import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.dao.UserTable;

import java.util.ArrayList;
import java.util.List;

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


    /************************************************ CSVQuestionTable **********************************************************/

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
   * deleteQuestionById
   * */
    boolean deleteCSVQuestionById(Long id);


    /************************************************ QuestionSetTable **********************************************************/

      /*
    * insertQuestionSetTable
    * */

    long insertQuestionSetTable(QuestionSetTable questionSetTable);

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
* deleteQuestionSetById
* */
    boolean deleteQuestionSetById(Long id);


    /************************************************ LanguageTable **********************************************************/

      /*
    * insertLanguageTable
    * */

    long insertLanguageTable(LanguageTable languageTable);

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

    /*
    * deleteLanguageById
    * */
    boolean deleteLanguageById(Long id);


    /************************************************ UserTable**********************************************************/

      /*
    * insertUserTable
    * */

    long insertUserTable(UserTable userTable);

    /*
   * updateUserTable
    * */
    Long updateUserTable(UserTable userTable);

    /*
   * listUserTable
   * */
    ArrayList<UserTable> listUserTable();


    /*
   * getUserTableById
    * */
    UserTable getUserTableById(long id);


    /*
    * deleteLanguageById
    * */
    boolean deleteUserById(Long id);

    UserTable getUserByServerUserID(long user_id);

    List<LeaderBoardTable> getLeaderBOardByServerUserID(long sever_user_id);

    /************************************************ TestTable**********************************************************/

      /*
    * insertTestTable
    * */

    long insertTestTable(TestTable testTable);

    /*
   * updateTestTable
    * */
    Long updateTestTable(TestTable testTable);


    /*
   * listTestTable
   * */
    ArrayList<TestTable> listTestTable();


    /*
   * getTestTableById
    * */
    TestTable getTestTableById(long id);

    TestTable getTestTableByServerTestId(long server_TestID);


    /*
    * deleteTestById
    * */
    boolean deleteTestById(Long id);


    /************************************************ LeaderBoardTable**********************************************************/

      /*
    * insertLeaderBoardTable
    * */
    long insertLeaderBoardTable(LeaderBoardTable leaderBoardTable);

    /*
   * updateLeaderBoardtTable
    * */
    Long updateLeaderBoardtTable(LeaderBoardTable leaderBoardTable);


    /*
   * listLeaderBoardTable
   * */
    ArrayList<LeaderBoardTable> listLeaderBoardTable();

    /*
   * getLeaderBoardTableById
    * */
    LeaderBoardTable getLeaderBoardTableById(long id);


    /*
    * deleteLeaderBoardById
    * */
    boolean deleteLeaderBoardById(Long id);

    LeaderBoardTable getLeaderBoardByUserID(long user_id, long testID);
}
