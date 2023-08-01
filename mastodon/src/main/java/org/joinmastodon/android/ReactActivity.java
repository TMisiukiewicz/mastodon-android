package org.joinmastodon.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.soloader.SoLoader;


public class ReactActivity extends Activity implements DefaultHardwareBackBtnHandler{
    private ReactRootView reactRootView;
	private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoLoader.init(this, false);

		reactRootView = new ReactRootView(this);
		mReactInstanceManager = ReactBridgeManager.shared.getReactNativeHost().getReactInstanceManager();
		reactRootView.startReactApplication(
				mReactInstanceManager,
				"ReactNativeScreen",
				null
		);
		setContentView(reactRootView);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mReactInstanceManager != null) {
			mReactInstanceManager.onHostPause(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mReactInstanceManager != null) {
			mReactInstanceManager.onHostResume(this, this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mReactInstanceManager != null) {
			mReactInstanceManager.onHostDestroy(this);
		}
		if (reactRootView != null) {
			reactRootView.unmountReactApplication();
		}
	}

	@Override
	public void onBackPressed() {
		if (mReactInstanceManager != null) {
			mReactInstanceManager.onBackPressed();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
			mReactInstanceManager.showDevOptionsDialog();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}