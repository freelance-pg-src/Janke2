package jp.dagon.firstandroidbook.janke2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // インテントから追加情報を取り出す(name:取り出したい情報キー defaultValue:データーが取り出せなかった時のデフォルト値)
        val id = intent.getIntExtra("MY_HAND", 0)

        val myHand: Int

        myHand = when (id) {
            R.id.gu -> {
                // ImmageViewのコンテンツに画像リソースを設定(resId:画像のリソースID)
                myHandImage.setImageResource(R.drawable.gu)
                gu
            }
            R.id.choki -> {
                myHandImage.setImageResource(R.drawable.choki)
                choki
            }
            R.id.pa -> {
                myHandImage.setImageResource(R.drawable.pa)
                pa
            }
            else -> gu
        }

        // コンピュータの手を決める
        val comHand = getHnad()

        when (comHand) {
            gu -> comHandImage.setImageResource(R.drawable.com_gu)
            choki -> comHandImage.setImageResource(R.drawable.com_choki)
            pa -> comHandImage.setImageResource(R.drawable.com_pa)
        }

        // 勝敗を判定する
        val gameResult = (comHand - myHand + 3) % 3

        when (gameResult) {
            // 引き分け
            0 -> resultLabel.setText(R.string.result_draw)
            // 勝った場合
            1 -> resultLabel.setText(R.string.result_win)
            // 負けた場合
            2 -> resultLabel.setText(R.string.result_lose)
        }

        backButton.setOnClickListener {
            // 現在のアクティビティを終了し前のアクティビティに戻る
            finish()
        }

        // じゃんけんの結果を保存する
        saveData(myHand, comHand, gameResult)
    }

    /**
     * じゃんけんの結果を保存
     *
     */
    private fun saveData(myHand: Int, comHand: Int, gameResult: Int) {
        // デフォルトの共有プリファレンスを取得(context:デフォルトの共有プリファレンスを取得するオブジェクト)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 共有プリファレンスの設定項目をInt型で取得(key:取り出したい設定項目のキー defValue:設定項目が未設定の場合の値)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        val editor = pref.edit()
        // 共有プリファレンスの設定項目にInt型の値を設定(key:書き込みたい設定項目名 value:書き込む値)
        editor.putInt("GAME_COUNT", gameCount + 1)
            .putInt("WINNING_STREAK_COUNT",
                if (lastGameResult == 2 && gameResult == 2)
                    winningStreakCount + 1
                else
                    0
            )
            .putInt("LAST_MY_HAND", myHand)
            .putInt("LAST_COM_HAND", comHand)
            .putInt("BEFORE_LAST_COM_HAND", lastComHand)
            .putInt("GAME_RESULT", gameResult)
            .apply()
    }

    /**
     * じゃんけんの手を決める
     */
    private fun getHnad(): Int {
        var hannd = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastMyHand = pref.getInt("LAST_MY_HAND", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val beforeLastComHnad = pref.getInt("BEFORE_LAST_COM_HAND", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)

        if (gameCount == 1) {
            if (gameResult == 2) {
                // 前回の勝負は1回目で、コンピュータが勝った場合、コンピュータは次に出す手を変える
                while (lastComHand == hannd) {
                    hannd = (Math.random() * 3).toInt()
                }
            } else if (gameResult == 1) {
                // 前回の勝負が1回目で、コンピュータが負けた場合相手の出した手に勝つ手を出す
                hannd = (lastMyHand - 1 + 3) % 3
            }
        } else if (winningStreakCount > 0) {
            if (beforeLastComHnad == lastComHand) {
                // 同じ手で連勝した場合は手を変える
                while (lastComHand == hannd) {
                    hannd = (Math.random() * 3).toInt()
                }
            }
        }

        return hannd
    }
}
