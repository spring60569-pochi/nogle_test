package com.pochi.nogletest.ui.b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pochi.nogletest.BaseFragment
import com.pochi.nogletest.databinding.FragmentBBinding

class BFragment : BaseFragment() {

    override var actionbarVisibility = View.GONE
    override var bottomNavigationViewVisibility = View.VISIBLE

    private var _binding: FragmentBBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bViewModel =
            ViewModelProvider(this).get(BViewModel::class.java)

        _binding = FragmentBBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textB
        bViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}