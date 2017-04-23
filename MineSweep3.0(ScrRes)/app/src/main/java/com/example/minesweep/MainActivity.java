package com.example.minesweep;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;
import android.view.HapticFeedbackConstants;


public class MainActivity extends Activity {
    String msg = "Android : ";
    private void print(String s)
    {
        Toast.makeText(this, s, 1).show();
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View graphics2View = new Graphics2View(this);
        setContentView(graphics2View);
        print("Welcome!");
    }
    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }
    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Graphics2View.onResume();
        Log.d(msg, "The onResume() event");
    }
    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Graphics2View.onPause();
        Log.d(msg, "The onPause() event");
    }
    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }
    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }
};

class Graphics2View extends View {

    /////////////////////////VARIABLES FOR TIMER AND TIMER LOGIC
    String secString = " ", secString2 = " ", minString = " ";
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
    public int[] scrAr = new int[2];

    private boolean isbomb              = true;
    private boolean gameOver            = false;
    private boolean youWon              = false;
    boolean badTouch                    = false;

    private final int GODLENnugget      = 15;
    private int ROWS              = 0;
    private final int placedBombs       = 18;
    public int COLS              = 0;
    private int DIM               = 0;
    private final int tile              = 10;
    private final int flag              = 11;
    private final int bomb              = 12;
    private final int smile             = 42;
    private final int empty             =  0;

    //////VARIABLES FOR DRAWING GAME BOARD CONSOLE ON ANY SCREEN SIZE
    private int timerOffsetX      =0;
    private int timerOffsetY      =0;
    private int offsetX           =0;
    private int offsetY           =0;
    private int widthLR           =0;
    private int heightTB          =0;
    private int timerTextLength = 350;
    public int timerTextSize = 0;
    private int offsetNewGameIconOne =0;
    private int offsetNewGameIconOneY =0;
    private int offsetNewGameIconTwo =0;
    public int boarderStrokeWidth = 20;
    public int flagCountTextSize = 40;
    public int offsetFlagCount =0;

    private void hdpi(){
        if(scrAr[1]>800&&scrAr[1]<860){
            ROWS = 13;
        }else{
            ROWS =12;
        }
        DIM = 50;
        timerTextSize=80;
        offsetY=100;
        offsetX=0;
        offsetNewGameIconOneY= -20;
        timerTextLength = timerTextSize*2 +timerTextSize/2;
        offsetFlagCount = offsetY/2+timerTextSize/3;
    }
    private void xhdpi(){
        ROWS =12;
        COLS = 9;
        DIM=80;
        offsetX=0;
        offsetY=150;
        timerTextSize=100;
        offsetNewGameIconOneY= -45;
        offsetNewGameIconOne = -100;
        offsetNewGameIconTwo = -75;
        flagCountTextSize=flagCountTextSize*2;
        timerTextLength = timerTextSize*2 +timerTextSize/2;
        offsetFlagCount = offsetY/2+timerTextSize/3;
    }
    private void xxhdpi(){
        DIM =100;
        offsetY=190;
        offsetX=42;
        offsetNewGameIconOneY= -40;
        ROWS=GODLENnugget;
        boarderStrokeWidth = boarderStrokeWidth*2;
        flagCountTextSize=flagCountTextSize*2;
        timerTextSize =140;
        offsetNewGameIconOne = -130;
        offsetNewGameIconTwo = -110;
        offsetFlagCount= offsetY/2;
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
        //print(scrAr[0]+","+scrAr[1]);
        if((scrAr[0] * scrAr[1]) <500000){
            hdpi();
        }else if(scrAr[0]*scrAr[1]<1000000) {
            xhdpi();
        }else{
            xxhdpi();
        }
        ////////////////////////////////////////DRAWING GAME BOARD AND CONSOLE
        //////DRAWING TIMER
        timerOffsetX      = scrAr[0]-timerTextLength;
        timerOffsetY      = offsetY/2+timerTextSize/3;
        /////DRAWING NEW GAME ICON
        offsetNewGameIconOne = offsetNewGameIconOne+ timerOffsetX - timerTextLength/2;
        offsetNewGameIconTwo = offsetNewGameIconTwo+ timerOffsetX - timerTextLength/2;
        ////DRAWING GAME BOARD AND BOARDER
        widthLR           = scrAr[0]-(scrAr[0]-(DIM*COLS))+offsetX;
        heightTB          = scrAr[1]-(scrAr[1]-(DIM*ROWS))+offsetY;
    }




    ////////////////////////////////////ON_TOUCH_EVENT VARIABLES
    private float touchX;
    private float touchY;
    private long mouseUP                =  0;
    private long mouseDOWN              =  0;
    boolean longPress                   = false;



    //////////////////////////////////////USED IN CONSTRUCTOR FOR GRAPHICS_2_VIEW
    private Context activityContext;

    //////////////////////////USED IN ON_DRAW
    Bitmap bmtile, bmempty, bmflag, bmbomb, bmsmile, bmnewgame, bmflagCount;
    Bitmap bmblu1, bmblu2, bmblu3, bmblu4, bmblu5, bmblu6, bmblu7, bmblu8;
    Bitmap bmnewgame2, bmnewgame3, bmhpstrsmiley, bmlock;

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
    private void endExistingGame()
    {
        stopTimer();

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
                if(minCount>=10)minString =  minCount + ":00";
                if(minCount<10)minString = "0" + minCount + ":00";
            }
            checkState();
            // add notification
            timer.postAtTime(this, currentMilliseconds);
            // notify to call back after 1 second
            // remain in the timer loop
            timer.postDelayed(updateTimeElasped, 1000);
            if(backInAct){
                 secString = secString2 = minString = "GO!!";
                 invalidate();
            }else{
                 if(gameOver)secondsPassed = 0;
                 invalidate();
            }
        }
    };

    //////////////METHOD FOR TOASTING TO THE USER AND LOG ACTIVITY
    private void print(String s)
    {
        Log.i(tag, s);
        Toast.makeText(activityContext, s, 1).show();
    }

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

    //////////////BOARD CREATION AND IMPLEMENTATION METHOD
    public Graphics2View(Context context) {
    	super(context);
        activityContext  = context;
        getScreenResolution(activityContext);
        gameOver         = false;
        status           = new int[ROWS][COLS];
        whereMyBombsAt   = new int[ROWS][COLS];
        bombs            = new boolean[ROWS][COLS];
        newGameTouch();

     //////////////////////////LOADING BITMAPS FOR ON DRAW TO DISPLAY
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
    	if ((r>=0) && (c>0) && (bombs[r-0][c-1]))count++;
    	if ((r<ROWS-1) && (c>0) && (bombs[r+1][c-1]))count++;
    	if ((r<ROWS-1) && (c>=0) && (bombs[r+1][c+0]))count++;
    	if ((r<ROWS-1) && (c<COLS-1) && (bombs[r+1][c+1]))count++;
    	if ((r>=0) && (c<COLS-1) && (bombs[r+0][c+1]))count++;
    	if ((r>0) && (c<COLS-1) && (bombs[r-1][c+1]))count++;
    	if ((r>0) && (c<=COLS-1) && (bombs[r-1][c-0]))count++;
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
                         (status[r+i][c+j] == tile) && (bombs[r+i][c+j]) == false)
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
              print("YOU WON!!!!!");
            }
    	}
    	else{
    		return;
    	}
    }

    ////////////////////////METHOD FOR DETONATING BOMBS
    private void showThemBombs(boolean n){
    	if(gameOver){
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
            		else if(!(bombs[i][j] == isbomb)&&youWon){
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
        if(minString.equals("GO!!")||secString2.equals("GO!!")||secString.equals("GO!!")){
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
            if(bombs[i][j] == false){
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
            invalidate();
            return true;
        } else if(touchX <= offsetX || touchX >= widthLR || touchY <= offsetY || touchY >= heightTB) {
            return false;
        } else{
            if(numOfTouches == 0){
                startNewGame();
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
                       performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                	  flagCount++;
                      }
                   else if(status[tocYdx][tocXdx] == flag){
                	       status[tocYdx][tocXdx] = tile;
                            performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                	       flagCount--;
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
        return true;
        }
    }

//############################### ON DRAW ###################################
    @Override
    public void onDraw(Canvas canvas) {

        /////////////////ON DRAW VARIABLES
        //print("onDraw:");
        Paint fg          = new Paint();
        Paint fg2         = new Paint();
        Paint timDigis    = new Paint();
        canvas.drawColor(Color.DKGRAY);
        fg.setStrokeWidth(boarderStrokeWidth);
        fg.setStrokeCap(Paint.Cap.ROUND);
        fg.setColor(Color.YELLOW);
        fg2.setColor(Color.WHITE);
        timDigis.setColor(Color.WHITE);
        timDigis.setTextSize(timerTextSize);
        fg2.setTextSize(flagCountTextSize);

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
            fg.setColor(Color.GREEN);
            if(badTouch){
                canvas.drawBitmap(bmlock, offsetNewGameIconTwo, 0, null);
                badTouch = false;
            }else
            canvas.drawBitmap(bmnewgame2, offsetNewGameIconTwo, 0, null);
        }

        /////////////DRAWING THE GAMEBOARD BOARDER
        //top horizontal border
        //(startX,startY,stopX,stopY)
        canvas.drawLine(offsetX, offsetY, widthLR, offsetY, fg);
        //right vertical border
        canvas.drawLine(widthLR, offsetY, widthLR, heightTB, fg);
        //bottom horizontal border
        canvas.drawLine(widthLR, heightTB, offsetX, heightTB,fg);
        //left vertical border
        canvas.drawLine(offsetX, heightTB, offsetX, offsetY,fg);

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
        
    }
}