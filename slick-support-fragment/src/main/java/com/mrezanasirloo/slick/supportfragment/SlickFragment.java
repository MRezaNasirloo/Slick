package com.mrezanasirloo.slick.supportfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mrezanasirloo.slick.SlickUniqueId;

import java.util.UUID;

import static com.mrezanasirloo.slick.SlickDelegateActivity.SLICK_UNIQUE_KEY;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-07
 */

public abstract class SlickFragment extends Fragment implements SlickUniqueId {

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            id = savedInstanceState.getString(SLICK_UNIQUE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SLICK_UNIQUE_KEY, id);
    }

    @Override
    public String getUniqueId() {
        return id = id != null ? id : UUID.randomUUID().toString();
    }
}
