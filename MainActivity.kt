package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // 介面元件
    private lateinit var edName: EditText
    private lateinit var tvText: TextView
    private lateinit var tvName: TextView
    private lateinit var tvWinner: TextView
    private lateinit var tvMyMora: TextView
    private lateinit var tvComputerMora: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnMora: Button

    // 使用常數統一管理出拳選項
    private val scissors = "剪刀"
    private val rock = "石頭"
    private val paper = "布"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
    }

    // 1. 綁定 XML 介面元件
    private fun initViews() {
        edName = findViewById(R.id.ed_name)
        tvText = findViewById(R.id.tv_text)
        tvName = findViewById(R.id.tv_name)
        tvWinner = findViewById(R.id.tv_winner)
        tvMyMora = findViewById(R.id.tv_mmora)
        tvComputerMora = findViewById(R.id.tv_comra)
        radioGroup = findViewById(R.id.radioGroup)
        btnMora = findViewById(R.id.btn_mora)
    }

    // 2. 設定按鈕事件
    private fun initListeners() {
        btnMora.setOnClickListener {
            playGame()
        }
    }

    // 3. 執行一局猜拳遊戲
    private fun playGame() {
        val playerName = edName.text.toString().trim()

        // 檢查姓名是否為空白
        if (playerName.isEmpty()) {
            tvText.text = "請輸入玩家姓名"
            edName.requestFocus()
            return
        }

        // 取得玩家選擇的拳法
        val playerMora = getPlayerMora()

        // 電腦隨機出拳
        val computerMora = getComputerMora()

        // 顯示玩家姓名與雙方出拳
        tvName.text = "姓名\n$playerName"
        tvMyMora.text = "我方出拳\n$playerMora"
        tvComputerMora.text = "電腦出拳\n$computerMora"

        // 判斷勝負並更新畫面
        showResult(
            playerName = playerName,
            playerMora = playerMora,
            computerMora = computerMora
        )
    }

    // 4. 取得玩家選擇的拳法
    private fun getPlayerMora(): String {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.btn_scissor -> scissors
            R.id.btn_stone -> rock
            R.id.btn_paper -> paper
            else -> scissors
        }
    }

    // 5. 產生電腦的隨機拳法
    private fun getComputerMora(): String {
        return when (Random.nextInt(3)) {
            0 -> scissors
            1 -> rock
            else -> paper
        }
    }

    // 6. 判斷勝負並顯示結果
    private fun showResult(
        playerName: String,
        playerMora: String,
        computerMora: String
    ) {
        val result = when {
            playerMora == computerMora -> {
                "平手" to "平手，請再試一次"
            }

            (playerMora == scissors && computerMora == paper) ||
            (playerMora == rock && computerMora == scissors) ||
            (playerMora == paper && computerMora == rock) -> {
                playerName to "恭喜你獲勝了!!"
            }

            else -> {
                "電腦" to "可惜，電腦獲勝了"
            }
        }

        tvWinner.text = "勝利者\n${result.first}"
        tvText.text = result.second
    }
}
