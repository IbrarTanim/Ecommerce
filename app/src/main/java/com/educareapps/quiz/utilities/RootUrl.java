package com.educareapps.quiz.utilities;

/**
 * Created by RK-REAZ on 8/6/2017.
 */

public class RootUrl {
    public final static String RootUrl = "http://192.52.243.6/Quiz/";
    public final static String QuizUrl = "http://192.52.243.6/Quiz/QuestionSet/GetAllQuiz";

    public static final String LOGIN_URL = RootUrl + "Users/Login/";
    public static final String REGISTRATION_URL = RootUrl + "Users/Registration/";
    public static final String UPDATE_LEADER_BOARD_URL = RootUrl + "Users/UpdateLeaderBoard";
    public static final String INSERT_LEADER_BOARD_URL = RootUrl + "Users/InsertLeaderBoard";
    public static final String GET_LEADER_BOARDS_FOR_USER_URL = RootUrl + "Users/GetLeaderboardByUserID";
}
