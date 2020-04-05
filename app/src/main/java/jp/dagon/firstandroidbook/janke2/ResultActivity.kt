package jp.dagon.firstandroidbook.janke2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // インテントから追加情報を取り出す(name:取り出したい情報キー defaultValue:データーが取り出せなかった時のデフォルト値)
        val id = intent.getIntExtra("My_HAND", 0)

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
        val comHand = (Math.random() * 3).toInt()

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
    }
}
