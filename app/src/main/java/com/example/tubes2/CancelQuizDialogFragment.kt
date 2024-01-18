package com.example.tubes2

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CancelQuizDialogFragment : DialogFragment() {

    interface CancelQuizListener {
        fun onCancelConfirmed()
    }

    private var listener: CancelQuizListener? = null

    fun setCancelQuizListener(listener: CancelQuizListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Cancel Quiz")
            .setMessage("Are you sure to cancel this quiz? You won't be able to continue.")
            .setPositiveButton("Yes") { _, _ ->
                listener?.onCancelConfirmed()
            }
            .setNegativeButton("No", null)
            .create()
    }
}