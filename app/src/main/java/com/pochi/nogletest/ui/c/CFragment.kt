package com.pochi.nogletest.ui.c

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pochi.nogletest.BaseFragment
import com.pochi.nogletest.databinding.FragmentCBinding

class CFragment : BaseFragment() {

    override var actionbarVisibility = View.GONE
    override var bottomNavigationViewVisibility = View.VISIBLE

    private var _binding: FragmentCBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val cViewModel =
                ViewModelProvider(this).get(CViewModel::class.java)

        _binding = FragmentCBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textC
        cViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}