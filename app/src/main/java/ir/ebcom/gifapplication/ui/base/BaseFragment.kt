package ir.ebcom.gifapplication.ui.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ir.ebcom.gifapplication.data.LocalException
import ir.ebcom.gifapplication.util.getErrorMessage

open class BaseFragment: Fragment() {
    fun showErrorMessage(view: View?=null,localException: LocalException?){
        view?.let {
            showSnackBarError(getErrorMessage(localException),it)
        } ?:kotlin.run {
            showToastError(getErrorMessage(localException))
        }
    }

    private fun showToastError(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    private fun showSnackBarError(message: String, view: View) {
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
    }

    fun showKeyboard(view: View?){
        view?.let {
            val imm=requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(view,0)
        }
    }
    fun hideKeyboard(view: View?) {
        view?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}