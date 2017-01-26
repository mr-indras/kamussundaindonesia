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
public class WarnaFragment extends Fragment {


    public WarnaFragment() {
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
        View rootView = inflater.inflate(R.layout.word_list,container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //lsit words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Merah","Beureum",R.drawable.color_red,R.raw.bereum));
        words.add(new Word("Kuning","Koneng",R.drawable.color_mustard_yellow,R.raw.koneng));
        words.add(new Word("Hijau","Hejo",R.drawable.color_green,R.raw.hejo));
        words.add(new Word("Coklat","Coklat",R.drawable.color_brown,R.raw.coklat));
        words.add(new Word("Hitam","Hideung",R.drawable.color_black,R.raw.hideung));
        words.add(new Word("Putih","Bodas",R.drawable.color_white,R.raw.bodas));
        words.add(new Word("Abu-abu","Kulawu",R.drawable.color_gray,R.raw.kulawu));


        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.bg_warna);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word = words.get(position); //Words bisa diakses jika modifiernya final
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

}
