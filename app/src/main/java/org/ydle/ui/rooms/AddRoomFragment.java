package org.ydle.ui.rooms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import org.ydle.R;
import org.ydle.config.Config;
import org.ydle.data.memoryprovider.MemoryProvider;
import org.ydle.data.model.Room;
import org.ydle.data.model.RoomType;
import org.ydle.network.manager.NetworkManager;
import org.ydle.ui.YdleFragment;
import org.ydle.utils.DisplayUtils;

import java.util.Observable;

public class AddRoomFragment extends YdleFragment {

    private static final String LOG_TAG = AddRoomFragment.class.getSimpleName();

    private EditText mNameEditText;
    private Spinner mRoomTypesSpinner;
    private EditText mDescriptionEditText;
    private Switch mActiveSwitch;
    private Button mValidateButton;

    private String[] mRoomTypes;
    private boolean mAdding = false;
    private Room mRoomToAdd;

    public static AddRoomFragment newInstance() {
        return new AddRoomFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_room_fragment, container, false);

        mNameEditText = (EditText) view.findViewById(R.id.add_room_name_edittext);
        mRoomTypesSpinner = (Spinner) view.findViewById(R.id.add_room_roomtypes_spinner);
        mDescriptionEditText = (EditText) view.findViewById(R.id.add_room_description_edittext);
        mActiveSwitch = (Switch) view.findViewById(R.id.add_room_active_switch);
        mValidateButton = (Button) view.findViewById(R.id.add_room_validate_button);

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRoomTypes = RoomType.getActiveRoomTypesName(MemoryProvider.getInstance().getRoomTypes());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mRoomTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoomTypesSpinner.setAdapter(adapter);
    }

    private void resetView() {
        mNameEditText.setText("");
        if (mRoomTypes != null && mRoomTypes.length >= 1) {
            mRoomTypesSpinner.setSelection(0);
        }
        mDescriptionEditText.setText("");
        mActiveSwitch.setChecked(true);
    }

    private void validate() {
        String name = mNameEditText.getText().toString();
        if(name.equals("")) {
            DisplayUtils.displayToast(getActivity(), getString(R.string.add_room_error_no_name));
        } else {
            mRoomToAdd = new Room();
            mRoomToAdd.setName(name);
            RoomType type = MemoryProvider.getInstance().getRoomType((String) mRoomTypesSpinner.getSelectedItem());
            mRoomToAdd.setType(type);
            mRoomToAdd.setDescription(mDescriptionEditText.getText().toString());
            mRoomToAdd.setActive(mActiveSwitch.isChecked());
            DisplayUtils.showProgressDialog(getActivity(), getString(R.string.add_room_dialog));
            mAdding = true;
            mNetworkManager.callPostRoom(mRoomToAdd);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (Config.displayLog()) {
            Log.d(LOG_TAG, "update");
        }
        if (observable == mNetworkManager) {
            if (data == NetworkManager.NetworkResult.PostRoomSuccess) {
                resetView();
                mNetworkManager.callGetAllRooms();
            } else if (data == NetworkManager.NetworkResult.PostRoomError) {
                DisplayUtils.dismissProgressDialog();
                DisplayUtils.displayToast(getActivity(), getString(R.string.add_room_failure));
                mAdding = false;
            } else if (data == NetworkManager.NetworkResult.GetAllRoomsSuccess && mAdding) {
                DisplayUtils.dismissProgressDialog();
                DisplayUtils.displayToast(getActivity(), getString(R.string.add_room_success));
                mAdding = false;
                getActivity().finish();
                RoomsActivity.displayRoomsActivity(getActivity(), true);
            }
        }
    }
}
