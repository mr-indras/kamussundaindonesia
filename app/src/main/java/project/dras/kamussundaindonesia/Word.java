package project.dras.kamussundaindonesia;

/**
 * Created by Dras on 27/07/2016.
 */
public class Word {
    private String mDefaultTranslation;
    private String mSundaTranslation;
    private int mImageResourceID = HAS_NO_IMAGE_ID;
    private static final int HAS_NO_IMAGE_ID = -1;
    private int mAudioResourceId;

    public Word(String defaultTranslation, String sundaTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mSundaTranslation = sundaTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String sundaTranslation, int imageResourceID, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mSundaTranslation = sundaTranslation;
        mImageResourceID = imageResourceID;
        mAudioResourceId = audioResourceId;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getSundaTranslation(){
        return mSundaTranslation;
    }

    public int getImageResourceID(){return mImageResourceID;}

    public boolean hasImageID(){
        return mImageResourceID != HAS_NO_IMAGE_ID;
    }

    public int getAudioResourceId(){return  mAudioResourceId;}
}
