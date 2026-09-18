package fr.agesfarouches.abysslarp.screens.NFC

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef

data class NfcReadResult(
    val tagId: String,
    val ndefText: String?
)

object NfcUtils {

    /** Récupère le tag lu depuis l'intent (NDEF_DISCOVERED ou TAG_DISCOVERED). */
    fun extractResult(intent: Intent): NfcReadResult? {
        val tag: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) ?: return null

        val tagId = tag.id.joinToString("") { "%02X".format(it) }

        val ndefText = try {
            Ndef.get(tag)?.use { ndef ->
                ndef.cachedNdefMessage?.let { parseNdefText(it) }
            }
        } catch (e: Exception) {
            null
        }

        return NfcReadResult(tagId = tagId, ndefText = ndefText)
    }

    private fun parseNdefText(message: NdefMessage): String? {
        val record = message.records.firstOrNull() ?: return null
        val payload = record.payload
        if (payload.isEmpty()) return null

        // Format NDEF Text Record : 1er octet = status byte, bits 0-5 = longueur du code langue
        val languageCodeLength = payload[0].toInt() and 0x3F
        return try {
            String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                Charsets.UTF_8
            )
        } catch (e: Exception) {
            null
        }
    }
}