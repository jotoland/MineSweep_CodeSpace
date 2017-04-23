package com.tempusFugit.app;

import android.os.Environment;
import android.os.Vibrator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by johntoland on 11/4/16.
 */
public class UserClass{
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/elloMineSweep4.2";
    public File savedFile1 = new File(path +"/highScores.txt");
    private File savedFile2 = new File(path +"/rankingBoard.txt");
    private Lex lex1;
    private Lex lex2;

    public UserClass(){


    }

    public Vibrator vibrator(){
        return MainActivity.v;
    }

    public void evaluateScoreBoard(int oS){
        if(getUserScore().contains("G"))return;
        setNewRank(oS);
        sortRankAr(getNewRank());
        for(int i=0; i<MainActivity.MAX; i++){
            MainActivity.rankingArString[i] = "."+Integer.toString(MainActivity.rankingAr[i])+".";
        }
        ///////finalizes the constructions of scoreboard and ranking arrays
        setUserRankAr(MainActivity.rankingArString);
        setRankingAr(MainActivity.rankingAr);
        setScoreBoardString(MainActivity.scoreBoardStr);
        ////////Saves the files to android handset
        Save(savedFile2, getUserRankAr());
        Save(savedFile1, getScoreBoardString());
    }

    private void sortRankAr(int in){
        boolean isATie = false;
        for(int i =0; i<MainActivity.rankingAr.length; i ++){
            if(in == MainActivity.rankingAr[i]){
                isATie=true;
            }
        }
        if(in<MainActivity.rankingAr[MainActivity.MAX-1]&&!isATie){
            MainActivity.rankingAr[MainActivity.MAX-1] = in;
            MergeSort m = new MergeSort();
            m.sort(MainActivity.rankingAr);
            setRankingAr(MainActivity.rankingAr);
            setScoreBoardLayout(getLocation());
        }
    }

    private void setScoreBoardLayout(int in){
        int grab = in;
        if(grab != MainActivity.MAX-1){
            for(int i =MainActivity.MAX-2; i>grab-1; i--){
                MainActivity.userBanner[(i*3)+3] = MainActivity.userBanner[(i*3)];
                MainActivity.userBanner[(i*3)+4] = MainActivity.userBanner[(i*3)+1];
                MainActivity.userBanner[(i*3)+5] = MainActivity.userBanner[(i*3)+2];
            }
        }
        MainActivity.userBanner[(grab*3)] = getUserName();
        MainActivity.userBanner[(grab*3)+1] = getCurrentLevel();
        MainActivity.userBanner[(grab*3)+2] = getCurrentScore();
        buildNewScoreBoard();
    }

    private String[] getScoreBoardString(){
        return MainActivity.scoreBoardStr;

    }

    private void setScoreBoardString(String[] in){
        MainActivity.scoreBoardStr = in;

    }

    private void setRankingAr(int[] in){
        MainActivity.rankingAr = in;

    }

    private void setNewRank(int in){
        MainActivity.newRank = in;

    }

    public int getNewRank(){
        return MainActivity.newRank;

    }

    public void setLocation(int in){
        MainActivity.location = in;

    }

    public int getLocation() {
        return MainActivity.location;

    }

    private String getCurrentLevel(){
        return Graphics2View.currentLvl;

    }

    private String getCurrentScore(){
        return MainActivity.score;

    }

    public void setUserName(String in){
        MainActivity.userName = in;

    }

    public String getUserName(){
        return MainActivity.userName;

    }

    private void setUserRankAr(String[] in){
        MainActivity.rankingArString= in;

    }

    private String[] getUserRankAr(){
        return MainActivity.rankingArString;

    }

    public String getUserScore(){
        return MainActivity.score;

    }

    public void setUserScore(String in){
            MainActivity.score = in;

    }

    public void gameLevelChosenbyUser(int i){
        Graphics2View.gameLevel(i);

    }

    public void gameCustomLevelChosenByUser(int i, int j){
        Graphics2View.gameCustomLevel(i,j);

    }

    public void createScoreBoard(){
        MainActivity.scoreBoardStr[0]=".Doug Funnie.[B].01:00.";
        MainActivity.scoreBoardStr[1]=".Pattie Mayonnaise.[B].02:00.";
        MainActivity.scoreBoardStr[2]=".Skeeter Valentine.[B].03:00.";
        MainActivity.scoreBoardStr[3]=".Roger Klotz.[B].04:00.";
        MainActivity.scoreBoardStr[4]=".Beebe Bluff.[B].05:00.";
        MainActivity.scoreBoardStr[5]=".Judy Funnie.[B].06:00.";
        MainActivity.scoreBoardStr[6]=".PorkChop.[B].07:00.";
        MainActivity.scoreBoardStr[7]=".Phil Funnie.[B].08:00.";
        MainActivity.scoreBoardStr[8]=".Tippi Dink.[B].09:00.";
        MainActivity.scoreBoardStr[9]=".Chalky Studebaker.[B].10:00.";
    }

    public void createRankAr(){
        int aMin;
        for(int i=0; i<MainActivity.rankingAr.length; i++){
            aMin = 60*(i+1);
            MainActivity.rankingAr[i] = aMin*100;
        }
    }

    public void createRankArString(){
        MainActivity.rankingArString[0] = ".6000.";
        MainActivity.rankingArString[1] = ".12000.";
        MainActivity.rankingArString[2] = ".18000.";
        MainActivity.rankingArString[3] = ".24000.";
        MainActivity.rankingArString[4] = ".30000.";
        MainActivity.rankingArString[5] = ".36000.";
        MainActivity.rankingArString[6] = ".42000.";
        MainActivity.rankingArString[7] = ".48000.";
        MainActivity.rankingArString[8] = ".54000.";
        MainActivity.rankingArString[9] = ".60000.";
    }

    private void buildScoreBoard(){
        for(int i=0; i<MainActivity.scoreBoardStr.length; i++){
            MainActivity.scoreBoardStr[i] = "."+lex1.nextTokenScore()+"."+lex1.nextTokenScore()+"."+lex1.nextTokenScore()+".";
        }
        lex1.resetIndex1();
        setScoreBoardString(MainActivity.scoreBoardStr);

    }

    private void buildNewScoreBoard(){
        Lex l = new Lex();
        for(int i=0; i<MainActivity.scoreBoardStr.length; i++){
            MainActivity.scoreBoardStr[i] = "."+l.nextTokenScore()+"."+l.nextTokenScore()+"."+l.nextTokenScore()+".";
        }
        setScoreBoardString(MainActivity.scoreBoardStr);
    }

    private void buildUserRankBoardInt(){
        for(int i =0; i<MainActivity.rankingAr.length; i++){
            MainActivity.rankingAr[i] = Integer.parseInt(lex2.nextTokenRank());
        }
        lex2.resetIndex2();
        setRankingAr(MainActivity.rankingAr);
    }

    private void buildUserRankBoardString(){
        for(int i=0; i<MainActivity.rankingArString.length; i++){
            MainActivity.rankingArString[i] = "."+lex2.nextTokenRank()+".";
        }
        setUserRankAr(MainActivity.rankingArString);
    }

    public void autoSave(){
        String[] savedText1 = new String[MainActivity.MAX];
        String[] savedText2 = new String[MainActivity.MAX];
        for(int i =0; i<savedText1.length; i++){
            savedText1[i] = MainActivity.scoreBoardStr[i];
            savedText2[i] = "."+Integer.toString(MainActivity.rankingAr[i])+".";
        }
        Save(savedFile1,savedText1);
        Save(savedFile2,savedText2);
    }

    public void autoLoad(){
        File file1 = new File(path + "/highScores.txt");
        String[] loadFile1 = Load(file1);
        String finalString1 = "";
        for(int i =0; i<loadFile1.length; i++){
            finalString1 += loadFile1[i];
        }
        parseScoreBoard(finalString1);

        File file2 = new File(path + "/rankingBoard.txt");
        String[] loadFile2= Load(file2);
        String finalString2 = "";
        for(int i =0; i<loadFile2.length; i++){
            finalString2 += loadFile2[i];
        }
        parseRankBoard(finalString2);
    }

    private void parseScoreBoard(String in){
        lex1 = new Lex(in, ".", true);
        buildScoreBoard();
    }

    public void parseRankBoard(String in){
        lex2 = new Lex(in, ".", false);
        buildUserRankBoardInt();
        buildUserRankBoardString();

    }

    private void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    private static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }
}
