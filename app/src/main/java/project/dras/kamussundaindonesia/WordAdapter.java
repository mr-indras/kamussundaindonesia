package project.dras.kamussundaindonesia;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dras on 27/07/2016.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceID;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceID){
        super(context,0,words);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        TextView sundaTextView = (TextView)listItemView.findViewById(R.id.sunda_text_view);
        sundaTextView.setText(currentWord.getSundaTranslation());

        TextView defaultTextView = (TextView)listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView iconImage = (ImageView)listItemView.findViewById(R.id.image_icon);

        if(currentWord.hasImageID()) {
            iconImage.setImageResource(currentWord.getImageResourceID());
            iconImage.setVisibility(View.VISIBLE);
        }
        else{
            iconImage.setVisibility(View.GONE);
        }

        int color = ContextCompat.getColor(getContext(),mColorResourceID);
        LinearLayout textContainer = (LinearLayout)listItemView.findViewById(R.id.text_container);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
