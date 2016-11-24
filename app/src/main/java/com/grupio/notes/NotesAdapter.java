package com.grupio.notes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.SimpleBaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 23/11/16.
 */

public class NotesAdapter extends SimpleBaseListAdapter<NotesData, NotesAdapter.Holder> {

    public NotesAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_notes;
    }

    @Override
    public Holder setViewHolder(View convertView) {
        return new Holder(convertView);
    }

    @Override
    public void getView(int position, Holder holder) {
        NotesData mNoteData = getItem(position);
        holder.mNoteText.setText(mNoteData.getNoteText());
        if (mNoteData.getNoteType().equalsIgnoreCase(NotesListActivity.THINGS_TO_DO)) {
            mHolder.mAlarmLayout.setVisibility(View.VISIBLE);
        } else {
            mHolder.mAlarmLayout.setVisibility(View.GONE);
        }

        if (mNoteData.isSelected()) {
            mHolder.mCheckbox.setSelected(true);
        } else {
            mHolder.mCheckbox.setSelected(false);
        }

        mHolder.mCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mNoteData.setSelected(true);
                getItem(position).setSelected(true);
            } else {
                getItem(position).setSelected(false);
                mNoteData.setSelected(false);
            }
            notifyDataSetChanged();
        });

    }

    public List<NotesData> getSelected() {
        List<NotesData> mSelectedList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).isSelected()) {
                mSelectedList.add(getItem(i));
                Log.i("NoteAdapter", "getSelected: DeleteList: \nText:" + getItem(i).getNoteText() + "\nId: " + getItem(i).getId() + "\nNoteId: " + getItem(i).getNoteId() + "\nSync" + getItem(i).getNoteSync());
            }
        }

        Log.i("NoteAdapter", "getSelected: Sending elements count: " + mSelectedList.size());
        return mSelectedList;
    }

    public class Holder {
        CheckBox mCheckbox;
        TextView mNoteText, mAlarmText;
        LinearLayout mAlarmLayout;

        public Holder(View convertView) {
            mCheckbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            mNoteText = (TextView) convertView.findViewById(R.id.noteTxt);
            mAlarmLayout = (LinearLayout) convertView.findViewById(R.id.alarmLay);
            mAlarmText = (TextView) convertView.findViewById(R.id.alarmTxt);
        }
    }

}
