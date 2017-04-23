package com.tempusFugit.app;

import android.os.IBinder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
/**
 * Created by johntoland on 10/23/16.
 */
public class CustomInputFragment extends Fragment implements Button.OnClickListener{
    private Button submit;
    public TextView pageTitle;
    public TextView pageEllo;
    public TextView bombTitle;
    public TextView rowTitle;
    public EditText bombInput;
    public EditText rowInput;
    public TextView outputTv1;
    public TextView outputTv2;
    public int bombInt;
    public double bombDub;
    public int rowInt;
    public double rowDub;
    public String j = "switch";
    String msg = "ANDROID:";
    UserClass u = new UserClass();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_custominput, container, false);
        submit = (Button) view.findViewById(R.id.submit_button);
        //submit.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        //submit.setTextColor(getResources().getColor(R.color.white));
        bombInput=(EditText) view.findViewById(R.id.bomb_input);
        rowInput=(EditText) view.findViewById(R.id.row_input);
        rowInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        bombInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        pageTitle=(TextView) view.findViewById(R.id.custom_input);
        pageTitle.setBackgroundResource(R.color.colorPrimaryDark);
        pageEllo=(TextView) view.findViewById(R.id.custom_ello);
        pageEllo.setBackgroundResource(R.color.colorPrimaryDark);
        bombTitle=(TextView) view.findViewById(R.id.bomb_text);
        bombTitle.setBackgroundResource(R.color.colorPrimaryDark);
        rowTitle=(TextView) view.findViewById(R.id.row_text);
        rowTitle.setBackgroundResource(R.color.colorPrimaryDark);
        outputTv1=(TextView) view.findViewById(R.id.output_tv1);
        outputTv2=(TextView) view.findViewById(R.id.output_tv2);
        submit.setOnClickListener(this);
        rowInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    onClick(null);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void setText(String text1,String text2) {
        TextView b = (TextView) getView().findViewById(R.id.output_tv1);
        TextView r = (TextView) getView().findViewById(R.id.output_tv2);//UPDATE
        b.setText(text1);
        b.setBackgroundResource(R.color.medium_grey);
        r.setText(text2);
        r.setBackgroundResource(R.color.medium_grey);
    }

    public void onClick(View v){
        Log.d(msg, "onClickEvent");
        String b = bombInput.getText().toString();//Nothing here yet
        String r = rowInput.getText().toString();
        if (b.length()==0||r.length()==0) {
            b = " [ello]:BOMBS:ERROR:\n<no input data specified> ";
            r = " [ello]:ROWS:ERROR:\n<no input data specified> ";
            setText(b,r);
        }else if (b.length() > 0 && r.length() > 0){
            outputTv1.setText("");
            outputTv2.setText("");
            bombDub = Double.parseDouble(b);
            bombInt = (int) Math.round(bombDub);
            rowDub = Double.parseDouble(r);
            rowInt = (int) Math.round(rowDub);
            if((rowInt>5&&rowInt<Graphics2View.maxIntRows+1)&&(bombInt>2&&bombInt<61)){
                Fragment fragment;
                fragment = new GameFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(j);
                ft.replace(R.id.content, fragment);
                ft.commit();
            }else if((rowInt<6||rowInt>Graphics2View.maxIntRows)||(bombInt<3||bombInt>60)){
                String help = "";
                if((rowInt<6||rowInt>Graphics2View.maxIntRows)&&(bombInt<3||bombInt>60)){
                    b = " [ello]:BOMBS:ERROR:\n<invalid input data specified>\nBOMBS:\nEnter a number [Min:3,Max:60] ";
                    r = " [ello]:ROWS:ERROR:\n<invalid input data specified>\nROWS:\nEnter a number [Min:6,Max:"+Graphics2View.maxRows+"] ";
                    setText(b,r);
                }else if (bombInt<3||bombInt>60){
                    b = " [ello]:BOMBS:ERROR:\n<invalid input data specified> ";
                    help = " BOMBS:\nEnter a number [Min:3,Max:60] ";
                    setText(b,help);
                }else if(rowInt<6||rowInt>Graphics2View.maxIntRows){
                    r = " [ello]:ROWS:ERROR:\n<invalid input data specified> ";
                    help = " ROWS:\nEnter a number [Min:6,Max:"+Graphics2View.maxRows+"] ";
                    setText(help,r);
                }
            }
        }
        Log.d(msg, "onClick:  bombInput = [" + bombInt + "]");
        Log.d(msg, "onClick:  rowInput = [" + rowInt + "]");
        closeKeyboard(getActivity(), submit.getWindowToken());
        u.gameCustomLevelChosenByUser(bombInt,rowInt);
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}
