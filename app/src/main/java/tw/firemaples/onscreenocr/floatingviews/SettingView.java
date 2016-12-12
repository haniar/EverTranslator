package tw.firemaples.onscreenocr.floatingviews;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import tw.firemaples.onscreenocr.R;
import tw.firemaples.onscreenocr.utils.Tool;

/**
 * Created by firemaples on 04/11/2016.
 */

public class SettingView extends FloatingView {
    private Tool tool;
    private OnSettingChangedCallback callback;

    public SettingView(Context context, OnSettingChangedCallback callback) {
        super(context);
        this.callback = callback;
        tool = Tool.getInstance();
        setViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_setting;
    }

    @Override
    protected int getLayoutSize() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void setViews() {
        CheckBox cb_debugMode = (CheckBox) getRootView().findViewById(R.id.cb_debugMode);
        CheckBox cb_enableTranslation = (CheckBox) getRootView().findViewById(R.id.cb_enableTranslation);
        CheckBox cb_startingWithSelectionMode = (CheckBox) getRootView().findViewById(R.id.cb_startingWithSelectionMode);
        CheckBox cb_removeLineBreaks = (CheckBox) getRootView().findViewById(R.id.cb_removeLineBreaks);
        getRootView().findViewById(R.id.bt_close).setOnClickListener(onClickListener);

        cb_debugMode.setOnCheckedChangeListener(onCheckChangeListener);
        cb_enableTranslation.setOnCheckedChangeListener(onCheckChangeListener);
        cb_startingWithSelectionMode.setOnCheckedChangeListener(onCheckChangeListener);
        cb_removeLineBreaks.setOnCheckedChangeListener(onCheckChangeListener);

        cb_debugMode.setChecked(tool.isDebugMode());
        cb_enableTranslation.setChecked(tool.isEnableTranslation());
        cb_startingWithSelectionMode.setChecked(tool.startingWithSelectionMode());
        cb_removeLineBreaks.setChecked(tool.removeLineBreaks());
    }

    private CompoundButton.OnCheckedChangeListener onCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.cb_debugMode) {
                tool.setDebugMode(isChecked);
            } else if (id == R.id.cb_enableTranslation) {
                tool.setEnableTranslation(isChecked);
                if (callback != null) {
                    callback.onEnableTranslationChanged(isChecked);
                }
            } else if (id == R.id.cb_startingWithSelectionMode) {
                tool.setStartingWithSelectionMode(isChecked);
            } else if (id == R.id.cb_removeLineBreaks) {
                tool.setRemoveLineBreaks(isChecked);
            }
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.bt_close) {
                SettingView.this.detachFromWindow();
            }
        }
    };

    public interface OnSettingChangedCallback {
        void onEnableTranslationChanged(boolean enableTranslation);
    }
}
