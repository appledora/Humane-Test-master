package com.thegorgeouscows.team.finalrev;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.dish,
            R.drawable.cloth,
            R.drawable.drop

    };

    public int[] background = {
            R.drawable.background,
            R.drawable.background,
            R.drawable.background
    };

    public String[] slide_headings = {
            "“If you want to eliminate hunger, everybody has to be involved.” ",
            "“No one has ever become poor by giving.”",
            "“It's not how much we give but how much love we put into giving.” "
    };

    public String[] slide_descs = {
            "BONO", "Anne Frank", "Mother Teresa"
    };

    public String[] button_text = {
            "Share food", "Share clothe", "Donate Blood"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }
    @Override

    public boolean isViewFromObject(View view, Object object) {

        return (view==(LinearLayout)object);
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.slidelinearlayout);
        ImageView slideImageView = view.findViewById(R.id.imageView);
        TextView slideHeading = view.findViewById(R.id.quote);
        TextView slideDescription = view.findViewById(R.id.quoter);
        Button pressButton = view.findViewById(R.id.button);


        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);
        pressButton.setText(button_text[position]);

        pressButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                if (position == 0) {
                    Log.i("my","FOOOD");
                }
                else if (position == 1) {
                    Log.i("my","CLOOTH");
                                  }
                else if(position == 2) {
                    Log.i("my","BLOOOOD");
                                   }

            }
        });

        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
