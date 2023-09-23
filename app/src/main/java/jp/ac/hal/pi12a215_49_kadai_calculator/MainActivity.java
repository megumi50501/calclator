package jp.ac.hal.pi12a215_49_kadai_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    //演算子フラグ
    boolean opFlg = false;
    //ひとつめの数値を記憶（退避用）
    int wkNum;
    //クリックした演算子を記憶
    String opCode;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 表示領域の変数を宣言
        // 表示領域のインスタンス化
        tv = (TextView) findViewById(R.id.outputArea);

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
            numBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //数値ボタンがクリックされた時の処理
                    //表示領域に数値が表示される
                    //自作表示メソッドを利用
                    displayNumber(v);
                }
            });
        }

        // クリアボタンのインスタンス化
        Button clearBtn = (Button) findViewById(R.id.clear);
        // クリアボタンの実装
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ディスプレイに`0`を表示する
                tv.setText("0");
            }
        });

        Button[] opSymbol = new Button[4];
        opSymbol[0] = findViewById(R.id.plus);
        opSymbol[1] = findViewById(R.id.minus);
        opSymbol[2] = findViewById(R.id.divided);
        opSymbol[3] = findViewById(R.id.multiplied);

        // 配列におさまった演算子にクリック時のリスナーを実装
        for(int i=0; i<opSymbol.length ; i++ ) {
            opSymbol[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //自作関数へ演算子クリック時の動作をまとめる
                    opClicked(v);
                    // もともとあったフラグをＯＮにする処理は、自作関数へお引越し
                    // するため、コメントアウトしました
                    // プラスボタンがクリックされたらすべきことは？
                    // 次に数値が入力されたら、画面に表示する準備
                    // 数値ボタンを押すアクションはすでに作成してあるはず・・・
                    // 演算ボタンがクリックされた・されていないの状態フラグを利用
                    opFlg = true;
                }
            });
        }

        //演算ボタンのインスタンス化
        Button plusOp = findViewById(R.id.plus);
        //ボタンクリック時のイベントを受け取るリスナー
        plusOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //プラスボタンがクリックされたらすべきことは？
                //次に数値が入力されたら、画面に表示する準備
                //数値ボタンを押すアクションはすでに作成してあるはず・・・
                //数値ボタンを押すアクションはすでに作成してあるはず・・・
            }
        });

    }

    private void displayNumber(View v){
        // ボタン型に型を変換しよう（キャスト）
        Button btn = (Button) v;
        // ボタンに表示されているラベル（数値）を取得
        String str = btn.getText().toString();
        // 取得した数字を数値に変換
        int dNum = Integer.parseInt(str);
        // 表示されている数値を取得
        String tvStr = tv.getText().toString();
        int tvNum = Integer.parseInt(tvStr);
        // 計算処理
        int num = tvNum * 10 + dNum;
        // ボタンの数値を画面に表示する
        tv.setText(String.valueOf(num));
    }

    //演算ボタンがクリックされた処理
    private void opClicked(View v){
        //演算子ボタンがクリックされたらすべきことは？
        //次に数値が入力されたら、画面に表示する準備
        //数値ボタンを押すアクションはすでに作成してあるはず・・・
        //演算ボタンがクリックされた・されていないの状態フラグを利用
        opFlg = true;
        //どの演算子ボタンがクリックされたかを記憶
        Button btn = (Button) v;
        opCode = btn.getText().toString();
    }

    private void calculate(){
        //➊ 一時保存で退避した１番目の数値と
        //❷ 表示されている2番目の数値の計算を実行
        String strNum = tv.getText().toString();
        int secondNum = Integer.parseInt(strNum);
        int answer = 0;
        //演算記号による演算処理の分岐
        //「＋」がクリックされている場合
        if(opCode.equals("+")){
            //➊ ＋ ❷
            answer = wkNum + secondNum;
        }
        //「－」がクリックされている場合
        else if (opCode.equals("－")) {
            //➊ － ❷
            answer = wkNum - secondNum;
        }
        //「×」がクリックされている場合
        else if (opCode.equals("×")) {
            //➊ × ❷
            answer = wkNum * secondNum;
        }
        //「÷」がクリックされている場合
        else if (opCode.equals("÷")) {
            //➊ × ❷
            answer = wkNum / secondNum;
        }
        //結果を表示する
        tv.setText(String.valueOf(answer));
    }
}
