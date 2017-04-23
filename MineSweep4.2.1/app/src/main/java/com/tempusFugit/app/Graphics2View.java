package com.tempusFugit.app;

import android.content.Context;
import android.view.ScaleGestureDetector;
import android.view.Display;
import android.view.ViewParent;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
/**
 * Created by johntoland on 10/22/16.
 */
class GameActivity extends MainActivity{
    String msg = "Android:";

    GameActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //print("Welcome!");
    }
}

public class Graphics2View extends View {

    GameActivity g = new GameActivity();
    private void print(String s)
    {
        Log.i(tag, s);
        Toast.makeText(activityContext, s, Toast.LENGTH_LONG).show();
    }
    long[] pattern1 = {0, 500};
    long[] pattern2 = {0, 400, 50, 100, 30, 100, 30, 100, 30, 400, 50, 800};
    long[] pattern3 = {0, 100};
    UserClass u = new UserClass();
    /////////////////////////VARIABLES FOR TIMER AND TIMER LOGIC
    String secString = " ", secString2 = " ", minString = " ";
    public int[] scrAr = new int[2];
    boolean backInAct                   = false;
    boolean leftAct                     = false;
    boolean paused                      = false;
    boolean isTimerStarted              = false;
    int secsOnPause                     = 0;
    int minsOnPause                     = 0;
    int numOfTouches                    = 0;
    int secondsPassed                   = 0;
    int minCount                        = 0;
    int gotAmin                         = 0;
    int temp;
    int temp2;
    private Handler timer               = new Handler();

    public int getDrawingCacheQuality() {
        return super.getDrawingCacheQuality();

    }

    ////////////////////////////GENERAL LOGICAL OR GRAPHICAL VARIABLES
    private String tag                   = "(e^ipi)+1";
    private int bombCount, flagCount, GAMEcheck, GAMEwon, i, j, count;
    private int whereMyBombsAt[][];
    private int status[][];
    private boolean bombs[][];
    private boolean isbomb              = true;
    private boolean gameOver            = false;
    private static boolean youWon       = false;
    boolean badTouch                    = false;
    public static int choice            = 0;
    public static int GOLDENnugget      = 0;  //rows
    public static int SILVERdollar      = 0;  //bombs
    public static String currentLvl     = "";
    private int ROWS                    = 0;
    private final int placedBombs       = SILVERdollar;
    private int COLS                    = 10;
    private int DIM                     = 0;
    private final int tile              = 10;
    private final int flag              = 11;
    private final int bomb              = 12;
    private final int smile             = 42;
    private final int empty             =  0;

    //////VARIABLES FOR DRAWING GAME BOARD CONSOLE ON ANY SCREEN SIZE
    public static boolean smallScreen   = false;
    public static boolean mediumScreen  = false;
    public static boolean largeScreen   = false;
    public static boolean extraLrgScreen = false;
    private int timerOffsetX            = 0;
    private int timerOffsetY            = 0;
    private int offsetX                 = 0;
    private int offsetY                 = 0;
    private int widthLR                 = 0;
    private int heightTB                = 0;
    private int timerTextLength         = 350;
    public int  timerTextSize           = 0;
    private int offsetNewGameIconOne    = 0;
    private int offsetNewGameIconOneY   = 0;
    private int offsetNewGameIconTwo    = 0;
    public int boarderStrokeWidth       = 20;
    public int flagCountTextSize        = 40;
    public int offsetFlagCount          = 0;
    public int userNameTextSize         = 0;
    public static String maxRows        = "";
    public static int maxIntRows        = 0;
    public int offsetUserNameY          = -20;
    public int area                     = 0;

    private void hdpi(){
        smallScreen = true;
        maxRows = "12";
        maxIntRows = 12;
        ROWS = GOLDENnugget;
        COLS = 9;
        DIM = 50;
        offsetX = 15;
        offsetY = 100;
        offsetNewGameIconOneY = -20;
        timerTextSize = 80;
        timerTextLength = timerTextSize * 2 +timerTextSize/2;
        offsetFlagCount = offsetY/2 + timerTextSize/3;
        userNameTextSize = 20;
        offsetUserNameY = -5;
    }

    private void xhdpi(){
        if(scrAr[1]<1300){
            smallScreen = true;
            maxRows = "12";
            maxIntRows = 12;
            ROWS = GOLDENnugget-1;
        }else {
            mediumScreen = true;
            maxRows = "13";
            maxIntRows = 13;
            ROWS = GOLDENnugget;
        }
        COLS = 9;
        DIM = 80;
        if(scrAr[0]<725){
            offsetX=0;
        }else{
            offsetX = 25;
        }
        offsetY = 150;
        offsetNewGameIconOneY = -45;
        offsetNewGameIconOne = -100;
        offsetNewGameIconTwo = offsetNewGameIconOne + 25;
        timerTextSize = 100;
        timerTextLength = timerTextSize * 2 + timerTextSize/2;
        flagCountTextSize = flagCountTextSize * 2;
        offsetFlagCount = offsetY/2 + timerTextSize/3;
        userNameTextSize = 40;
        offsetUserNameY = -5;
    }

    private void xxhdpi(){
        ROWS = GOLDENnugget;
        DIM = 100;
        offsetX = 42;
        offsetY = 200;
        offsetNewGameIconOne = -135;
        offsetNewGameIconTwo = offsetNewGameIconOne + 30;
        offsetNewGameIconOneY = -30;
        timerTextSize = 140;
        flagCountTextSize = flagCountTextSize * 2;
        offsetFlagCount = offsetY/2;
        boarderStrokeWidth = boarderStrokeWidth * 2;
        userNameTextSize = 50;
    }

    private void xxExtraLrghdpi(){
        extraLrgScreen =true;
        maxRows ="15";
        maxIntRows=15;
        ROWS = GOLDENnugget;
        COLS = 11;
        DIM = 125;
        offsetX = 35;
        offsetY = 220;
        offsetNewGameIconOne = -265;
        offsetNewGameIconTwo = offsetNewGameIconOne + 30;
        offsetNewGameIconOneY = -40;
        timerTextSize = 160;
        timerTextLength = timerTextSize * 2 + timerTextSize/2;
        flagCountTextSize = flagCountTextSize * 2;
        offsetFlagCount = offsetY/2;
        boarderStrokeWidth = boarderStrokeWidth * 2;
        userNameTextSize = 70;
    }
    //////METHOD FOR DRAWING OBJECTS IN THE CORRECT (X,Y) COORDINATE
    private void getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        scrAr[0] = width;
        scrAr[1] = height;
        area = scrAr[0]*scrAr[1];
       // print(scrAr[0]+","+scrAr[1]);
       // print(""+scrAr[0]*scrAr[1]);
        if(area < 500000){
            hdpi();
        }else if(area < 1000000 && area > 500000) {
            xhdpi();
        }else if(area < 2000000 && area > 1000000) {
            mediumScreen = true;
            maxRows = "13";
            maxIntRows = 13;
            xxhdpi();
        }else if(area < 3000000 && area > 2000000){
            largeScreen = true;
            maxRows = "15";
            maxIntRows = 15;
            xxhdpi();
        }else if(area < 4000000 && area > 3000000){
            xxExtraLrghdpi();
        }
        ////////////////////////////////////////DRAWING GAME BOARD AND CONSOLE
        //////DRAWING TIMER
        timerOffsetX      = scrAr[0] - timerTextLength;
        timerOffsetY      = offsetY/2 + timerTextSize/3;
        /////DRAWING NEW GAME ICON
        offsetNewGameIconOne = offsetNewGameIconOne + timerOffsetX - timerTextLength/2;
        offsetNewGameIconTwo = offsetNewGameIconTwo + timerOffsetX - timerTextLength/2;
        ////DRAWING GAME BOARD AND BOARDER
        widthLR           = scrAr[0] - (scrAr[0] - (DIM * COLS)) + offsetX;
        heightTB          = scrAr[1] - (scrAr[1] - (DIM * ROWS)) + offsetY;
    }

    public static void gameLevel(int level){
        choice = level;
        if(choice == 0){
            SILVERdollar = GOLDENnugget = 10;
            currentLvl = "[B]";
        }else if(choice == 1){
            if(smallScreen){
                GOLDENnugget = 11;
            }else if(mediumScreen){
                GOLDENnugget = 12;
            }else if(largeScreen||extraLrgScreen){
                GOLDENnugget = 13;
            }
            SILVERdollar = 20;
            currentLvl = "[I]";
        }else if(choice == 2){
            if(smallScreen){
                GOLDENnugget = 12;
            }else if(mediumScreen){
                GOLDENnugget = 13;
            }else if(largeScreen||extraLrgScreen){
                GOLDENnugget = 15;
            }
            SILVERdollar = 32;
            currentLvl = "[A]";
        }

    }

    public static void gameCustomLevel(int numOfBomb, int numOfRows){
        SILVERdollar = numOfBomb;
        GOLDENnugget = numOfRows;
        choice = 3;
        currentLvl = "[C]";
    }

    /////////////////ON DRAW VARIABLES
    Paint fg          = new Paint();
    Paint fg2         = new Paint();
    Paint fg3         = new Paint();
    Paint timDigis    = new Paint();

    ////////////////////////////////////ON_TOUCH_EVENT VARIABLES
    private float touchX;
    private float touchY;
    private long mouseUP                =  0;
    private long mouseDOWN              =  0;
    boolean longPress                   = false;

    //////////////////////////////////////USED IN CONSTRUCTOR FOR GRAPHICS_2_VIEW
    public static Context activityContext;

    //////////////////////////USED IN ON_DRAW
    Bitmap bmtile, bmempty, bmflag, bmbomb, bmsmile, bmnewgame, bmflagCount;
    Bitmap bmblu1, bmblu2, bmblu3, bmblu4, bmblu5, bmblu6, bmblu7, bmblu8;
    Bitmap bmnewgame2, bmnewgame3, bmhpstrsmiley, bmlock, bmbckgrdInter;
    Bitmap bmbckgrdCus1;

    ///////////////////////USED IN CHECKING THE STATE OF THE ACTIVITY METHODS
    static boolean activityVisible;

    ////////////////////////METHOD FOR STARTING THE TIMER WHEN A NEW GAME IS STARTED
    private void startNewGame()
    {
        startTimer();
        secondsPassed = 0;
    }

    ///////////////////////METHOD THAT STARTS THE TIMER
    public void startTimer()
    {
        if (secondsPassed == 0) {
            //isTimerStarted = true;
            timer.removeCallbacks(updateTimeElasped);
            // tell timer to run call back after 1 second
            timer.postDelayed(updateTimeElasped, 1000);
        }
    }

    /////////////////////////////////////METHOD FOR STOPPING THE TIMER
    public void stopTimer()
    {
        // disable call backs
        timer.removeCallbacks(updateTimeElasped);
    }

    //////////////////////METHOD FOR STOPPING THE TIMER WHEN GAME IS OVER
    ////////////////THIS IS WERE SAVING THE GAME SCORE TO THE highscores.txt FILE BEGINS!!!!!!!!!!!
    private void endExistingGame()
    {

        stopTimer();
        if(youWon) {
            if (secondsPassed < 10 && gotAmin == 0) {
                u.setUserScore(secString);
            } else if (secondsPassed > 9) {
                u.setUserScore(secString2);
            } else if (secondsPassed == 0) {
                u.setUserScore(minString);
            }
            if(choice != 3){
                int passThe = calculateTime(minCount,secondsPassed);
                //print("User: "+ u.getUserName() +"'s winning time is = "+ u.getUserScore());
                //print("Calculated time is = " + passThe);
                ///////////sends rank to scoreboard machine
                u.evaluateScoreBoard(passThe);
                //print("location of newRank = " + u.getLocation());
            }
        }
    }

    public int calculateTime(int m, int s){
        int overallSec = (m*60) + s;
        ///////evaluates the correct rank based on level chosen by user
        if(choice == 0){
            overallSec = overallSec*100;
        }else if(choice == 1){
            overallSec = overallSec*10;
        }
        return overallSec;
    }

    ///////////////////////////////////////////////METHOD CALL BACK WHEN TIMER TICKS
    private Runnable updateTimeElasped = new Runnable()
    {
        public void run()
        {
            if(leftAct){
                leftAct = false;
                secsOnPause = secondsPassed;
                minsOnPause = minCount;
            }else if(backInAct){
                backInAct = false;
                temp = secondsPassed - secsOnPause;
                secondsPassed = secondsPassed - temp-2;
                if(minsOnPause == 0){
                    minCount= 0;
                }else{
                    temp2 = minCount - minsOnPause;
                    minCount = minCount - temp2;
                }
            }
            isTimerStarted = true;
            long currentMilliseconds = System.currentTimeMillis();
            ++secondsPassed;
            if (secondsPassed < 10) {
                gotAmin = 0;
                if(minCount==0)secString = "00:0" + Integer.toString(secondsPassed);
                if(minCount>0 && minCount<10)secString = "0" + minCount + ":0" + Integer.toString(secondsPassed);
                if(minCount>=10)secString =  minCount + ":0" + Integer.toString(secondsPassed);
            }
            else if (secondsPassed < 60)
            {
                if(minCount==0)secString2 = "00:"+ Integer.toString(secondsPassed);
                if(minCount>0 && minCount<10 && gotAmin == 0)secString2 = "0" + minCount + ":" + Integer.toString(secondsPassed);
                if(minCount>=10)secString2 = minCount + ":" +Integer.toString(secondsPassed);
            }
            else if (secondsPassed==60)
            {
                minCount++;
                gotAmin = 1;
                secondsPassed= secondsPassed - secondsPassed;
                if(minCount == 60){
                    stopTimer();
                }else{
                    if(minCount>=10)minString =  minCount + ":00";
                    if(minCount<10)minString = "0" + minCount + ":00";
                }
            }
            // add notification
            timer.postAtTime(this, currentMilliseconds);
            // notify to call back after 1 second
            // remain in the timer loop
            timer.postDelayed(updateTimeElasped, 1000);
            checkState();
            if(backInAct){
                secString = secString2 = minString = "GO!!!!";
            }else {
                if (gameOver){
                    secondsPassed = 0;
                }
            }
            invalidate();
        }
    };



    /////////METHOD THAT CHECKS THE CURRENT STAT OF THE PROGRAM//////CALLS GET_INFO()
    public void checkState(){
        boolean gotInfo = getInfo();
        if(gotInfo && paused){
            paused = false;
            backInAct = true;
        }else if(!gotInfo && !paused){
            leftAct = true;
            paused = true;
            isTimerStarted = false;
        }
    }

    //////////////////CALLED FROM A STATIC CONTEXT IN MAIN ACTIVITY BY GRAPHICS_2_VIEW CLASS
    ///////////////////CHECKS FOR THE MOMENT THE USER LEAVES THE ACTIVITY RIGHT BEFORE ON_STOP() GETS CALLED
    public static void onPause(){
        activityVisible = false;

    }

    ////////////CALLED FROM A STATIC CONTEXT IN MAIN ACTIVITY BY GRAPHICS_2_VIEW CLASS
    ////////////CHECKS FOR THE MOMENT THE USER RETURNS TO THE ACTIVITY AFTER ON_STOP() HAS BEEN CALLED
    public static void onResume(){
        activityVisible = true;

    }

    //////////CALLED BY GET_INFO()/////////////RETURNS ACTIVITY_VISIBLE
    public static boolean isActivityVisible() {
        return activityVisible;

    }

    //////////CALLED BY CHECK()///////CALLS IS_ACTIVITY_VISIBLE()
    public boolean getInfo (){
        return isActivityVisible();

    }

    //private ScaleGestureDetector mScaleDetector;
    //private float mScaleFactor = 1.f;
    //////////////BOARD CREATION AND IMPLEMENTATION METHOD
    public Graphics2View(Context context) {
        super(context);
        activityContext  = context;
        //mScaleDetector = new ScaleGestureDetector(activityContext, new ScaleListener());
        getScreenResolution(activityContext);
        status           = new int[ROWS][COLS];
        whereMyBombsAt   = new int[ROWS][COLS];
        bombs            = new boolean[ROWS][COLS];
        newGameTouch();

        ///////this print statement helps determine the priority of the winning time
        ////////based on the level of difficulty (BOMB Count vs ROW count)
        print("LEVEL: " + currentLvl + " <" + SILVERdollar+ "> BOMBS");

        //////////////////////////LOADING BITMAPS FOR ON DRAW TO DISPLAY
        bmbckgrdInter = BitmapFactory.decodeResource(getResources(), R.drawable.bkgrdinter);
        bmbckgrdCus1 = BitmapFactory.decodeResource(getResources(), R.drawable.bkgrdcustom1);
        bmtile       = BitmapFactory.decodeResource(getResources(), R.drawable.tile);
        bmempty      = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmflag       = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        bmbomb       = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        bmsmile      = BitmapFactory.decodeResource(getResources(), R.drawable.smileface);
        bmnewgame    = BitmapFactory.decodeResource(getResources(), R.drawable.newgame1);
        bmflagCount  = BitmapFactory.decodeResource(getResources(), R.drawable.flag_countimage);
        bmblu1       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum1);
        bmblu2       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum2);
        bmblu3       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum3);
        bmblu4       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum4);
        bmblu5       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum5);
        bmblu6       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum6);
        bmblu7       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum7);
        bmblu8       = BitmapFactory.decodeResource(getResources(), R.drawable.bluenum8);
        bmnewgame2   = BitmapFactory.decodeResource(getResources(), R.drawable.newgame2);
        bmnewgame3   = BitmapFactory.decodeResource(getResources(), R.drawable.newgame3);
        bmhpstrsmiley   = BitmapFactory.decodeResource(getResources(), R.drawable.hpstrsmiley);
        bmlock          = BitmapFactory.decodeResource(getResources(), R.drawable.lock);

    }

    /////////////METHOD FOR COUNTING NEIGHBOR BOMBS
    public int countNeighboringBombs1(int r, int c){
        count = 0;
        if ((r>0) && (c>0) && (bombs[r-1][c-1]))count++;
        if ((r>=0) && (c>0) && (bombs[r][c-1]))count++;
        if ((r<ROWS-1) && (c>0) && (bombs[r+1][c-1]))count++;
        if ((r<ROWS-1) && (c>=0) && (bombs[r+1][c]))count++;
        if ((r<ROWS-1) && (c<COLS-1) && (bombs[r+1][c+1]))count++;
        if ((r>=0) && (c<COLS-1) && (bombs[r][c+1]))count++;
        if ((r>0) && (c<COLS-1) && (bombs[r-1][c+1]))count++;
        if ((r>0) && (c<=COLS-1) && (bombs[r-1][c]))count++;
        return count;

    }

    ////////////////RECURSIVE METHOD FOR REMOVING TILES
    private void clearBlankNeighbors(int r, int c)
    {
        status[r][c] = empty;
        int n = countNeighboringBombs1(r, c);
        if (n == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    // Clear rows & columns if the tile
                    // has not already been EMPTY
                    if ( (r+i<ROWS) && (r+i>=0) && (c+j<COLS) && (c+j>=0) &&
                            (status[r+i][c+j] == tile) && !(bombs[r+i][c+j]))
                    {
                        // Recursive call to clear open tiles.
                        clearBlankNeighbors(r + i, c + j);
                    }
                }
            }
        } else {
            status[r][c] = n;
        }
    }

    ///////////METHOD FOR CHECKING THE GAME PLAY SCORES
    private void didYouWin(int fp, int fLeft){
        GAMEcheck    = 0;
        GAMEwon      = 0;
        int yourGood = fLeft-fp;

        if(yourGood == 0){
            for(i=0; i<ROWS; i++){
                for(j=0; j<COLS; j++){
                    if(!(status[i][j] == tile) && !(status[i][j] == flag)){
                        GAMEcheck = GAMEcheck +1;
                    }
                    else if(!(status[i][j]==tile)){
                        if((status[i][j] == whereMyBombsAt[i][j])){
                            GAMEwon = GAMEwon + 10;
                        }
                    }
                }
            }
            if((GAMEwon == placedBombs*10) && (GAMEcheck == (ROWS*COLS)-placedBombs)){
                gameOver   = true;
                youWon     = true;
                endExistingGame();
                showThemBombs(gameOver);
                u.vibrator().vibrate(pattern2,-1);
                print("YOU WON!!!!!");
            }
        }

    }

    ////////////////////////METHOD FOR DETONATING BOMBS OR SHOWING THE SMILES
    private void showThemBombs(boolean n){
        if(n){
            u.vibrator().vibrate(pattern1, -1);
            for(i=0; i<ROWS; i++){
                for(j=0; j<COLS; j++){
                    if(bombs[i][j] == isbomb){
                        if(youWon){
                            status[i][j] = smile;
                        }
                        else if(!(youWon)){
                            status[i][j] = bomb;
                        }
                    }
                    else if(youWon && !(bombs[i][j] == isbomb)){
                        status[i][j] = empty;
                    }
                }
            }
        }
    }

    /////////////////////////METHOD FOR NEW GAME BUTTON TOUCH
    public void newGameTouch(){
        gameOver         = false;
        youWon           = false;
        isTimerStarted   = false;
        if(minString.equals("GO!!!!")||secString2.equals("GO!!!!")||secString.equals("GO!!!!")){
            secsOnPause      = 1;
            minString = secString2 = secString  = " ";
        }
        secondsPassed =numOfTouches =minCount =flagCount =bombCount =GAMEcheck =GAMEwon = 0;

        for(i=0; i<ROWS; i++){
            for(j=0; j<COLS; j++){
                status[i][j] = tile;
            }
        }
        for(i=0; i<ROWS; i++){
            for(j=0; j<COLS; j++){
                bombs[i][j] = false;
            }
        }
        while(bombCount < placedBombs){
            i = (int)(ROWS*Math.random());
            j = (int)(COLS*Math.random());
            if(!bombs[i][j]){
                bombs[i][j] = isbomb;
                bombCount++;
            }
        }
        for(i=0; i<ROWS; i++){
            for(j=0; j<COLS; j++){
                whereMyBombsAt[i][j] = 69;
            }
        }
        for(i=0; i<ROWS; i++){
            for(j=0; j<COLS; j++){
                if(bombs[i][j] == isbomb){
                    whereMyBombsAt[i][j] = 11;
                }
            }
        }
    }
//################################# ON TOUCH EVENT ###################################//

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        /////////ON TOUCH EVENT VARIABLES
        touchX       = e.getX();
        touchY       = e.getY();
        int action   = e.getAction();
        int tocXdx   = ((Math.round(touchX)-(offsetX))/(DIM));
        int tocYdx   = ((Math.round(touchY)-(offsetY))/(DIM));

        //////////JGT LOGIC FOR MINE_SWEEP
        if(gameOver){
            if(touchX>offsetNewGameIconTwo && touchY> 3 && touchX <offsetNewGameIconTwo+timerTextSize && touchY <offsetY){
                newGameTouch();
                invalidate();
                return true;
            } else
                return true;
        }else if(!gameOver &&(touchX>offsetNewGameIconTwo && touchY> 3 && touchX <offsetNewGameIconTwo+timerTextSize && touchY <offsetY)&&numOfTouches!=0){
            badTouch = true;
            u.vibrator().vibrate(pattern3, -1);
            invalidate();
            return true;
        } else if(touchX <= offsetX || touchX >= widthLR || touchY <= offsetY || touchY >= heightTB) {
            //mScaleDetector.onTouchEvent(e);
            return false;
        } else{
            if(numOfTouches == 0){
                startNewGame();
                invalidate();
                numOfTouches++;
            }
            if(action == MotionEvent.ACTION_DOWN) {
                mouseDOWN = System.currentTimeMillis();
            }else if(action == MotionEvent.ACTION_UP) {
                mouseUP = System.currentTimeMillis();
                if(mouseUP-mouseDOWN >200) {
                    longPress = true;
                    if(status[tocYdx][tocXdx] == tile){
                        status[tocYdx][tocXdx] = flag;
                        flagCount++;
                        performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    }
                    else if(status[tocYdx][tocXdx] == flag){
                        status[tocYdx][tocXdx] = tile;
                        flagCount--;
                        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    }
                    else if(!(status[tocYdx][tocXdx] == flag) && !(status[tocYdx][tocXdx] == tile)){
                        status[tocYdx][tocXdx] = status[tocYdx][tocXdx];
                    }
                }else if(status[tocYdx][tocXdx] == flag) {
                    longPress = false;
                    flagCount--;
                    if(bombs[tocYdx][tocXdx] == isbomb){
                        status[tocYdx][tocXdx] = bomb;
                        gameOver = true;
                        endExistingGame();
                        showThemBombs(gameOver);
                        print("GAME OVER!!!!");
                    } else{
                        int n = countNeighboringBombs1(tocYdx, tocXdx);
                        if(!(n==12) && (n>0)){
                            status[tocYdx][tocXdx] = n;
                        }
                        else
                            clearBlankNeighbors(tocYdx, tocXdx);
                    }
                }else {
                    longPress = false;
                    if(bombs[tocYdx][tocXdx] == isbomb){
                        status[tocYdx][tocXdx] = bomb;
                        gameOver = true;
                        endExistingGame();
                        showThemBombs(gameOver);
                        print("GAME OVER!!!!");
                    } else{
                        int n = countNeighboringBombs1(tocYdx, tocXdx);
                        if(!(n==12) && (n>0)){
                            status[tocYdx][tocXdx] = n;
                        } else {
                            clearBlankNeighbors(tocYdx, tocXdx);
                        }
                    }
                }
            }
            if(flagCount == bombCount)didYouWin(flagCount,placedBombs);
            invalidate();
           // mScaleDetector.onTouchEvent(e);
            return true;
        }

    }
/*
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.6f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
*/

    //############################### ON DRAW ###################################
    @Override
    public void onDraw(Canvas canvas) {
        //canvas.save();
        //canvas.scale(mScaleFactor, mScaleFactor);
        fg.setStrokeWidth(boarderStrokeWidth);
        fg.setStrokeCap(Paint.Cap.ROUND);
        fg.setColor(Color.YELLOW);
        fg2.setColor(Color.WHITE);
        fg3.setColor(Color.WHITE);
        timDigis.setColor(Color.WHITE);
        timDigis.setTextSize(timerTextSize);
        fg2.setTextSize(flagCountTextSize);
        fg3.setTextSize(userNameTextSize);

        ///////DRAWING BACKGROUND
        if(choice == 3){
            canvas.drawBitmap(bmbckgrdInter, 0, -2, null);
        }else {
            canvas.drawColor(getResources().getColor(R.color.dark_grey));
        }

        ///////////DRAWING FLAG COUNT, NEW GAME BUTTON
        canvas.drawBitmap(bmflagCount, 10, 10, null);
        canvas.drawText(" = " + flagCount, offsetX+DIM, offsetFlagCount, fg2);
        if(numOfTouches == 0){
            fg.setColor(Color.BLUE);
            canvas.drawBitmap(bmnewgame, offsetNewGameIconOne, offsetNewGameIconOneY, null);
        }else if(gameOver && !youWon) {
            fg.setColor(Color.RED);
            canvas.drawBitmap(bmnewgame3, offsetNewGameIconTwo, 0, null);
        }else if(youWon) {
            fg.setColor(Color.YELLOW);
            canvas.drawBitmap(bmhpstrsmiley, offsetNewGameIconTwo, 0, null);
        }else {
            fg.setColor(getResources().getColor(R.color.tealgreen));
            if(badTouch){
                canvas.drawBitmap(bmlock, offsetNewGameIconTwo, 0, null);
                badTouch = false;
            }else
                canvas.drawBitmap(bmnewgame2, offsetNewGameIconTwo, 0, null);
        }

        /////////////DRAWING THE GAME BOARD BOARDER, USERNAME
        //top horizontal border
        //(startX,startY,stopX,stopY)
        canvas.drawLine(offsetX, offsetY, widthLR, offsetY, fg);
        //right vertical border
        canvas.drawLine(widthLR, offsetY, widthLR, heightTB, fg);
        //bottom horizontal border
        canvas.drawLine(widthLR, heightTB, offsetX, heightTB,fg);
        //left vertical border
        canvas.drawLine(offsetX, heightTB, offsetX, offsetY,fg);
        canvas.drawText("~/"+u.getUserName(), 2, offsetY+offsetUserNameY, fg3);
        ////////////DRAWING MY TIMER
        if(isTimerStarted) {
            if (secondsPassed < 10 && gotAmin == 0) canvas.drawText(secString, timerOffsetX, timerOffsetY, timDigis);
            if (secondsPassed > 9) canvas.drawText(secString2, timerOffsetX, timerOffsetY, timDigis);
            if (secondsPassed == 0 && gotAmin >0) canvas.drawText(minString, timerOffsetX, timerOffsetY, timDigis);
        } else{
            canvas.drawText("00:00", timerOffsetX, timerOffsetY, timDigis);
        }

        ////////////////DRAWING GAME BOARD
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                int x = offsetX + j * DIM;
                int y = offsetY + i * DIM;

                if(status[i][j] == tile){
                    canvas.drawBitmap(bmtile, x, y, null);
                }
                else if(status[i][j] == empty){
                    canvas.drawBitmap(bmempty, x, y, null);
                }
                else if(status[i][j] == flag){
                    canvas.drawBitmap(bmflag, x, y, null);
                }
                else if(status[i][j] == bomb){
                    canvas.drawBitmap(bmbomb, x, y, null);
                }
                else if(status[i][j] == smile){
                    canvas.drawBitmap(bmsmile, x, y, null);
                }
                else if(status[i][j] == 1){
                    canvas.drawBitmap(bmblu1, x, y, null);
                }
                else if(status[i][j] == 2){
                    canvas.drawBitmap(bmblu2, x, y, null);
                }
                else if(status[i][j] == 3){
                    canvas.drawBitmap(bmblu3, x, y, null);
                }
                else if(status[i][j] == 4){
                    canvas.drawBitmap(bmblu4, x, y, null);
                }
                else if(status[i][j] == 5){
                    canvas.drawBitmap(bmblu5, x, y, null);
                }
                else if(status[i][j] == 6){
                    canvas.drawBitmap(bmblu6, x, y, null);
                }
                else if(status[i][j] == 7){
                    canvas.drawBitmap(bmblu7, x, y, null);
                }
                else if(status[i][j] == 8){
                    canvas.drawBitmap(bmblu8, x, y, null);
                }
                ////////////////END OF PROGRAM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
        //canvas.restore();

    }
}
