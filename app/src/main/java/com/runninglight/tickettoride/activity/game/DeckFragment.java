package com.runninglight.tickettoride.activity.game;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.runninglight.shared.CardColor;
import com.runninglight.shared.Cards.TrainCard;

public class DeckFragment extends Fragment
{
}

class CardHolder
{
    private ImageView imageView;
    private TrainCard trainCard;

    public CardHolder(ImageView imageView)
    {
        this.imageView = imageView;
        trainCard = null;
    }

    public CardColor viewCardColor()
    {
        if (trainCard == null) return null;
        return trainCard.getCardColor();
    }

    public void setCard(TrainCard trainCard)
    {
        this.trainCard = trainCard;
        setTrainCardImage(this.trainCard);
    }

    public TrainCard getCard()
    {

        return null;
    }

    private void setTrainCardImage(TrainCard trainCard)
    {
        switch (trainCard.getCardColor())
        {
            case BLACK:
                break;

            case WHITE:
                break;

            case RED:
                break;

            case BLUE:
                break;

            case PINK:
                break;

            case GREEN:
                break;

            case ORANGE:
                break;

            case YELLOW:
                break;

            case WILD:
                break;

            default:

        }
    }
}
