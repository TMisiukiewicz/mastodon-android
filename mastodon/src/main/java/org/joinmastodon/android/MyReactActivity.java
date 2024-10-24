package org.joinmastodon.android;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

public class MyReactActivity extends ReactActivity {

	@Override
	protected String getMainComponentName() {
		return "ReactNativeScreen";
	}

	@Override
	protected ReactActivityDelegate createReactActivityDelegate() {
		return new DefaultReactActivityDelegate(this, getMainComponentName(), DefaultNewArchitectureEntryPoint.getFabricEnabled());
	}
}