package com.datnt.slideshowmaker.basefragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class DiaLog : DialogFragment() {
    companion object {
        const val CANCELABLE_KEY = "CANCELABLE_KEY"
        const val TITLE_KEY: String = "TITLE_KEY"
        const val MESSAGE_KEY: String = "MESSAGE_KEY"
        const val ICON_KEY: String = "ICON_KEY"
        const val POSITIVE_KEY: String = "POSITIVE_KEY"
        const val NEGATIVE_KEY: String = "NEGATIVE_KEY"
        const val DELAY_DIALOG_UPDATE = 500L
    }

    protected var bundle = Bundle()
    private lateinit var dialogBuilder: AlertDialog.Builder
    var callback: () -> Unit = {}
    var messageError: String? = null
    var isDialogUpdate = false
    var dialogBackPress: () -> Unit = {}
    var dialogDestroy : (() -> Unit)? = null
    var onDialogShowSuccess: ((isShow: Boolean) -> Unit)? = null
    abstract fun shows(fm: FragmentManager): DiaLog

    abstract fun createDialog(dialogBuilder: AlertDialog.Builder)

    /**
     * Start referencing bundle and put data in it.
     * If dialog need data, override this
     */
    open fun put(param: DiaLog.Params): DiaLog {
        arguments = bundle
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBuilder = AlertDialog.Builder(requireActivity()).setOnKeyListener { arg0, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK){
                dialogBackPress?.invoke()
            }
            false
        }

        createDialog(dialogBuilder)
        return dialogBuilder.create()
    }


    fun dismissDialog(){
        dismiss()
    }

    override fun onDestroy() {
        dialogDestroy?.invoke()
        super.onDestroy()
    }

    abstract class Params
}