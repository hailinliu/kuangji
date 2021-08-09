package it.mbkj.lib.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

class AppManager private constructor(){

   companion object{
     @JvmField
      val activitystack = Stack<Activity>()

           private var instance2: AppManager? = null
            @JvmStatic
           fun getSingleton(): AppManager {
               if (instance2 == null) {
                   instance2 = AppManager()
               }
               return instance2!!
           }
       }


     fun addActivity(activity: Activity){
         activitystack.add(activity)
    }
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activitystack.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishSingleActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            if (activitystack.contains(activity)) {
                activitystack.remove(activity)
            }
            activity.finish()
            activity = null
        }
    }

    fun finishActivity(cls: Class<*>) {
        val var2: MutableIterator<*> = activitystack.iterator()
        while (var2.hasNext()) {
            val activity = var2.next() as Activity
            if (activity.javaClass == cls) {
                var2.remove()
                activity.finish()
                // this.finishActivity(activity);
            }
        }
    }

    fun finishAllActivity() {
        var i = 0
        val size: Int = activitystack.size
        while (i < size) {
            if (null != activitystack.get(i)) {
                (activitystack.get(i) as Activity).finish()
            }
            ++i
        }
        activitystack.clear()
    }

    fun finishAllActivity(cls: Class<*>) {
        val iterator: MutableIterator<*> = activitystack.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next() as Activity?
            if (next!!.javaClass != cls && next != null) {
                next.finish()
                iterator.remove()
            }
        }
    }

    fun finishAllActivity(cls: String) {
        val iterator: MutableIterator<*> = activitystack.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next() as Activity
            if (next.javaClass.name.contains(cls)) {
                next.finish()
                iterator.remove()
            }
        }
    }

    fun AppExit(context: Context) {
        try {
            this.finishAllActivity()
            val activityMgr = context.getSystemService("activity") as ActivityManager
            activityMgr.restartPackage(context.packageName)
            System.exit(0)
        } catch (var3: Exception) {
        }
    }

    fun getAllActivities(): Stack<Activity>? {
        return activitystack
    }
}