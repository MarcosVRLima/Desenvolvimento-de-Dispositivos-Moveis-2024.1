package br.ufc.cronometro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var timerText: TextView
    private lateinit var timeInput: EditText
    private lateinit var startButton: Button
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layout programático
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(16, 16, 16, 16)
        }

        // Campo de entrada para o tempo
        timeInput = EditText(this).apply {
            hint = "Tempo em segundos"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        layout.addView(timeInput)

        // Botão de iniciar
        startButton = Button(this).apply {
            text = "Iniciar"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        layout.addView(startButton)

        // Texto do cronômetro
        timerText = TextView(this).apply {
            text = "00:00"
            textSize = 48f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        layout.addView(timerText)

        // Configurar o layout como a view principal
        setContentView(layout)

        // Configurar o clique do botão
        startButton.setOnClickListener {
            val timeInSeconds = timeInput.text.toString().toLongOrNull()
            if (timeInSeconds != null) {
                startTimer(timeInSeconds)
            } else {
                showToast("Por favor, insira um número válido")
            }
        }
    }

    private fun startTimer(timeInSeconds: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeInSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerText.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerText.text = "Tempo Esgotado!"
                showToast("Tempo Esgotado!")

                // Inicia a tela de alarme
                val intent = Intent(this@MainActivity, AlarmeActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

// Nova Activity para a tela de alarme
class AlarmeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val alarmText = TextView(this).apply {
            text = "Alarme! Tempo Esgotado!"
            textSize = 48f
            gravity = Gravity.CENTER
        }

        layout.addView(alarmText)
        setContentView(layout)
    }
}