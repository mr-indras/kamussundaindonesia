package project.dras.kamussundaindonesia;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AngkaFragment extends Fragment {


    public AngkaFragment() {
        // Required empty public constructor
    }

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //pause playback
                        mMediaPlayer.pause();
                        //start again from begining
                        mMediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //resume playback
                        mMediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //stop playback and cleanup resources
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //lsit words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Satu","Hiji",R.drawable.number_one,R.raw.hiji));
        words.add(new Word("Dua","Dua",R.drawable.number_two,R.raw.dua));
        words.add(new Word("Tiga","Tilu",R.drawable.number_three,R.raw.tilu));
        words.add(new Word("Empat","Opat",R.drawable.number_four,R.raw.opat));
        words.add(new Word("Lima","Lima",R.drawable.number_five,R.raw.lima));
        words.add(new Word("Enam","Genep",R.drawable.number_six,R.raw.genep));
        words.add(new Word("Tujuh","Tujuh",R.drawable.number_seven,R.raw.tujuh));
        words.add(new Word("Delapan","Dalapan",R.drawable.number_eight,R.raw.dalapan));
        words.add(new Word("Sembilan","Salapan",R.drawable.number_nine,R.raw.salapan));
        words.add(new Word("Sepuluh","Sapuluh",R.drawable.number_ten,R.raw.sapuluh));


        final WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.bg_angka);
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word = words.get(position); //Words bisa diakses jika modifiernya final
                releaseMediaPlayer();

                //request audio focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //start playback
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

}
