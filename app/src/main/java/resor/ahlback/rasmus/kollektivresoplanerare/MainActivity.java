package resor.ahlback.rasmus.kollektivresoplanerare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewTest);
        testGetKey();
    }

    public void testGetKey(){
        String hallplatser = "Hållplatser:" + getString(R.string.hallplatser_och_linjer);
        String platsuppslag = "Platsuppslag: " + getString(R.string.platsuppslag);
        String stringBuilder = hallplatser + "\n" + platsuppslag;
        textView.setText(stringBuilder);
    }
}
