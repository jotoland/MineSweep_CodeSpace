package com.tempusFugit.app;

/**
 * Created by johntoland on 12/1/16.
 */
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;

public class FindUnpairedNumbersUnitTest {

    public static Integer[] findUnpairedNumbers(Integer[] numbers){
        if(numbers == null){
            throw new IllegalArgumentException();
        }
        HashSet<Integer> result = new HashSet<Integer>();
        for(int next: numbers){
            if(result.contains(next)){
                result.remove(next);
            }else{
                result.add(next);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    @Test
    public void emptyArray_isCorrect() throws Exception{
        Integer[] mNull = {};
        Integer[] mExpectedOutput = {};
        assertArrayEquals(mExpectedOutput, findUnpairedNumbers(mNull));
    }

    @Test
    public void negativeIntArray_isCorrect() throws Exception{
        Integer[] mNegativeIntArray = {-1, -4, -4, 5, -6};
        Integer[] mExpectedOutput = {-1, 5, -6};
        assertArrayEquals(mExpectedOutput, findUnpairedNumbers(mNegativeIntArray));
    }

    @Test
    public void pairedIntegers_isCorrect() throws Exception{
        Integer[] mPairedInts = {1, 1, 2, 2, 4, 4, 5, 5, 6, 6};
        Integer[] mExpectedOutput = {};
        assertArrayEquals(mExpectedOutput,findUnpairedNumbers(mPairedInts));
    }

    @Test
    public void unPairedIntegers_isCorrect() throws Exception{
        Integer[] mPairedInts = {1, 2, 4, 5, 6};
        Integer[] mExpectedOutput = {1, 2, 4, 5, 6};
        assertArrayEquals(mExpectedOutput,findUnpairedNumbers(mPairedInts));
    }

    @Test
    public void evenRepeatingInt_isCorrect() throws Exception{
        Integer[] mEvenRepeatInt = {1, 2, 2, 2, 2, 4, 5, 6};
        Integer[] mExpectedOutput = {1, 4, 5, 6};
        assertArrayEquals(mExpectedOutput,findUnpairedNumbers(mEvenRepeatInt));
    }

    @Test
    public void oddRepeatingInt_isCorrect() throws Exception{
        Integer[] mOddRepeatInt = {1, 2, 2, 2, 4, 5, 6};
        Integer[] mExpectedOutput = {1, 2, 4, 5, 6};
        assertArrayEquals(mExpectedOutput,findUnpairedNumbers(mOddRepeatInt));
    }



}
