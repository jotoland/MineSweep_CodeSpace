package com.tempusFugit.app;
/**
 * Created by johntoland on 11/5/16.
 */
public class MergeSort {
    private int[] array;
    private int[] tempMergArr;
    private int length;
    public int found;
    ///COMMENT OUT FOR UNIT TESTS
    public UserClass u = new UserClass();

    public void sort(int inputArr[]) {
        this.array = inputArr;
        this.length = inputArr.length;
        this.tempMergArr = new int[length];
        doMergeSort(0, length - 1);
        ///COMMENT OUT FOR UNIT TESTS
        found = findNewRank();
        u.setLocation(found);
    }
    ///COMMENT OUT FOR UNIT TESTS
    public int findNewRank(){
        int temp =0;
        for(int i =0; i<array.length; i++){
            if(array[i]==u.getNewRank()){
                temp = i;
            }
        }
        return temp;
    }

    private void doMergeSort(int lowerIndex, int higherIndex) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            doMergeSort(lowerIndex, middle);
            doMergeSort(middle + 1, higherIndex);
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }
    }
}