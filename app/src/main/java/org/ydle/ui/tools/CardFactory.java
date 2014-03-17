package org.ydle.ui.tools;


import android.content.Context;

import org.ydle.R;
import org.ydle.data.model.Room;
import org.ydle.data.model.Sensor;
import org.ydle.ui.rooms.RoomCard;
import org.ydle.ui.rooms.SensorCard;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Jean-Baptiste on 06/02/14.
 */
public class CardFactory {

    private static final String LOG_TAG = CardFactory.class.getSimpleName();

    public static SensorCard getSensorCard(Context context, Sensor sensor) {
        SensorCard card = new SensorCard(context, sensor);

        CardHeader header = new CardHeader(context);
        header.setTitle(sensor.getName());
        card.addCardHeader(header);
        return card;
    }

    public static RoomCard getRoomCard(Context context, Room room) {
        RoomCard card = new RoomCard(context, room);

        CardHeader header = new CardHeader(context);
        header.setOtherButtonVisible(true);
        header.setOtherButtonDrawable(R.drawable.ic_edit);
        header.setTitle(room.getType().getName());
        card.addCardHeader(header);
        return card;
    }
}
