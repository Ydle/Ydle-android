package org.ydle.ui.rooms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ydle.R;
import org.ydle.data.model.Room;
import org.ydle.utils.AnimationUtils;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Jean-Baptiste on 05/02/14.
 */
public class RoomCard extends Card{

    private static final String LOG_TAG = RoomCard.class.getSimpleName();

    protected ImageView mStatusImageView;
    protected ImageView mIconImageView;
    protected TextView mValueTextView;
    protected TextView mDescriptionTextView;

    private Room mRoom;
    private boolean isExpand;

    public RoomCard(Context context, Room room) {
        this(context, R.layout.room_card, room);
    }

    public RoomCard(Context context, int innerLayout, Room room) {
        super(context, innerLayout);
        mRoom = room;
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
        mDescriptionTextView = (TextView) parent.findViewById(R.id.card_descprition);

        if (mStatusImageView != null) {
            int statusColor;
            if (mRoom.isActive()) {
                statusColor = getContext().getResources().getColor(android.R.color.holo_green_light);
            } else {
                statusColor = getContext().getResources().getColor(android.R.color.holo_red_light);
            }
            mStatusImageView.setBackgroundColor(statusColor);
        }
//        if (mIconImageView != null)
//            mIconImageView.setImageDrawable(getContext().getResources().getDrawable(mRoom.getType().getIcon()));
        if (mValueTextView != null)
            mValueTextView.setText(mRoom.getName());

        if (mDescriptionTextView != null)
            mDescriptionTextView.setText(mRoom.getDescription());
    }
}
