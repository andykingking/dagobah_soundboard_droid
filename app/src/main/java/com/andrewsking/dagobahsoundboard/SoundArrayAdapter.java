package com.andrewsking.dagobahsoundboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SoundArrayAdapter extends ArrayAdapter<Sound> {

    private Context context;
    private static final int itemLayout = R.layout.sound_list_item;

    public SoundArrayAdapter(Context context) {
        super(context, itemLayout, SoundStore.getInstance(context).getSounds());
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Sound[] sounds = SoundStore.getInstance(context).getSounds();
        Sound sound = sounds[position];
        View rowView = inflater.inflate(itemLayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(sound.getDisplayName());
        if (sound.isPlaying()) textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        return rowView;
    }
}
