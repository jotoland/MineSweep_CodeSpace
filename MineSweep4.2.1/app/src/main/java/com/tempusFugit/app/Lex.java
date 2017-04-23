package com.tempusFugit.app;
/**
 * Created by johntoland on 11/5/16.
 */
public class Lex{

    private final int STRINGLING = 70;
    private int numTokens = 0;
    private int index1 = 0;
    private int index2 = 0;

    ///FOR UNIT TEST
    //public char[] star = new char[10];

    public Lex(){
    }

    public Lex(String s, String d, boolean isAScoreBoard)
    {
        if(isAScoreBoard){
            MainActivity.userBanner = new String[STRINGLING];
        }else{
            ///COMMENT OUT FOR UNIT TEST
            MainActivity.userRank = new String[STRINGLING];
        }
        ///COMMENT OUT FOR UNIT TEST
        String coin;
        //FOR UNIT TEST
        //char coin;
        String cha1;
        String cha2 = "null";
        int end = 0;
        int start = 0;
        String m = d;

        for(int i =0; i<s.length()-1; i++){
            cha1 = s.substring(i, i+1);
            cha2 = s.substring(i+1, i+2);

            if(isDelim(cha1,m) && !isDelim(cha2,m)){
                start = i+1;
            }else if(!isDelim(cha1,m) && isDelim(cha2,m)){
                end = i+1;
                //COMMENT OUT FOR UNIT TEST
                coin = s.substring(start,end);
                //FOR UNIT TEST
                //coin =s.charAt(start);
                if(isAScoreBoard){
                    //COMMENT OUT FOR UNIT TEST
                    MainActivity.userBanner[numTokens++] = coin;
                }else{
                    //COMMENT OUT FOR UNIT TEST
                    MainActivity.userRank[numTokens++] = coin;
                    //FOR UNIT TEST
                    //star[numTokens++]=coin;
                }
            }
        }
        if(end<s.length() && !isDelim(cha2,m)){
            //COMMENT OUT FOR UNIT TEST
            coin = s.substring(start);
            //FOR UNIT TEST
            //coin = s.charAt(start);
            if(isAScoreBoard){
                //COMMENT OUT FOR UNIT TEST
                //MainActivity.userBanner[numTokens] = coin;
                //numTokens++;
            }else{
                //COMMENT OUT FOR UNIT TEST
                MainActivity.userRank[numTokens]= coin;
                //FOR UNIT TEST
                //star[numTokens]=coin;
                numTokens++;
            }
        }
    }

    public String nextTokenScore()
    {
        return(MainActivity.userBanner[index1++]);
    }

    public String nextTokenRank()
    {
        return(MainActivity.userRank[index2++]);
    }

    private boolean isDelim(String c, String d)
    {
        return(d.contains(c));
    }

    public void resetIndex2() {
        index2 = 0;
    }

    public void resetIndex1() {
        index1 = 0;
    }

}
