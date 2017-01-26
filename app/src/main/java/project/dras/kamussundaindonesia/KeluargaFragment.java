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
public class KeluargaFragment extends Fragment {


    public KeluargaFragment() {
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
        words.add(new Word("Ayah","Bapa",R.drawable.family_father,R.raw.bapa));
        words.add(new Word("Ibu","Indung",R.drawable.family_mother,R.raw.indung));
        words.add(new Word("Kakak","Lanceuk",R.drawable.family_older_sister,R.raw.lanceuk));
        words.add(new Word("Adik","Adi",R.drawable.family_younger_brother,R.raw.adi));
        words.add(new Word("Cucu","Incu",R.drawable.family_daughter,R.raw.incu));
        words.add(new Word("Kakek","Aki",R.drawable.family_grandfather,R.raw.aki));
        words.add(new Word("Nenek","Nini",R.drawable.family_grandmother,R.raw.nini));
        words.add(new Word("Paman","Amang",R.drawable.family_older_brother,R.raw.amang));
        words.add(new Word("Tante","Bibi",R.drawable.family_younger_sister,R.raw.bibi));


        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.bg_keluarga);
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
