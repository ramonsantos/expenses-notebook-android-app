package io.github.ramonsantos.expensesnotebook.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.ramonsantos.expensesnotebook.R
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_send_to_email), Context.MODE_PRIVATE
        )

        val emailToSend = sharedPref.getString(getString(R.string.preference_send_to_email_key), "")

        send_email_to_edit_text.setText(emailToSend)
    }

    fun saveSettings(view: View) {
        val email = send_email_to_edit_text.text.toString()

        if (isValidEmail(email)) {
            val editor: Editor = sharedPref.edit()
            editor.putString(getString(R.string.preference_send_to_email_key), email)
            editor.commit()

            Toast.makeText(this, getString(R.string.message_settings_updated), Toast.LENGTH_SHORT)
                .show()
            this.finish()
        } else {
            Toast.makeText(
                this,
                getString(R.string.message_email_format_invalid),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
