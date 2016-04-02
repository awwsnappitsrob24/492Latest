package com.example.robien.beachbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robien on 4/2/2016.
 */
public class InviteAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public InviteAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Invite object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        InviteHolder inviteHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.invite_row_layout, parent, false);
            inviteHolder = new InviteHolder();
            inviteHolder.tx_classinvite = (TextView)row.findViewById(R.id.tx_name);

            row.setTag(inviteHolder);
        }
        else {
            inviteHolder = (InviteHolder)row.getTag();
        }

        Invite invite = (Invite)this.getItem(position);
        inviteHolder.tx_classinvite.setText(invite.getClassInvite());
        return row;
    }

    static class InviteHolder {
        TextView tx_classinvite;
    }
}
