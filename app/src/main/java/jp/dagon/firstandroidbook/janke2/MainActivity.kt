package jp.dagon.firstandroidbook.janke2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gu.setOnClickListener { onJankenButtonTapped(it) }
        choki.setOnClickListener { onJankenButtonTapped(it) }
        pa.setOnClickListener { onJankenButtonTapped(it) }

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        // 共有プリファレンスをクリア
        editor.clear().apply()
    }

    /**
     * ボタンを押した時に実行する
     */
    fun onJankenButtonTapped(view: View?) {
        // Intentクラスのコンストラクタ(context:呼び出し元のインスタンス class:呼び出したいアクティビティのクラス)
        val intent = Intent(this, ResultActivity::class.java)
        // インテントにデータを格納(name:追加したい情報キー value:追加する値)
        intent.putExtra("MY_HAND", view?.id)
        // アクティビティを起動する
        startActivity(intent)
    }
}
