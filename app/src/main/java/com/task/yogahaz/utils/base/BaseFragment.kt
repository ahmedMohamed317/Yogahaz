package com.task.yogahaz.utils.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.task.yogahaz.utils.DialogUtil


abstract class BaseFragment<VDB : ViewBinding> : Fragment() {
    private var _binding: VDB? = null
    protected val binding get() = _binding!!
    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return _binding!!.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VDB


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        handleBars()
        onCreateInit()
        initClicks()
    }

    //adding abstract fun for clicking events
    protected abstract fun initClicks()
    //adding abstract fun for init at fragment creation
    protected abstract fun onCreateInit()


    // checking current destination and return true if the current destination is the same as parameter so i can navigate after
    fun checkCurrentDestination(currentFragment: Int): Boolean {
        return findNavController().currentDestination?.id == currentFragment
    }

    // showing toast for error messages
    fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    //dismissing snackBars
    override fun onPause() {
        super.onPause()
        if (snackBar != null) {
            snackBar?.dismiss()
        }
    }

//    //showing  status bar and navigation bar
//    private fun handleBars() {
//        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
//
//        val window = requireActivity().window
//        val decorView = window.decorView
//
//        WindowInsetsControllerCompat(window, decorView).apply {
//            // Show the status bar
//            show(WindowInsetsCompat.Type.statusBars())
//            // Show the navigation bar
//            show(WindowInsetsCompat.Type.navigationBars())
//        }
//    }
//    protected fun handleStatusBars(showStatusBar: Boolean = true) {
//        val window = requireActivity().window
//        val decorView = window.decorView
//
//        WindowInsetsControllerCompat(window, decorView).apply {
//            if (showStatusBar) {
//                show(WindowInsetsCompat.Type.statusBars())
//            } else {
//                hide(WindowInsetsCompat.Type.statusBars())
//            }
//
//            show(WindowInsetsCompat.Type.navigationBars())
//
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showProgressBar() {
        DialogUtil.showDialog(requireContext())
    }
    fun dismissProgressBar() {
        DialogUtil.dismissDialog()
    }

}