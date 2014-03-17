package org.ydle.ui.rooms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ydle.R;
import org.ydle.data.model.Sensor;
import org.ydle.utils.AnimationUtils;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Jean-Baptiste on 05/02/14.
 */
public class SensorCard extends Card{

    private static final String LOG_TAG = SensorCard.class.getSimpleName();

    protected ImageView mStatusImageView;
    protected ImageView mIconImageView;
    protected TextView mValueTextView;

    private Sensor mSensor;
    private boolean isExpand;

    public SensorCard(Context context, Sensor sensor) {
        this(context, R.layout.sensor_card, sensor);
    }

    public SensorCard(Context context, int innerLayout, Sensor sensor) {
        super(context, innerLayout);
        mSensor = sensor;
        isExpand = false;
        init();
    }

    private void init(){
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                if (isExpand) {
                    AnimationUtils.collapse(view.findViewById(R.id.card_layout_expand));
                    isExpand = false;
                } else {
                    AnimationUtils.expand(view.findViewById(R.id.card_layout_expand), 100);
                    isExpand = true;
                }
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        mStatusImageView = (ImageView) parent.findViewById(R.id.card_status);
        mIconImageView = (ImageView) parent.findViewById(R.id.card_icon);
        mValueTextView = (TextView) parent.findViewById(R.id.card_value);

        if (mStatusImageView != null) {
            int statusColor;
            if (mSensor.isActive()) {
                statusColor = getContext().getResources().getColor(android.R.color.holo_green_light);
            } else {
                statusColor = getContext().getResources().getColor(android.R.color.holo_red_light);
            }
            mStatusImageView.setBackgroundColor(statusColor);
        }
//        if (mIconImageView != null)
//            mIconImageView.setImageDrawable(getContext().getResources().getDrawable(mSensor.getType().getIcon()));
        if (mValueTextView != null && mSensor.isActive())
            mValueTextView.setText(mSensor.getCurrentValue().toString());
    }
}
