/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ydle.ui.rooms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.data.model.Room;
import org.ydle.data.model.Sensor;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.YdleFragment;
import org.ydle.ui.tools.CardFactory;
import org.ydle.utils.AnimationUtils;
import org.ydle.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.Observable;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class RoomFragment extends YdleFragment {

    private static final String LOG_TAG = RoomFragment.class.getSimpleName();

    private static final String BUNDLE_ROOM = "BUNDLE_ROOM";

    private boolean mDeleting = false;
    private Room mRoom;

    private Button mDeleteButton;
    private ViewGroup mEditButtonLayout;
    private boolean mEditButtonLayoutExpand = false;

    public static RoomFragment newInstance(Room room) {
        RoomFragment fragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ROOM, room);
        fragment.setArguments(bundle);
        fragment.setRoom(room);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_fragment, container, false);
        mEditButtonLayout = (ViewGroup) view.findViewById(R.id.room_edit_button_layout);
        mDeleteButton = (Button) view.findViewById(R.id.room_delete_button);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete_room_dialog_title)
                        .setMessage(R.string.delete_room_dialog_message)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DisplayUtils.showProgressDialog(getActivity(), getString(R.string.delete_room_progressdialog));
                                mDeleting = true;
                                mNetworkManager.callDeleteRoom(mRoom);
                            }
                        })
                        .show();
            }
        });

        initCards();
    }

    private void initCards() {
        ArrayList<Card> cards = new ArrayList<Card>();

        RoomCard roomCard = CardFactory.getRoomCard(getActivity(), mRoom);
        roomCard.getCardHeader().setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
            @Override
            public void onButtonItemClick(Card card, View view) {

                if (mEditButtonLayoutExpand) {
                    AnimationUtils.collapse(mEditButtonLayout);
                    mEditButtonLayoutExpand = false;
                } else {
                    AnimationUtils.expand(mEditButtonLayout, 70);
                    mEditButtonLayoutExpand = true;
                }
            }
        });
        cards.add(roomCard);

        if (mRoom.getSensors() != null) {
            for (Sensor sensor : mRoom.getSensors()) {
                cards.add(CardFactory.getSensorCard(getActivity(), sensor));
            }
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) getView().findViewById(R.id.room_cardlistview);
        if (listView != null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }



    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.DeleteRoomSuccess) {
                mNetworkManager.callGetAllRooms();
            } else if (data == NetworkManager.NetworkResult.DeleteRoomError) {
                DisplayUtils.dismissProgressDialog();
                DisplayUtils.displayToast(getActivity(), getString(R.string.delete_room_failure));
            } else if (data == NetworkManager.NetworkResult.GetAllRoomsSuccess && mDeleting) {
                DisplayUtils.dismissProgressDialog();
                DisplayUtils.displayToast(getActivity(), getString(R.string.delete_room_success));
                mDeleting = false;
                int position = ((RoomsActivity) getActivity()).getCurrentPosition();
                getActivity().finish();
                RoomsActivity.displayRoomsActivity(getActivity(), (position - 1) >= 0 ? position - 1 : 0);
            }
        }
    }
}