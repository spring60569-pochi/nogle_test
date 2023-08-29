package com.pochi.nogletest.ui.a

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pochi.nogletest.BaseFragment
import com.pochi.nogletest.R
import com.pochi.nogletest.databinding.FragmentABinding
import com.pochi.nogletest.repositary.restful.Server
import com.pochi.nogletest.repositary.ws.WsServer
import com.pochi.nogletest.repositary.ws.WsServer.OnPriceChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AFragment : BaseFragment() {
    private val TAG = "AFragment"

    override var actionbarVisibility = View.GONE
    override var bottomNavigationViewVisibility = View.VISIBLE

    private var _binding: FragmentABinding? = null
    private lateinit var aViewModel: AViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val wsServer: WsServer = WsServer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aViewModel =
            ViewModelProvider(this).get(AViewModel::class.java)

        _binding = FragmentABinding.inflate(inflater, container, false)
        val root: View = binding.root

        setListener()

        getDataFromServer()

        return root
    }

    private fun setListener() {
        // spot/futures tabs
        binding.tabSpot.setOnClickListener {
            binding.tabSpot.setBackgroundColor(resources.getColor(R.color.tab_on_background))
            binding.tabFuture.setBackgroundColor(resources.getColor(R.color.tab_off_background))

            binding.spotList.visibility = View.VISIBLE
            binding.futureList.visibility = View.INVISIBLE
        }

        binding.tabFuture.setOnClickListener {
            binding.tabSpot.setBackgroundColor(resources.getColor(R.color.tab_off_background))
            binding.tabFuture.setBackgroundColor(resources.getColor(R.color.tab_on_background))

            binding.spotList.visibility = View.INVISIBLE
            binding.futureList.visibility = View.VISIBLE
        }

        aViewModel.getSpotData().observe(viewLifecycleOwner) {
            // limit notify range
            binding.spotList.layoutManager?.run {
                val first = (this as LinearLayoutManager).findFirstVisibleItemPosition()
                val last = this.findLastVisibleItemPosition()
                binding.spotList.adapter?.notifyItemRangeChanged(first, last)
            }
        }

        aViewModel.getFutureData().observe(viewLifecycleOwner) {
            // limit notify range
            binding.futureList.layoutManager?.run {
                val first = (this as LinearLayoutManager).findFirstVisibleItemPosition()
                val last = this.findLastVisibleItemPosition()
                binding.futureList.adapter?.notifyItemRangeChanged(first, last)
            }
        }

    }

    private fun getDataFromServer() {
        CoroutineScope(Dispatchers.Main).launch {
            val data = Server.getMarket()
            // sorted by name
            val sortedData = data.sortedWith(compareBy { it["symbol"] as String })
            aViewModel.setData(sortedData)
            Log.d(tag, "SpotData -> ${aViewModel.getSpotData().value!!.size}")
            Log.d(tag, "FutureData -> ${aViewModel.getFutureData().value!!.size}")

            binding.spotList.apply {
                (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                adapter = DataRecyclerAdapter(aViewModel, "Spot")
                layoutManager = LinearLayoutManager(context)
            }

            binding.futureList.apply {
                (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                adapter = DataRecyclerAdapter(aViewModel, "Future")
                layoutManager = LinearLayoutManager(context)
            }

            //
            getPriceFromWebSocketServer()
        }
    }

    private fun getPriceFromWebSocketServer() {
        wsServer.getPrice(object : OnPriceChangedListener {
            override fun onPriceChanged(priceStr: String) {
                this@AFragment.activity?.runOnUiThread { aViewModel.setPrice(priceStr) }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        wsServer.shutdown()
    }

    override fun onDestroy() {
        super.onDestroy()
        wsServer.close()
    }
}