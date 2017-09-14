package com.educareapps.quiz.utilities;

/**
 * Created by Rakib on 9/14/2017.
 */

public class Documentation {
    /*
    *
    * LOGIN FLOW

    1. take user name and password and hit the server then for login mismatch.
    2. after successful matching found it return success response and then insert the user id in local sharedpreference
    3. then it take user to in the dashboard screen.
    4. if the user after  creating new acc and delete the application and
    then that again install the app and try to login then match user information with server and return response
    and the user information from server and the app saved it in the local database.
    * */
    /*
    * REGISTRATION FLOW
    *
    * 1. take the user information if ther user is already not exist it register first to the server database
     * then return success response from server and insert the user information into local database.
    * */

    /*
    *
    * DASHBOARD FLOW
    * 1. showing user email and other information form local database.
    * 2. no logout option here
    * 3. showing leaderboard for the specific logged in user in descending playing order
    * 4. leaderboard are showing here by qurying using server user_id and server test_id not the local one.
    * (must be using server userid and test id)
    * 5. one start button here it navigate user to ther subject list area
    * */

    /*
    *
    * SUBJECT LIST FLOW
    * 1. take all the question set form our local database that fill in splash screen and display in griview using custom adapter
    * 2. per subject has one click listener for going to browse specific subject test.
    * 3. send question_set_id to load test through intent in the TestListActivity.
    * */

    /*
    *
    * TEST LIST FLOW
    *
    * 1. user selected subject (questionset) id means local database question_set_id come here through intent to show that
    * questions_set_id wise test list
    * 2. per test has one click listener
    * 3. when user clicked a test it pass its test_id for playing under this test question in the testplayer activity
    * */


    /*
    * TEST PLAYING FLOW
    * 1. play test that user selected for playing.
    * 2. timer is functional also here.
    * 3. if time is over the test will autometically closed and show statistic to ther user
    * 4. select an option to play next question flow is functional here
    * 5. back to previous functionality available here.
    * 6. after successful test complete it pass several information through intent like
    * total_played, total_duration, correctanwerlist,wronganswerlist, test_id, user_id,score
    *
    * */

    /*
    *
    * RESULT SHOWING FLOW
    *
    * 1. it save the test summuray in the leaderboard table
    * by passsing server_user_id(not local ledearboad table id) and server_test_id(not local ledearboad table id) in
    * the local database. then it request server to save the user leaderboad in the server also using the server_user_id and server_test _id
    * 2. show several information like
    * total_played, total_duration, correctanwerlist,wronganswerlist, test_id, user_id,score.
    * 3. show wrongquestion in the dialog listview fuctionality available here(show with correct sign )
    * 4. shwo correctAnswerList in the dialog listview fuctionality available here(show with correct and wrong sign )
    * 
    *
    * */
}
