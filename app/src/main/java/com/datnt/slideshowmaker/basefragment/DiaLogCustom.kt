package com.datnt.slideshowmaker.basefragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

abstract class DiaLogCustom : DiaLog() {
    private var ONE : Int = 0
    override fun createDialog(dialogBuilder: AlertDialog.Builder) {
        dialogBuilder.setView(createCustomView())
    }

    abstract fun createCustomView(): View

    /**
     * Set custom animation style for dialog windows
     */
    open fun onCreateCustomAnimation(): Int = ONE

    override fun put(param: DiaLog.Params): DiaLog {
        (param as Params).let {
            dialogLayout = it.layoutType
            dialogPosition = it.dialogPosition
        }
        return super.put(param)
    }

    override fun onStart() {
        super.onStart()
        if (getDialogPosition() == DialogPosition.BOTTOM) {
            dialog?.window?.setGravity(Gravity.BOTTOM)
        }
        layoutSetting()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = onCreateCustomAnimation()
    }

    private fun layoutSetting() {
        if (getLayoutType() == LayoutType.ROUNDED_CORNER) {
            dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } else if (getLayoutType() != LayoutType.NONE_LAYOUT) {
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

    }

    private var dialogPosition: DialogPosition? = DialogPosition.DEFAULT

    private var dialogLayout: LayoutType? = LayoutType.DEFAULT

    fun getDialogPosition() = dialogPosition

    fun getLayoutType() = dialogLayout

    enum class DialogPosition {
        DEFAULT, BOTTOM
    }

    enum class LayoutType {
        DEFAULT, ROUNDED_CORNER, NONE_LAYOUT
    }

    open class Params(var dialogPosition: DialogPosition? = DialogPosition.DEFAULT,
                      var layoutType: LayoutType? = LayoutType.DEFAULT) : DiaLog.Params()
}