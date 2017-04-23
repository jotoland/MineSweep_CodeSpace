package com.tempusFugit.app;

import org.junit.Test;
import android.content.Context;
import android.content.Intent;

import static org.junit.Assert.*;
import java.util.HashSet;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TempusFugitUnitTest {

    @Test
    public void merge_isCorrect() throws Exception {
        int[] mSortAr = {1,2,3,4,5,6};
        int[] mUnsortedAr = {6,4,3,1,2,5};
        MergeSort m = new MergeSort();
        m.sort(mUnsortedAr);
        assertArrayEquals(mSortAr,mUnsortedAr);
    }

    @Test
    public void lex_isCorrect() throws Exception {
       char[] parsedString = {'T', 'p', 'w', 'j', 't','y','g','f','d','W'};
       String toBeParsed = "T.p.w.j.t.y.g.f.d.W";
        Lex l = new Lex(toBeParsed,".",false);
        //assertArrayEquals(parsedString,l.star);
    }

    @Test
    public void clearBlankNeighbors_isCorrect() throws Exception {

       initailize();
       clearBlankNeighbors(4,4);
        assertArrayEquals(expectedStatus, status);
    }

    private int bombCount, i, j, count;
    private boolean isbomb              = true;
    private int whereMyBombsAt[][];
    private int status[][];
    private boolean bombs[][];
    private int expectedStatus[][];
    private final int ROWS              = 5;
    private final int placedBombs       = 3;
    private final int COLS              = 5;
    private final int tile              = 10;
    private final int empty             =  0;

    public void initailize(){
        status           = new int[ROWS][COLS];
        whereMyBombsAt   = new int[ROWS][COLS];
        bombs            = new boolean[ROWS][COLS];
        expectedStatus = new int[5][5];
        for(i=0; i<ROWS; i++){
            for(j=0; j<COLS; j++){
                if(i==0 && j<3) {
                    expectedStatus[i][j] = tile;
                } else if ((i==0&&j==3)||(i==1&&j==3)){
                    expectedStatus[i][j] =1;
                }else if((i==1&&j<3&&j!=1)){
                    expectedStatus[i][j]=2;
                }else if((i==1&&j==1)){
                    expectedStatus[i][j] =3;
                }else{
                    expectedStatus[i][j] =empty;
                }
            }
        }
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
        i=0;
        j=0;
        while(bombCount< placedBombs){
            if(!bombs[i][j]){
                bombs[i][j] = isbomb;
                bombCount++;
                j++;
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

    /////////////METHOD FOR COUNTING NEIGHBOR BOMBS
    public int countNeighboringBombs1(int r, int c){
        count = 0;
        if ((r>0) && (c>0) && (bombs[r-1][c-1]))count++;
        if ((r>=0) && (c>0) && (bombs[r][c-1]))count++;
        if ((r<ROWS-1) && (c>0) && (bombs[r+1][c-1]))count++;
        if ((r<ROWS-1) && (c>=0) && (bombs[r+1][c]))count++;
        if ((r<ROWS-1) && (c<4) && (bombs[r+1][c+1]))count++;
        if ((r>=0) && (c<4) && (bombs[r][c+1]))count++;
        if ((r>0) && (c<4) && (bombs[r-1][c+1]))count++;
        if ((r>0) && (c<=4) && (bombs[r-1][c]))count++;
        return count;

    }


}