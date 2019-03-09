package com.runninglight.tickettoride.activity.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.tickettoride.IPresenter.game.IDestCardPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.iview.game.IDestCardView;
import com.runninglight.tickettoride.presenter.game.DestCardPresenter;

import java.util.ArrayList;

public class DestinationCardActivity extends AppCompatActivity implements IDestCardView {

    private IDestCardPresenter presenter;

    private ImageView destCard1;
    private RadioButton destCard1Button;
    private ImageView destCard2;
    private RadioButton destCard2Button;
    private ImageView destCard3;
    private RadioButton destCard3Button;

    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_card);
        presenter = DestCardPresenter.getInstance();
        presenter.addView(this);

        destCard1 = findViewById(R.id.dest_card_1);
        destCard1Button = findViewById(R.id.dest_card_1_radio);
        destCard2 = findViewById(R.id.dest_card_2);
        destCard2Button = findViewById(R.id.dest_card_2_radio);
        destCard3 = findViewById(R.id.dest_card_3);
        destCard3Button = findViewById(R.id.dest_card_3_radio);
        confirmButton = findViewById(R.id.confirm_dest_cards_button);

        drawDestCards();
        setImageClickListeners();
        setConfirmButtonListener();
    }

    @Override
    public void onBackPressed(){
        // Back button is disabled
    }

    public void drawDestCards(){
        int resID = 0;
        DestinationCard[] cards = presenter.drawDestCards();
        if(cards[0] != null) {
            resID = getResources().getIdentifier(cards[0].getImageResourceString(), "drawable", getPackageName());
            destCard1.setImageResource(resID);
        }
        if(cards[1] != null) {
            resID = getResources().getIdentifier(cards[1].getImageResourceString(), "drawable", getPackageName());
            destCard2.setImageResource(resID);
        }
        if(cards[2] != null) {
            resID = getResources().getIdentifier(cards[2].getImageResourceString(), "drawable", getPackageName());
            destCard3.setImageResource(resID);
        }
    }
    private void setImageClickListeners(){
        destCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destCard1Button.setChecked(!destCard1Button.isChecked());
            }
        });
        destCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destCard2Button.setChecked(!destCard2Button.isChecked());
            }
        });
        destCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destCard3Button.setChecked(!destCard3Button.isChecked());
            }
        });
    }

    public void setConfirmButtonListener(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> keptIndices = new ArrayList<>();
                ArrayList<Integer> returnIndices = new ArrayList<>();
                if(destCard1Button.isChecked()){
                    keptIndices.add(0);
                }
                else{
                    returnIndices.add(0);
                }
                if(destCard2Button.isChecked()){
                    keptIndices.add(1);
                }
                else{
                    returnIndices.add(1);
                }
                if(destCard3Button.isChecked()){
                    keptIndices.add(2);
                }
                else{
                    returnIndices.add(2);
                }
                presenter.returnDestCards(keptIndices.toArray(new Integer[0]), returnIndices.toArray(new Integer[0]));
            }
        });
    }

    public void onSelectionSuccessful(){
        finish();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
