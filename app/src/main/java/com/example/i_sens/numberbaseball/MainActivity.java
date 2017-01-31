package com.example.i_sens.numberbaseball;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends ActionBarActivity {

    static int ItemResult;
    static int trynumber;
    class BaseBallScore{
        int strike;
        int ball;
        int out;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker np1 = (NumberPicker) findViewById(R.id.numberPicker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.numberPicker3);

        np1.setMaxValue(9);
        np1.setMinValue(0);
        np1.setWrapSelectorWheel(true);
        np2.setMaxValue(9);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(true);
        np3.setMaxValue(9);
        np3.setMinValue(0);
        np3.setWrapSelectorWheel(true);

        CreateNumber();

       // editID.setText("Hello edit");
       // editID.setTextColor(0xFFFF0000);
       // editID.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        Intent intent = new Intent(this, FirstLoading.class);
        startActivity(intent);

        //View.OnClickListener listener = new View.OnClickListener()
        //{
        //   @Override
        //    public void onClick(View v)
        //    {

        //    }
        //};
        //Button btnLogin =(Button) findViewById(R.id.btn_login);
        //btnLogin.setOnClickListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void CreateNumber(){
        Random rnd = new Random();
        int tempnumber= rnd.nextInt(1000);
        int result1digit=tempnumber%10;
        int result10digit=(tempnumber/10)%10;
        int result100digit=(tempnumber/100)%10;

        this.trynumber=0;
        if(result1digit==result10digit){
            result10digit++;
            result10digit=result10digit%10;
        }
        if(result1digit==result100digit){
            result100digit++;
            result100digit=result100digit%10;
        }
        if(result10digit==result100digit){
            result100digit++;
            result100digit=result100digit%10;
        }
        this.ItemResult=result1digit+result10digit*10+result100digit*100;
        TextView texttry = (TextView) findViewById(R.id.txt_try);
        texttry.setText(String.valueOf(this.trynumber));
        ImageView imagestrike1 = (ImageView) findViewById(R.id.img_strike1);
        ImageView imagestrike2 = (ImageView) findViewById(R.id.img_strike2);
        ImageView imagestrike3 = (ImageView) findViewById(R.id.img_strike3);
        ImageView imageball1 = (ImageView) findViewById(R.id.img_ball1);
        ImageView imageball2 = (ImageView) findViewById(R.id.img_ball2);
        ImageView imageball3 = (ImageView) findViewById(R.id.img_ball3);
        ImageView imageout = (ImageView) findViewById(R.id.img_out);

        imagestrike1.setVisibility(View.INVISIBLE);
        imagestrike2.setVisibility(View.INVISIBLE);
        imagestrike3.setVisibility(View.INVISIBLE);
        imageball1.setVisibility(View.INVISIBLE);
        imageball2.setVisibility(View.INVISIBLE);
        imageball3.setVisibility(View.INVISIBLE);
        imageout.setVisibility(View.INVISIBLE);

        NumberPicker np1 = (NumberPicker) findViewById(R.id.numberPicker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.numberPicker3);
        np1.setValue(0);
        np2.setValue(0);
        np3.setValue(0);
    }

    public BaseBallScore scoreUpdate(int number, int goalnumber){
        BaseBallScore score= new BaseBallScore();
        int goalnumberdigit[]={goalnumber%10,(goalnumber/10)%10,(goalnumber/100)%10};
        int numberdigit[]={number%10,(number/10)%10,(number/100)%10};
        score.strike = 0;
        score.ball=0;
        score.out=0;

        for(int numbercount=0;numbercount<3;numbercount++){
            for(int goalcount=0;goalcount<3;goalcount++){
                if(numberdigit[numbercount]==goalnumberdigit[goalcount]){
                    if(numbercount==goalcount)
                        score.strike++;
                    else
                        score.ball++;
                    break;
                }
            }
        }
        if((score.strike==0)&&(score.ball==0))
            score.out=1;

        if(goalnumber==number) {
            score.strike = 3;
            score.ball=0;
            score.out=0;
        }
        return score;
    }

    public void onButtonClick(View v) {
        TextView texttry = (TextView) findViewById(R.id.txt_try);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.numberPicker1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.numberPicker3);
        ImageView imagestrike1 = (ImageView) findViewById(R.id.img_strike1);
        ImageView imagestrike2 = (ImageView) findViewById(R.id.img_strike2);
        ImageView imagestrike3 = (ImageView) findViewById(R.id.img_strike3);
        ImageView imageball1 = (ImageView) findViewById(R.id.img_ball1);
        ImageView imageball2 = (ImageView) findViewById(R.id.img_ball2);
        ImageView imageball3 = (ImageView) findViewById(R.id.img_ball3);
        ImageView imageout = (ImageView) findViewById(R.id.img_out);
        BaseBallScore score;

        int getNp = np1.getValue()*100;
        getNp = getNp+np2.getValue()*10;
        getNp = getNp+np3.getValue();

        int CurrentNumber;

        CurrentNumber = getNp;
        score = scoreUpdate(CurrentNumber, this.ItemResult);
        this.trynumber++;
        texttry.setText(String.valueOf(this.trynumber));
        if (score.strike == 1) {
            imagestrike1.setVisibility(View.VISIBLE);
            imagestrike2.setVisibility(View.INVISIBLE);
            imagestrike3.setVisibility(View.INVISIBLE);
        } else if (score.strike == 2) {
            imagestrike1.setVisibility(View.VISIBLE);
            imagestrike2.setVisibility(View.VISIBLE);
            imagestrike3.setVisibility(View.INVISIBLE);
        } else if (score.strike == 3) {
            imagestrike1.setVisibility(View.VISIBLE);
            imagestrike2.setVisibility(View.VISIBLE);
            imagestrike3.setVisibility(View.VISIBLE);
            String attempts = String.valueOf(this.trynumber);
            new AlertDialog.Builder(this)
                    // 메시지를 설정한다.
                    .setMessage("congratulation !! your attempts : "+attempts+" Retry It?")
                    // positive 버튼을 설정한다.
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                    {
                        // positive 버튼에 대한 클릭 이벤트 처리를 구현한다.
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            CreateNumber();
                            // 다이얼로그를 화면에서 사라지게 한다.
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        // negative 버튼에 대한 클릭 이벤트 처리를 구현한다.
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // 다이얼로그를 화면에서 사라지게 한다.
                            dialog.dismiss();
                            finish();
                        }
                    }
                    )
                    // 빌더를 통해 만든 알럿 다이얼로그를 화면에 보여준다.
                    .show();
        } else {
            imagestrike1.setVisibility(View.INVISIBLE);
            imagestrike2.setVisibility(View.INVISIBLE);
            imagestrike3.setVisibility(View.INVISIBLE);
        }

        if (score.ball == 1) {
            imageball1.setVisibility(View.VISIBLE);
            imageball2.setVisibility(View.INVISIBLE);
            imageball3.setVisibility(View.INVISIBLE);
        } else if (score.ball == 2) {
            imageball1.setVisibility(View.VISIBLE);
            imageball2.setVisibility(View.VISIBLE);
            imageball3.setVisibility(View.INVISIBLE);
        } else if (score.ball == 3) {
            imageball1.setVisibility(View.VISIBLE);
            imageball2.setVisibility(View.VISIBLE);
            imageball3.setVisibility(View.VISIBLE);
        } else {
            imageball1.setVisibility(View.INVISIBLE);
            imageball2.setVisibility(View.INVISIBLE);
            imageball3.setVisibility(View.INVISIBLE);
        }

        if (score.out == 1) {
            imageout.setVisibility(View.VISIBLE);
        } else {
            imageout.setVisibility(View.INVISIBLE);
        }


        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast.makeText(this, "결과를 확인 하세요", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
