package fr.agesfarouches.abysslarp.screens.nfc

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NfcController {

    private val _lastTagId = MutableStateFlow<String?>(null)
    val lastTagId: StateFlow<String?> = _lastTagId

    private var adapter: NfcAdapter? = null

    /** À appeler une fois, dans MainActivity.onCreate */
    fun init(activity: Activity) {
        adapter = NfcAdapter.getDefaultAdapter(activity)
    }

    fun isNfcAvailable(): Boolean = adapter != null

    /** Démarre l'écoute NFC — à appeler quand l'écran NfcMenu devient visible. */
    fun startListening(activity: Activity) {
        val readerFlags = NfcAdapter.FLAG_READER_NFC_A or
                NfcAdapter.FLAG_READER_NFC_B or
                NfcAdapter.FLAG_READER_NFC_F or
                NfcAdapter.FLAG_READER_NFC_V or
                NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK

        adapter?.enableReaderMode(
            activity,
            { tag -> onTagDiscovered(tag) },
            readerFlags,
            null
        )
    }

    /** Arrête l'écoute — à appeler quand on quitte l'écran NfcMenu. */
    fun stopListening(activity: Activity) {
        adapter?.disableReaderMode(activity)
    }

    private fun onTagDiscovered(tag: Tag) {
        val hexId = tag.id.joinToString("") { "%02X".format(it) }
        _lastTagId.value = hexId
    }

    /** Test manuel pour l'émulateur (pas de vraie puce NFC disponible). */
    fun simulateScan(fakeId: String = "04A1B2C3D4") {
        _lastTagId.value = fakeId
    }

    fun clear() {
        _lastTagId.value = null
    }
}