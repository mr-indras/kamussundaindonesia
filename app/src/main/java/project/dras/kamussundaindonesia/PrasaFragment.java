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
public class PrasaFragment extends Fragment {


    public PrasaFragment() {
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
        words.add(new Word("Bagaimana Kabarnya ?","Kumaha Damang ?",R.raw.kumaha_damang));
        words.add(new Word("Sehat/Baik","Sae/Pangestu/Damang",R.raw.sae));
        words.add(new Word("Mau Kemana ?","Bade Kamana ?",R.raw.bade_kamana));
        words.add(new Word("Mau ke Pasar","Bade ka Pasar",R.raw.ka_pasar));
        words.add(new Word("Siapa Nama Anda ?","Saha Nami Anjeun ?",R.raw.saha_nami));
        words.add(new Word("Nama Saya ....","Nami Abdi ...",R.raw.nami_abdi));
        words.add(new Word("Darimana Asalmu ?","Timana Kawit ?",R.raw.timana));
        words.add(new Word("Saya dari Garut","Abdi ti Garut",R.raw.tigarut));
        words.add(new Word("Lama tidak bertemu","Awis tepang",R.raw.awis_tepang));


        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.bg_prasa);
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
