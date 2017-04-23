package com.tempusFugit.app;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
/**
 * Created by johntoland on 10/23/16.
 */
public class NewUserFragment extends Fragment implements Button.OnClickListener{
    UserClass u = new UserClass();
    public String grabUserName;
    private Button submit;
    public TextView newUser;
    public EditText nameInput;
    public TextView outputTv1;
    String msg = "ANDROID:";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_new_user, container, false);
        submit = (Button) view.findViewById(R.id.submit_button);
        //submit.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        //submit.setTextColor(getResources().getColor(R.color.white));
        newUser=(TextView) view.findViewById(R.id.new_user);
        newUser.setBackgroundResource(R.color.colorPrimaryDark);
        nameInput=(EditText) view.findViewById(R.id.name_input);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        outputTv1=(TextView) view.findViewById(R.id.output_tv1);
        submit.setOnClickListener(this);
        nameInput.setOnKeyListener(new OnKeyListener() {
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

    public void setText(String text1) {
        TextView debugMess = (TextView) getView().findViewById(R.id.output_tv1);
        debugMess.setText(text1);
        debugMess.setBackgroundResource(R.color.medium_grey);
    }

    String j = "switch";
    public void onClick(View v){
        String help= "";
        Log.d(msg, "onClickEvent");
        grabUserName = nameInput.getText().toString();
        if(grabUserName.length()>14){
            help = "          Error! Username Too Big!\n              [Max:14 Characters]";
            setText(help);
            return;
        }else if(grabUserName.contains(".")) {
            help = "      Error! Do Not Use a Period(.)!      ";
            setText(help);
            return;
        }
        if(grabUserName.length() == 0)grabUserName = "Doug Funnie";
        u.setUserName(grabUserName);
        Log.d(msg, "input name is = " + u.getUserName());
        closeKeyboard(getActivity(), submit.getWindowToken());
        switchFrags();
        Graphics2View.gameLevel(0);
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public boolean switchFrags(){
        Fragment fragment;
        fragment = new GameFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(j);
        ft.replace(R.id.content, fragment);
        ft.commit();
        return true;
    }
}