package com.andrewsking.dagobahsoundboard;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andrewsking.dagobahsoundboard.ui_controllers.PlaySoundFragment;
import com.andrewsking.dagobahsoundboard.view_models.SoundViewModel;

import java.util.ArrayList;


public class SoundArrayAdapter extends ArrayAdapter<Sound> {

    private Context context;
    private static final int itemLayout = R.layout.sound_list_item;
    private Sound[] sounds;

    public SoundArrayAdapter(Context context, PlaySoundFragment fragment, LiveData<Sound[]> liveData) {
        super(context, itemLayout, liveData.getValue());
        this.context = context;
        liveData.observe(fragment, (Sound[] sounds) -> {
            this.sounds = sounds;
            this.notifyDataSetChanged();
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Sound sound = this.sounds[position];
        View rowView = inflater.inflate(itemLayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(sound.getDisplayName());
        if (sound.isPlaying()) textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        return rowView;
    }
}
