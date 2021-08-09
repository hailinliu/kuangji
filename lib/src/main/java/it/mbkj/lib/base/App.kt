package it.mbkj.lib.base

import android.app.Application

object App {

    public  var INSTANCE: Application?=null
    init {
        var app: Application? = null
        try {
            app =
                Class.forName("android.app.AppGlobals").getMethod("getInitialApplication")
                    .invoke(null as Any?) as Application
            checkNotNull(app) { "Static initialization of Applications must be on main thread." }
        } catch (var8: Exception) {
            println("Failed to get current application from AppGlobals." + var8.message)
            try {
                app =
                    Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                        .invoke(null as Any?) as Application
            } catch (var7: Exception) {
                println("Failed to get current application from ActivityThread." + var8.message)
            }
        } finally {
            INSTANCE  = app
        }
    }
}