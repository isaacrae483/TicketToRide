package com.runninglight.tickettoride.activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.tickettoride.IPresenter.game.IDeckPresenter;
import com.runninglight.tickettoride.R;
import com.runninglight.tickettoride.communication.ClientModel;
import com.runninglight.tickettoride.communication.ServerProxy;
import com.runninglight.tickettoride.iview.game.IDeckView;
import com.runninglight.tickettoride.presenter.game.DeckPresenter;

import java.util.Observer;

public class DeckFragment extends Fragment implements IDeckView
{
    private View view;
    private IDeckPresenter presenter;

    private CardHolder cardHolder1;
    private CardHolder cardHolder2;
    private CardHolder cardHolder3;
    private CardHolder cardHolder4;
    private CardHolder cardHolder5;

    private ImageView destCardDeck;
    private TextView destCardDeckSize;
    private RelativeLayout deckRelativeLayout;
    private TextView trainCardDeckSizeText;

    private int numTrainCardsInDeck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_deck, container, false);
        presenter = DeckPresenter.getInstance();
        ((DeckPresenter) presenter).addView(this);

        ImageView imageView1 = view.findViewById(R.id.card_holder_1);
        ImageView imageView2 = view.findViewById(R.id.card_holder_2);
        ImageView imageView3 = view.findViewById(R.id.card_holder_3);
        ImageView imageView4 = view.findViewById(R.id.card_holder_4);
        ImageView imageView5 = view.findViewById(R.id.card_holder_5);

        // ImageView deckImageView = view.findViewById(R.id.deck_view);

        deckRelativeLayout = view.findViewById(R.id.deck_view);
        trainCardDeckSizeText = view.findViewById(R.id.num_train_cards_deck);
        trainCardDeckSizeText.setText("" + ClientModel.getInstance().getCurrentGame().getTrainCardDeckSize());


        cardHolder1 = new CardHolder(imageView1, 1);
        cardHolder2 = new CardHolder(imageView2, 2);
        cardHolder3 = new CardHolder(imageView3, 3);
        cardHolder4 = new CardHolder(imageView4, 4);
        cardHolder5 = new CardHolder(imageView5, 5);

        cardHolder1.setCard(null);
        cardHolder2.setCard(null);
        cardHolder3.setCard(null);
        cardHolder4.setCard(null);
        cardHolder5.setCard(null);

        // TEMP for testing
        //ServerProxy.getInstance().drawCardFromFaceUpToHand(ClientModel.getInstance().getCurrentGame(), null, null, 1);
        //ServerProxy.getInstance().drawCardFromFaceUpToHand(ClientModel.getInstance().getCurrentGame(), null, null, 2);
        //ServerProxy.getInstance().drawCardFromFaceUpToHand(ClientModel.getInstance().getCurrentGame(), null, null, 3);
        //ServerProxy.getInstance().drawCardFromFaceUpToHand(ClientModel.getInstance().getCurrentGame(), null, null, 4);
        //ServerProxy.getInstance().drawCardFromFaceUpToHand(ClientModel.getInstance().getCurrentGame(), null, null, 5);

        destCardDeck = view.findViewById(R.id.dest_deck);
        destCardDeckSize = view.findViewById(R.id.dest_deck_size);

        presenter.checkIfMyTurn();

        return view;
    }

    @Override
    public void enableListeners(){
        enableDestDeckListener();
        enableTrainDeckListeners();
    }

    @Override
    public void enableDestDeckListener(){
        destCardDeck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), DestinationCardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void enableTrainDeckListeners(){
        deckRelativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.drawCardFromDeck();
            }
        });

        cardHolder1.setClickListener();
        cardHolder2.setClickListener();
        cardHolder3.setClickListener();
        cardHolder4.setClickListener();
        cardHolder5.setClickListener();
    }

    @Override
    public void disableListeners(){
        disableDestDeckListener();
        disableTrainDeckListeners();
    }

    @Override
    public void disableDestDeckListener(){
        destCardDeck.setOnClickListener(null);
    }

    @Override
    public void disableTrainDeckListeners(){
        deckRelativeLayout.setOnClickListener(null);

        disableFaceUpListener(1);
        disableFaceUpListener(2);
        disableFaceUpListener(3);
        disableFaceUpListener(4);
        disableFaceUpListener(5);
    }

    @Override
    public void disableFaceUpListener(int position){
        switch (position){
            case 1:
                cardHolder1.disableClickListener();
                break;
            case 2:
                cardHolder2.disableClickListener();
                break;
            case 3:
                cardHolder3.disableClickListener();
                break;
            case 4:
                cardHolder4.disableClickListener();
                break;
            case 5:
                cardHolder5.disableClickListener();
                break;
        }
    }

    @Override
    public void disableFaceUpWildListeners(){
        if(cardHolder1.getCard() != null && cardHolder1.getCard().isWild()){
            disableFaceUpListener(1);
        }
        if(cardHolder2.getCard() != null && cardHolder2.getCard().isWild()){
            disableFaceUpListener(2);
        }
        if(cardHolder3.getCard() != null && cardHolder3.getCard().isWild()){
            disableFaceUpListener(3);
        }
        if(cardHolder4.getCard() != null && cardHolder4.getCard().isWild()){
            disableFaceUpListener(4);
        }
        if(cardHolder5.getCard() != null && cardHolder5.getCard().isWild()){
            disableFaceUpListener(5);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.initObserver();
        presenter.initDestCardDeck();
    }

    @Override
    public void onPause(){
        super.onPause();
        presenter.removeObserver();
    }

    @Override
    public void addCardToFaceUpDeck(TrainCard trainCard, int position)
    {
        if (position == 1) { cardHolder1.setCard(trainCard); return; }
        if (position == 2) { cardHolder2.setCard(trainCard); return; }
        if (position == 3) { cardHolder3.setCard(trainCard); return; }
        if (position == 4) { cardHolder4.setCard(trainCard); return; }
        if (position == 5) { cardHolder5.setCard(trainCard); return; }
    }

    class CardHolder
    {
        private ImageView imageView;
        private TrainCard trainCard;
        private boolean isEmpty;
        private int position;

        public CardHolder(final ImageView imageView, final int position)
        {
            this.imageView = imageView;
            this.position = position;
            trainCard = null;
            isEmpty = true;


        }

        public CardColor viewCardColor()
        {
            if (trainCard == null) return null;
            return trainCard.getCardColor();
        }

        public void setClickListener(){
            this.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (isEmpty) return;
                    Log.d("TTR.DeckFragment", "selected card: " + trainCard.getCardColor() );
                    setCard(null);
                    ClientModel.getInstance().getCurrentGame().removeCardFromFaceUp(position);
                    presenter.cardDrawnFromFaceUp(trainCard, position);
                }
            });
        }

        public void disableClickListener(){
            this.imageView.setOnClickListener(null);
        }

        public void setCard(TrainCard trainCard)
        {
            if (trainCard != null)
            {
                isEmpty = false;
                this.trainCard = trainCard;
                setTrainCardImage(this.trainCard);
            }
            else
            {
                isEmpty = true;
                setTrainCardImage(null);
            }
        }

        public TrainCard getCard()
        {
            return trainCard;
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

    public void refreshDestDeck(int deckSize){
        destCardDeckSize.setText(Integer.toString(deckSize));
    }

    @Override
    public void refreshTrainCardDeckSize(int deckSize)
    {
        trainCardDeckSizeText.setText(Integer.toString(deckSize));
    }
}