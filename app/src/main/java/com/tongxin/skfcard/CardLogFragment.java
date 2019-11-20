package com.tongxin.skfcard;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tongxin.cardemulation.SkfInterface;

/**
 * Created by call on 2019/11/20.
 */

public class CardLogFragment extends Fragment {

    TextView _textview = null;
    ScrollView _scrollview = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.log_fragment, container, false);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        _scrollview = new ScrollView(getActivity());
        _textview = new TextView(getActivity());
        _textview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        _scrollview.addView(_textview);
        layout.addView(_scrollview);
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LiveEventBus.get()
                .with(SkfInterface.KEY_TEST_OBSERVE, String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        logText(s);
                    }
                });
    }

    private static String bytesToString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b & 0xFF));
        }
        return sb.toString();
    }

    private void logText(String message) {
        _scrollview.post(new Runnable() {
            public void run() {
                _scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        _textview.append("\n->");
        _textview.append(message);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
