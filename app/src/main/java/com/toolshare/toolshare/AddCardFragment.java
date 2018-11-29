package com.toolshare.toolshare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.toolshare.toolshare.db.DbHandler;
import com.toolshare.toolshare.models.Card;
import com.toolshare.toolshare.models.Tool;
import com.vinaygaba.creditcardview.CreditCardView;


public class AddCardFragment extends Fragment {

    private Bundle bundle;
    private DbHandler db;
    private Tool tool;
    private Button mToolPurchase;
    private CreditCardView creditCardView;
    private Card card;
    private String cardNumber;
    private String cardName;
    private int expiryDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, null);


        bundle = getArguments();
        db = (DbHandler) bundle.getSerializable("db");
        tool = (Tool) bundle.getSerializable("tool");

        /*card = new Card();
        if (card.getCardNumber()=="null"){
            insertCard();
        }
        cardNumber = creditCardView.getCardNumber();
        cardName = creditCardView.getCardName();

        expiryDate = creditCardView.getExpiryDate();
        */


        creditCardView = (CreditCardView) view.findViewById(R.id.card);
        /*mToolPurchase = (Button) view.findViewById(R.id.b_rent_tool);
        mToolPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCard();
            }
        });*/
        return view;
    }

    /*private void insertCard() {
        card.setCardNumber(cardNumber);
        card.setFullName(cardName);
        card.setExpiryMonth(expiryDate);
        card.setExpiryYear(expiryDate);
    }*/

}