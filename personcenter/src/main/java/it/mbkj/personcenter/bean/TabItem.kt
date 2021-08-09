package it.mbkj.personcenter.bean

import com.flyco.tablayout.listener.CustomTabEntity

class TabItem : CustomTabEntity {
    var title: String
    var selectedIcon = 0
    var unSelectedIcon = 0

    constructor(title: String, unSelectedIcon: Int, selectedIcon: Int) {
        this.title = title
        this.unSelectedIcon = unSelectedIcon
        this.selectedIcon = selectedIcon
    }

    constructor(title: String) {
        this.title = title
    }

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}