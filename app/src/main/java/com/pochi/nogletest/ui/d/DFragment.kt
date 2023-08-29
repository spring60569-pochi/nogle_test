package com.pochi.nogletest.ui.d

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.pochi.nogletest.BaseFragment
import com.pochi.nogletest.R
import com.pochi.nogletest.databinding.FragmentDBinding

class DFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.VISIBLE

    private var _binding: FragmentDBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dViewModel =
            ViewModelProvider(this).get(DViewModel::class.java)

        _binding = FragmentDBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textD
        dViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val settingIcon: ImageView = binding.settingIcon
        settingIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.icon_settings))
        settingIcon.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_d_to_navigation_settings)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}