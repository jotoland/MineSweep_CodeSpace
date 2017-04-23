package com.tempusFugit.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
/**
 * Provides UI for the view with List.
 */
public class HighScoreFragment extends Fragment {
    String msg = "Android:";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView lvl;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_high_score, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);
            lvl = (TextView) itemView.findViewById(R.id.list_lvl);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        // Set numbers of List in RecyclerView.
        private static final int LENGTH = MainActivity.MAX;
        private final String[] mlvl = new String[LENGTH];
        private final String[] mMembers = new String[LENGTH];
        private final String[] mMemberEmails = new String[LENGTH];
        private final Drawable[] mMemberAvators;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            for(int i=0; i<LENGTH; i++){
                mMembers[i] = MainActivity.userBanner[i*3];
                mMemberEmails[i] = MainActivity.userBanner[i*3+2];
                mlvl[i] = MainActivity.userBanner[i*3+1];
            }
            TypedArray a = resources.obtainTypedArray(R.array.scores_img);
            mMemberAvators = new Drawable[a.length()];
            for (int i = 0; i < mMemberAvators.length; i++) {
                mMemberAvators[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.avator.setImageDrawable(mMemberAvators[position % mMemberAvators.length]);
            holder.name.setText(mMembers[position % mMembers.length]);
            holder.lvl.setText(mlvl[position % mlvl.length]);
            holder.description.setText(mMemberEmails[position % mMemberEmails.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}