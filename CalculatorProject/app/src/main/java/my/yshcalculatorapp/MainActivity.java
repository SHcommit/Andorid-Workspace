package my.yshcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView process; // 계산 과정 피연산자 1 + 피연산자 2. ( 연산자와 피연산자 모두 보이는 textView)
    TextView result; //결과 텍스트
    //First Row
    Button btnAC; //OK
    Button btnPM; //OK
    Button btnPer; //ok

    // Right Col _ 노란색
    Button btnDiv; //ok
    Button btnMul; //ok
    Button btnMin;  //ok
    Button btnPlus; //ok
    Button btnAns; // ok
    Button btnDot; //ok

    Boolean flag; // ans ( = 연산자 버튼을 눌렀는가?)
    Double n1_D; //결과 계산하기 위한 변수 (피연산자 1)
    Double n2_D; //결과 계산하기 위한 변수 (피연산자 2)
    // + - / * %
    enum Operators {plus,min,div,mul,per} //자주 사용될 연산자를 열거형으로 정리했습니다.

    Operators operateState; // 피연산자 1 과 2 간 덧셈인지 곱셈인지 등 연산자를 이곳에 저장합니다.
    String operate = ""; //중간 과정이 저장될 Stirng
    //피연산자1
    String n1 = ""; //첫번째 피연산자
    //피연산자2
    String n2 = ""; //두번째 피연산자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 각각의 연산자에 대한 헨들러값을 얻어옵니다.
        result = findViewById(R.id.Text_Result);
        flag = false;
        btnAC = (Button)findViewById(R.id.Btn_AC);
        btnPM = (Button)findViewById(R.id.Btn_PM);
        btnPer = (Button)findViewById(R.id.Btn_Per);

        btnDiv = (Button)findViewById(R.id.Btn_div);
        btnMul = (Button)findViewById(R.id.Btn_Mul);
        btnMin = (Button)findViewById(R.id.Btn_Min);
        btnPlus = (Button)findViewById(R.id.Btn_Plus);
        btnAns = (Button)findViewById(R.id.Btn_ans);
        btnDot = (Button)findViewById(R.id.Btn_dot);
        process = findViewById(R.id.Text_process);
        //초기 결과, 연산과정 ""로 초기화.
        process.setText("");
        result.setText("");
        operate = n1 = n2 = "" ;

        //AC 누르면 결과, 연산과정등 전부 초기화합니다.
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input.setText("");
                result.setText("");
                operate = n1 = n2 = "" ;
                process.setText("");
                flag = false;
            }
        });
        //버튼.setOnClickListener를 클래스로 구현한 myListener를 추가합니다.
        btnPlus.setOnClickListener(myListener);
        btnDiv.setOnClickListener(myListener);
        btnMul.setOnClickListener(myListener);
        btnMin.setOnClickListener(myListener);
        btnAns.setOnClickListener(myListener);
        btnPer.setOnClickListener(myListener);


        //이부분도 한번 ans 결과 = 연산자를 사용할 경우 계속 에러가 떴는데, 그 이유는 결과값이 double이기때문에
        // 한번의 = 연산자 수행 이후 부터는 double 처리만 하면 된다.
        btnPM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //만약 실수 - 정수 ==  0이면 정수고, 0이 아닐 경우에는 실수다.
                if (flag == false) {
                    if (((Double.parseDouble(result.getText().toString())) - ((int) Double.parseDouble(result.getText().toString()))) == 0.0) {
                        result.setText("" + (Integer.parseInt(result.getText().toString()) * -1));
                    } else {
                        result.setText("" + (Double.parseDouble(result.getText().toString()) * -1));
                    }
                }else{
                    result.setText("" + (Double.parseDouble(result.getText().toString()) * -1));
                }
            }

        });
    }

    /**
     * 오류가 났던 이유
     * 계속 text한 곳에 operate 를 통해 진행과정도 출력할려고
     * 저장했는데 나중에 ans 때 result에 숫자 이외에 연산자까지 저장되서 오류 발생.
     */
    Button.OnClickListener myListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(process.getText().toString() == null)
            {
                Toast.makeText(MainActivity.this,"숫자를 입력하셔야해요..",Toast.LENGTH_SHORT).show();
            }
            // + - / * %
           // Toast.makeText(MainActivity.this,"사칙연산 버튼 호출 확인",Toast.LENGTH_SHORT).show();
            /**
             * 경우에따라서 특정 연산자일 경우,
             * 우선 맨 아레 onClick()를 통해  각각의 번호 num0~9까지 버튼이 눌릴 경우
             * result에 저장했었는데 그 저장한 숫자를 n1로 받아오다가,
             * 특정한 연산자 ex) + - / % +/- 를 눌렀을 경우
             * n1에 이전까지 num0~9 버튼으로 입력되었던 문자열들(숫자) 를 n1에 저장하고,
             * 그 특정 연산자는 operate에 저장해서, 중간 과정 을 보여줍니다 ex) 피연산자1  + or / or % ...
             * 또한 추후 ans( = 버튼)를 누를 경우 이전 연산자가 무엇이었는지 파악하기 위해 operateState 변수를 선언하고
             * 그곳에 저장했습니다.
             */
            switch(view.getId()){
                case R.id.Btn_Plus:
                    n1 = result.getText().toString();
                    operate = result.getText().toString() + " + " ;
                    process.setText(operate);
                    result.setText("");
                    operateState = Operators.plus;
                    break;
                case R.id.Btn_Mul:
                    n1 = result.getText().toString();
                    operate = result.getText().toString()+ " x " ;
                    process.setText(operate);
                    result.setText("");
                    operateState = Operators.mul;
                    break;
                case R.id.Btn_Min:
                    n1 = result.getText().toString();
                    operate =result.getText().toString() + " - ";
                    process.setText(operate);
                    result.setText("");
                    operateState=Operators.min;
                    break;
                case R.id.Btn_div:
                    n1 = result.getText().toString();
                    operate = result.getText().toString() + " / ";
                    process.setText(operate);
                    result.setText("");
                    operateState = Operators.div;
                    break;
                case R.id.Btn_Per:
                    n1 = result.getText().toString();
                    operate = result.getText().toString() + " % ";
                    process.setText(operate);
                    result.setText("");
                    operateState = Operators.per;
                    break;
                case R.id.Btn_ans:
                    double answer = 0.0;
                    //Toast.makeText(MainActivity.this,"연산 결과",Toast.LENGTH_SHORT).show();
                    n2 = result.getText().toString();
                    operate += n2;
                    process.setText(operate);
                    n1_D = Double.parseDouble(n1);
                    n2_D = Double.parseDouble(n2);

                    switch(operateState){
                        case div:
                            answer = n1_D / n2_D ;
                            break;
                        case per:
                            answer = n1_D % n2_D ;
                            break;
                        case min:
                            answer = n1_D - n2_D ;
                            break;
                        case mul:
                            answer = n1_D * n2_D ;
                            break;
                        case plus:
                            answer = n1_D + n2_D ;
                            break;
                    }
                    flag = true;
                    result.setText("" + answer);
                    n1 = result.getText().toString() ;
                    break;
            }

        }
    };
    //이 함수 각각 버튼 .xml에 추가하니까 된다.
    public void onClick(View v){
        //Toast.makeText(MainActivity.this,"버튼 이벤트 헨들러\n 잘 실행 됬다.",Toast.LENGTH_SHORT).show();
        switch(v.getId()){
            case R.id.Btn_dot:
                result.setText(result.getText().toString() + ".");
                break;
            case R.id.Num_0:
                result.setText(result.getText().toString() + 0);
                break;
            case R.id.Num_1:
                result.setText(result.getText().toString() + 1);
                break;
            case R.id.Num_2:
                result.setText(result.getText().toString() + 2);
                break;
            case R.id.Num_3:
                result.setText(result.getText().toString() + 3);
                break;
            case R.id.Num_4:
                result.setText(result.getText().toString() + 4);
                break;
            case R.id.Num_5:
                result.setText(result.getText().toString() + 5);
                break;
            case R.id.Num_6:
                result.setText(result.getText().toString() + 6);
                break;
            case R.id.Num_7:
                result.setText(result.getText().toString() + 7);
                break;
            case R.id.Num_8:
                result.setText(result.getText().toString() + 8);
                break;
            case R.id.Num_9:
                result.setText(result.getText().toString() + 9);
                break;

        }
    }
}