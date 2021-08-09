package it.mbkj.kuangji

import android.app.Activity
import android.app.Application
import android.os.Bundle
import it.mbkj.lib.base.AppManager

class BaseActivityLifecycleCallbacks:Application.ActivityLifecycleCallbacks{
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        AppManager.getSingleton().addActivity(p0)
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {
        AppManager.getSingleton().finishActivity(p0)
    }
}