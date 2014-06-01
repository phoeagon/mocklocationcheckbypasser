package com.kisslab.mocklocation.checkbypasser;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.content.ContentResolver;
import android.graphics.Color;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Bypass implements IXposedHookLoadPackage {
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		XposedBridge.log(lpparam.packageName);
		if (!lpparam.packageName.equals("com.tencent.mm")) 
			//change to the package name of the app you want to intercept
			return;
		XposedBridge.log("we are in target App!");
        findAndHookMethod("android.provider.Settings.Secure",
        		lpparam.classLoader, 
        		"getString",
        		ContentResolver.class,
        		String.class,
        		new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    	if (param.args[1].equals("mock_location"))
                    		param.setResult("0");
                    }
            });
	}
}
