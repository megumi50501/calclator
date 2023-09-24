package jp.ac.hal.pi12a215_49_kadai_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    // 演算子フラグ
    private boolean opFlg = false;

    // 演算子コード
    private String opCode = "";

    // 画面初期化フラグ
    private boolean resetRequired = false;

    // ひとつめの数値を記憶（退避用）
    private int firstNum;

    // (+/-)反転回数
    private int reverseCount = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 表示領域の変数を宣言
        // 表示領域のインスタンス化
        tv = findViewById(R.id.outputArea);

        Button[] numBtn = new Button[10];
        //ボタン部品(０～９)のインスタンス化
        numBtn[0] = findViewById(R.id.zero);
        numBtn[1] = findViewById(R.id.one);
        numBtn[2] = findViewById(R.id.two);
        numBtn[3] = findViewById(R.id.three);
        numBtn[4] = findViewById(R.id.four);
        numBtn[5] = findViewById(R.id.five);
        numBtn[6] = findViewById(R.id.six);
        numBtn[7] = findViewById(R.id.seven);
        numBtn[8] = findViewById(R.id.eight);
        numBtn[9] = findViewById(R.id.nine);

        //数値ボタン×１０にボタンクリックリスナを実装
        for (int i = 0; i < 10; i++) {
            //数値ボタンがクリックされた時の処理
            numBtn[i].setOnClickListener(this::displayNumber);
        }

        // クリアボタンのインスタンス化
        Button clearBtn = findViewById(R.id.clear);
        // クリアボタンの実装
        clearBtn.setOnClickListener((View v) -> {
            // 演算子フラグを初期化する
            opFlg = false;
            // 演算子コードを初期化する
            opCode = "";
            // 画面初期化フラグを初期化する
            resetRequired = false;
            // 反転回数を初期化する
            reverseCount = 0;
            // ディスプレイに`0`を表示する
            tv.setText(String.valueOf(0));
        });

        Button[] opSymbols = new Button[4];
        opSymbols[0] = findViewById(R.id.plus);
        opSymbols[1] = findViewById(R.id.minus);
        opSymbols[2] = findViewById(R.id.divided);
        opSymbols[3] = findViewById(R.id.multiplied);

        // 配列におさまった演算子にクリック時のリスナーを実装
        for (Button opSymbol : opSymbols) {
            // クリックされた演算子の処理
            opSymbol.setOnClickListener(this::opClicked);
        }

        // イコールボタンのインスタンス化
        Button equalOp = findViewById(R.id.equal);
        // イコールボタンクリック時のイベントを受け取るリスナー
        equalOp.setOnClickListener((View v) -> {
            // 計算結果を画面に表示する
            tv.setText(String.valueOf(calculate()));
            resetRequired = true;
            // 反転カウントを初期化する
            reverseCount = 0;
        });

        // ±ボタンのインスタンス化
        Button reverseOp = findViewById(R.id.plusminus);
        reverseOp.setOnClickListener((View v) -> {
            // 反転回数をカウントする
            reverseCount++;
            // 表示されている数値を取得
            String tvStr = tv.getText().toString();
            int displayedNum = Integer.parseInt(tvStr);
            if (reverseCount % 2 == 1) {
                tv.setText(String.valueOf(-displayedNum));
            } else {
                tv.setText(String.valueOf(displayedNum));
            }
        });

        // バックスペースボタンのインスタンス化
        Button backSpaceOp = findViewById(R.id.backspace);
        backSpaceOp.setOnClickListener((View v) -> {
            // 表示されている数値を取得
            String tvStr = tv.getText().toString();
            String editedNum = tvStr.substring(0, tvStr.length() - 1);
            if(editedNum.length() == 0){
                tv.setText(String.valueOf(0));
            } else {
                tv.setText(editedNum);
            }
        });
    }

    private void displayNumber(View v){
        // ボタン型に型を変換しよう（キャスト）
        Button btn = (Button) v;
        // ボタンに表示されているラベル（数値）を取得
        String str = btn.getText().toString();
        int pushedNum = Integer.parseInt(str);
        // 表示されている数値を取得
        String tvStr = tv.getText().toString();
        int displayedNum = Integer.parseInt(tvStr);

        // 演算子フラグが`true`または演算子フラグが`false`でかつ演算子コードが指定されている場合は、
        // 一つ目の数値を記憶し、二つ目の数値を画面に表示する
        // それ以外の場合は、選択された数値を画面に表示する
        if(opFlg && resetRequired){
            firstNum = displayedNum;
            tv.setText(String.valueOf(pushedNum));
            resetRequired = false;
        } else {
            // 選択された数値を画面に表示する
            tv.setText(String.valueOf(displayedNum * 10 + pushedNum));
        }
    }

    //演算ボタンがクリックされた処理
    private void opClicked(View v){
        //演算ボタンがクリックされた・されていないの状態フラグ
        opFlg = true;
        resetRequired = true;
        reverseCount = 0;
        //どの演算子ボタンがクリックされたかを記憶
        Button btn = (Button) v;

        opCode = btn.getText().toString();
    }

    private int calculate(){
        // 一時保存で退避した１番目の数値と表示されている2番目の数値の計算を実行
        String str = tv.getText().toString();
        int secondNum = Integer.parseInt(str);
        int answer = 0;

        switch(opCode) {
            //演算記号による演算処理の分岐
            //「＋」がクリックされている場合
            case "+":
                //➊ ＋ ❷
                answer = firstNum + secondNum;
                break;
            //「－」がクリックされている場合
            case "-":
                //➊ － ❷
                answer = firstNum - secondNum;
                break;

            //「×」がクリックされている場合
            case "×":
                //➊ × ❷
                answer = firstNum * secondNum;
                break;

            //「÷」がクリックされている場合
            case "÷":
                //➊ × ❷
                answer = firstNum / secondNum;
        }

        return answer;
    }
}
