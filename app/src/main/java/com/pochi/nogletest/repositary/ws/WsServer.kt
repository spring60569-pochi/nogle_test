package com.pochi.nogletest.repositary.ws

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WsServer {
    interface OnPriceChangedListener {
        fun onPriceChanged(priceStr: String)
    }

    private val tag = WsServer::class.java.name
    private val URL = "wss://ws.btse.com/ws/futures"

    private lateinit var webSocketListener: WebSocketListener
    private val okHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var onPriceChangedListener: OnPriceChangedListener? = null

    private lateinit var request: Request

    init {
        request = Request.Builder()
            .url(URL)
            .build()
    }

    fun getPrice(onPriceChangedListener: OnPriceChangedListener) {
        this.onPriceChangedListener = onPriceChangedListener
        webSocketListener = WebSocketListener(this)
        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
        webSocket?.send("{\"op\": \"subscribe\", \"args\": [\"coinIndex\"]}")
    }

    fun notifyPriceChanged(priceStr: String) {
        onPriceChangedListener?.onPriceChanged(priceStr)
    }

    fun shutdown() {
        okHttpClient.dispatcher.executorService.shutdown()
    }

    fun close() {
        webSocket?.close(1000, "Canceled")
    }
}