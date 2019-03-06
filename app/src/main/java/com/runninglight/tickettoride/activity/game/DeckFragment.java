package com.runninglight.tickettoride.activity.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.runninglight.shared.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ServerProxy;

public class DeckFragment extends Fragment
{
    private View view;

    private CardHolder cardHolder1;
    private CardHolder cardHolder2;
    private CardHolder cardHolder3;
    private CardHolder cardHolder4;
    private CardHolder cardHolder5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_deck, container, false);

        ImageView imageView1 = view.findViewById(R.id.card_holder_1);
        ImageView imageView2 = view.findViewById(R.id.card_holder_2);
        ImageView imageView3 = view.findViewById(R.id.card_holder_3);
        ImageView imageView4 = view.findViewById(R.id.card_holder_4);
        ImageView imageView5 = view.findViewById(R.id.card_holder_5);

        ImageView deckImageView = view.findViewById(R.id.deck_view);

        deckImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addCardToFaceUp(ServerProxy.getInstance().drawTrainCard(null));
            }
        });

        cardHolder1 = new CardHolder(imageView1);
        cardHolder2 = new CardHolder(imageView2);
        cardHolder3 = new CardHolder(imageView3);
        cardHolder4 = new CardHolder(imageView4);
        cardHolder5 = new CardHolder(imageView5);

        cardHolder1.setCard(null);
        cardHolder2.setCard(null);
        cardHolder3.setCard(null);
        cardHolder4.setCard(null);
        cardHolder5.setCard(null);

        return view;
    }

    public void addCardToFaceUp(TrainCard trainCard)
    {
        if (cardHolder1.isEmpty()) { cardHolder1.setCard(trainCard); return; }
        if (cardHolder2.isEmpty()) { cardHolder2.setCard(trainCard); return; }
        if (cardHolder3.isEmpty()) { cardHolder3.setCard(trainCard); return; }
        if (cardHolder4.isEmpty()) { cardHolder4.setCard(trainCard); return; }
        if (cardHolder5.isEmpty()) { cardHolder5.setCard(trainCard); return; }
    }
}

class CardHolder
{
    private ImageView imageView;
    private TrainCard trainCard;
    private boolean isEmpty;

    public CardHolder(final ImageView imageView)
    {
        this.imageView = imageView;
        trainCard = null;
        isEmpty = true;

        this.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("TTR.DeckFragment", "selected card: " + trainCard.getCardColor() );
                setCard(null);
            }
        });
    }

    public CardColor viewCardColor()
    {
        if (trainCard == null) return null;
        return trainCard.getCardColor();
    }

    public void setCard(TrainCard trainCard)
    {
        if (trainCard != null) isEmpty = false;
        else isEmpty = true;
        this.trainCard = trainCard;
        setTrainCardImage(this.trainCard);
    }

    public TrainCard getCard()
    {

        return null;
    }

    public boolean isEmpty()
    {
        return isEmpty;
    }

    private void setTrainCardImage(TrainCard trainCard)
    {
        if (trainCard == null)
        {
            imageView.setImageResource(R.drawable.traincard_blank);
            return;
        }
        switch (trainCard.getCardColor())
        {
            case BLACK:
                imageView.setImageResource(R.drawable.traincard_black);
                break;

            case WHITE:
                imageView.setImageResource(R.drawable.traincard_white);
                break;

            case RED:
                imageView.setImageResource(R.drawable.traincard_red);
                break;

            case BLUE:
                imageView.setImageResource(R.drawable.traincard_blue);
                break;

            case PINK:
                imageView.setImageResource(R.drawable.traincard_pink);
                break;

            case GREEN:
                imageView.setImageResource(R.drawable.traincard_green);
                break;

            case ORANGE:
                imageView.setImageResource(R.drawable.traincard_orange);
                break;

            case YELLOW:
                imageView.setImageResource(R.drawable.traincard_yellow);
                break;

            case WILD:
                imageView.setImageResource(R.drawable.traincard_wild);
                break;

            default:
                imageView.setImageResource(R.drawable.traincard_blank);

        }
    }
}
