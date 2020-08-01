package com.example.navigation2.ui.feedback;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigation2.R;

public class FeedbackFragment extends Fragment {

    Button button;
    RatingBar ratingStars;
    float myrating = 0;
    private FeedbackViewModel feedbackViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_feedback);
        final View root = inflater.inflate(R.layout.feedback_fragment, container, false);
        final EditText editText = root.findViewById(R.id.edit1);
        final EditText editText2 = root.findViewById(R.id.edit2);

        button = root.findViewById(R.id.btt_feedback);
        ratingStars = root.findViewById(R.id.ratingBar);

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int rating = (int) v;
                String message = null;

                myrating = ratingBar.getRating();

                switch (rating){
                    case 1:
                        message = "Sorry to here that! :(";
                        break;
                    case 2:
                        message = "We always accept suggestion!";
                        break;
                    case 3:
                        message = "Good enough!";
                        break;
                    case 4:
                        message = "Great! Thank you!";
                        break;
                    case 5:
                        message = "Awesome!";
                        break;
                }

                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(root.getContext(), "Your rating is : "+ String.valueOf(myrating), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String("xyz@gmail.com"));
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback From Financial App");
                i.putExtra(Intent.EXTRA_TEXT,"Name: " + editText.getText()+ "\n" +
                        "Message: " + editText2.getText()+ "\n" +
                        "Rating: " + String.valueOf(myrating));
                try {
                    startActivity(Intent.createChooser(i,"please select Email"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(root.getContext(), "There is no Email Clients", Toast.LENGTH_SHORT).show();
                }

            }
        });

        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        final TextView textView = root.findViewById(R.id.text_feedback);
        feedbackViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
