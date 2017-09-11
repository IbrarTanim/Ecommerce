package com.educareapps.quiz.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.CSVQuestionTableDao;
import com.educareapps.quiz.dao.DaoMaster;
import com.educareapps.quiz.dao.DaoSession;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.LanguageTableDao;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.dao.LeaderBoardTableDao;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.dao.QuestionSetTableDao;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.dao.TestTableDao;
import com.educareapps.quiz.dao.UserTable;
import com.educareapps.quiz.dao.UserTableDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * @author Octa
 */
public class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "sample-database", null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    /**
     * @param context The Android {@link Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
//            asyncSession.deleteAll(User.class);    // clear all elements from a table
//            asyncSession.deleteAll(Task.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************************************
     * CRUIZE Table Operation
     ************************************/


    /************************************************ CSVQuestionTable **********************************************************/

    @Override
    public CSVQuestionTable insertCSVQuestionTable(CSVQuestionTable csvQuestionTable) {
        try {
            if (csvQuestionTable != null) {
                openWritableDb();
                CSVQuestionTableDao csvQuestionTableDao = daoSession.getCSVQuestionTableDao();
                csvQuestionTableDao.insert(csvQuestionTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return csvQuestionTable;
    }

    @Override
    public Long updateCsvQuestionTable(CSVQuestionTable csvQuestionTable) {
        Long csvQuestionKey = null;
        try {
            if (csvQuestionTable != null) {
                openWritableDb();
                daoSession.update(csvQuestionTable);
                csvQuestionKey = csvQuestionTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return csvQuestionKey;
    }


    @Override
    public ArrayList<CSVQuestionTable> listCSVQuestionTable() {
        List<CSVQuestionTable> listCSVCsvQuestionTable = null;
        try {
            openReadableDb();
            CSVQuestionTableDao csvQuestionTableDao = daoSession.getCSVQuestionTableDao();
            listCSVCsvQuestionTable = csvQuestionTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (listCSVCsvQuestionTable != null) {
            return new ArrayList<>(listCSVCsvQuestionTable);
        }

        return null;
    }

    @Override
    public CSVQuestionTable getCsvQuestionTableById(long id) {
        CSVQuestionTable csvQuestionTable = null;
        try {
            openWritableDb();
            CSVQuestionTableDao csvQuestionTableDao = daoSession.getCSVQuestionTableDao();
            csvQuestionTable = csvQuestionTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return csvQuestionTable;
    }

    @Override
    public synchronized boolean deleteCSVQuestionById(Long id) {
        try {
            openWritableDb();
            CSVQuestionTableDao csvQuestionTableDao = daoSession.getCSVQuestionTableDao();
            csvQuestionTableDao.deleteByKey(id);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /************************************************ QuestionSetTable **********************************************************/

    @Override
    public long insertQuestionSetTable(QuestionSetTable questionSetTable) {
        long id = 0;
        try {
            if (questionSetTable != null) {
                openWritableDb();
                QuestionSetTableDao questionSetTableDao = daoSession.getQuestionSetTableDao();
                id = questionSetTableDao.insert(questionSetTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            id = 0;
        }

        return id;
    }

    @Override
    public Long updateQuestionSetTable(QuestionSetTable questionSetTable) {
        Long questionSetKey = null;
        try {
            if (questionSetTable != null) {
                openWritableDb();
                daoSession.update(questionSetTable);
                questionSetKey = questionSetTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return questionSetKey;
    }

    @Override
    public ArrayList<QuestionSetTable> listQuestionSetTable() {
        List<QuestionSetTable> listQuestionSetTable = null;

        try {
            openReadableDb();
            QuestionSetTableDao questionSetTableDao = daoSession.getQuestionSetTableDao();
            listQuestionSetTable = questionSetTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (listQuestionSetTable != null) {
            return new ArrayList<>(listQuestionSetTable);
        }

        return null;
    }


    @Override
    public QuestionSetTable getQuestionSetTableById(long id) {

        QuestionSetTable questionSetTable = null;
        try {
            openWritableDb();
            QuestionSetTableDao questionSetTableDao = daoSession.getQuestionSetTableDao();
            questionSetTable = questionSetTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return questionSetTable;
    }


    @Override
    public synchronized boolean deleteQuestionSetById(Long userId) {
        try {
            openWritableDb();
            QuestionSetTableDao questionSetTableDao = daoSession.getQuestionSetTableDao();
            questionSetTableDao.deleteByKey(userId);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /************************************************ LanguageTable **********************************************************/

    @Override
    public long insertLanguageTable(LanguageTable languageTable) {
        long id = 0;
        try {
            if (languageTable != null) {
                openWritableDb();
                LanguageTableDao languageTableDao = daoSession.getLanguageTableDao();
                id = languageTableDao.insert(languageTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Long updateLanguageTable(LanguageTable languageTable) {

        Long languageKey = null;
        try {
            if (languageTable != null) {
                openWritableDb();
                daoSession.update(languageTable);
                languageKey = languageTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return languageKey;
    }

    @Override
    public ArrayList<LanguageTable> listLanguageTable() {
        List<LanguageTable> listLanguageTable = null;
        try {
            openReadableDb();
            LanguageTableDao languageTableDao = daoSession.getLanguageTableDao();
            listLanguageTable = languageTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (listLanguageTable != null) {
            return new ArrayList<>(listLanguageTable);
        }
        return null;
    }

    @Override
    public LanguageTable getLanguageTableById(long id) {
        LanguageTable languageTable = null;

        try {
            openWritableDb();
            LanguageTableDao languageTableDao = daoSession.getLanguageTableDao();
            languageTable = languageTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return languageTable;
    }

    @Override
    public synchronized boolean deleteLanguageById(Long id) {
        try {
            openWritableDb();
            LanguageTableDao languageTableDao = daoSession.getLanguageTableDao();
            languageTableDao.deleteByKey(id);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /************************************************ UserTable **********************************************************/

    @Override
    public long insertUserTable(UserTable userTable) {
        long id = 0;
        try {
            if (userTable != null) {
                openWritableDb();
                UserTableDao userTableDao = daoSession.getUserTableDao();
                id = userTableDao.insert(userTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Long updateUserTable(UserTable userTable) {
        Long userKey = null;
        try {
            if (userTable != null) {
                openWritableDb();
                daoSession.update(userTable);
                userKey = userTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return userKey;
    }

    @Override
    public ArrayList<UserTable> listUserTable() {

        List<UserTable> listUserTable = null;
        try {
            openReadableDb();
            UserTableDao userTableDao = daoSession.getUserTableDao();
            listUserTable = userTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (listUserTable != null) {
            return new ArrayList<>(listUserTable);
        }

        return null;
    }

    @Override
    public UserTable getUserTableById(long id) {
        UserTable userTable = null;
        try {
            openWritableDb();
            UserTableDao userTableDao = daoSession.getUserTableDao();
            userTable = userTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return userTable;
    }

    @Override
    public synchronized boolean deleteUserById(Long id) {
        try {
            openWritableDb();
            UserTableDao userTableDao = daoSession.getUserTableDao();
            userTableDao.deleteByKey(id);
            daoSession.clear();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public UserTable getUserByServerUserID(long user_id) {
        UserTable userTable = null;
        try {
            openWritableDb();
            UserTableDao userTableDao = daoSession.getUserTableDao();
            QueryBuilder<UserTable> queryBuilder = userTableDao.queryBuilder().where(UserTableDao.Properties.User_id.eq(user_id));
            List<UserTable> users = queryBuilder.list();
           // List<UserTable> users2 = userTableDao.loadAll();
            if (users.size() > 0) {
                userTable = users.get(0);
            }
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return userTable;
    }

    /************************************************ TestTable **********************************************************/

    @Override
    public long insertTestTable(TestTable testTable) {
        long id = 0;
        try {
            if (testTable != null) {
                openWritableDb();
                TestTableDao testTableDao = daoSession.getTestTableDao();
                id = testTableDao.insert(testTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Long updateTestTable(TestTable testTable) {
        Long testKey = null;
        try {
            if (testTable != null) {
                openWritableDb();
                daoSession.update(testTable);
                testKey = testTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return testKey;
    }

    @Override
    public ArrayList<TestTable> listTestTable() {

        List<TestTable> listTestTable = null;

        try {
            openReadableDb();
            TestTableDao testTableDao = daoSession.getTestTableDao();
            listTestTable = testTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (listTestTable != null) {
            return new ArrayList<>(listTestTable);
        }

        return null;
    }

    @Override
    public TestTable getTestTableById(long id) {

        TestTable testTable = null;
        try {
            openWritableDb();
            TestTableDao testTableDao = daoSession.getTestTableDao();
            testTable = testTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return testTable;
    }

    @Override
    public synchronized boolean deleteTestById(Long id) {

        try {
            openWritableDb();
            TestTableDao testTableDao = daoSession.getTestTableDao();
            testTableDao.deleteByKey(id);
            daoSession.clear();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }

    }

    /************************************************ LeaderBoardTable **********************************************************/

    @Override
    public long insertLeaderBoardTable(LeaderBoardTable leaderBoardTable) {
        long id = 0;
        try {
            if (leaderBoardTable != null) {
                openWritableDb();
                LeaderBoardTableDao leaderBoardTableDao = daoSession.getLeaderBoardTableDao();
                id = leaderBoardTableDao.insert(leaderBoardTable);
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Long updateLeaderBoardtTable(LeaderBoardTable leaderBoardTable) {
        Long leaderBoardKey = null;
        try {
            if (leaderBoardTable != null) {
                openWritableDb();
                daoSession.update(leaderBoardTable);
                leaderBoardKey = leaderBoardTable.getId();
                daoSession.clear();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return leaderBoardKey;
    }

    @Override
    public ArrayList<LeaderBoardTable> listLeaderBoardTable() {

        List<LeaderBoardTable> listLeaderBoardTable = null;

        try {
            openReadableDb();
            LeaderBoardTableDao leaderBoardTableDao = daoSession.getLeaderBoardTableDao();
            listLeaderBoardTable = leaderBoardTableDao.loadAll();
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (listLeaderBoardTable != null) {
            return new ArrayList<>(listLeaderBoardTable);
        }
        return null;
    }

    @Override
    public LeaderBoardTable getLeaderBoardTableById(long id) {

        LeaderBoardTable leaderBoardTable = null;

        try {
            openWritableDb();
            LeaderBoardTableDao leaderBoardTableDao = daoSession.getLeaderBoardTableDao();
            leaderBoardTable = leaderBoardTableDao.load(id);
            daoSession.clear();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return leaderBoardTable;
    }

    @Override
    public synchronized boolean deleteLeaderBoardById(Long id) {

        try {
            openWritableDb();
            LeaderBoardTableDao leaderBoardTableDao = daoSession.getLeaderBoardTableDao();
            leaderBoardTableDao.deleteByKey(id);
            daoSession.clear();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }


}
