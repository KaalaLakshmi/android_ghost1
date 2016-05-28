package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
   // private GhostDictionary dictionary1;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try
        {
            InputStream inputStream = assetManager.open("words.txt");
            //dictionary = new FastDictionary(inputStream);
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e)
        {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        TextView fragment = (TextView) findViewById(R.id.ghostText);
        TextView status = (TextView) findViewById(R.id.gameStatus);
        if (29<=keyCode && keyCode<=54)
        {
            char c = (char)event.getUnicodeChar();
            fragment.append(""+c);
            String word = fragment.getText().toString();
            //status.setText(word);
           /* if(dictionary.isWord(word))
            {
                status.setText("Complete Word");
            }
            else {status.setText("Not Found");}*/
            userTurn=false;
            status.setText(COMPUTER_TURN);
            computerTurn();
        }

        return super.onKeyUp(keyCode,event);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    public void challenge(View view)
    {
        TextView text = (TextView) findViewById(R.id.ghostText);
        TextView label = (TextView) findViewById(R.id.gameStatus);
        String word = text.getText().toString();
        String s=dictionary.getAnyWordStartingWith(word);
        if((word.length()>=4) && (dictionary.isWord(word)))
        {
            label.setText("User Wins!");
        }
        else if(s!=null)
        {
            label.setText("Computer Wins!");
            text.setText(s);
        }
        else
        {
            label.setText("User Wins!");
        }

    }

    private void computerTurn()
    {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);
        String word = text.getText().toString();
       /* if(word=="")
        {
            Random r=new Random();
            char c=(char)(r.nextInt(26)+'a');
            text.setText(""+c);
        }*/
        if((word.length()>=4) && (dictionary.isWord(word)))
        {
            label.setText("Computer Wins!");
        }
       else
        {
            String s = dictionary.getAnyWordStartingWith(word);
            if (s != null)
            {
                int len = word.length();
                String res = s.substring(len, len + 1);
                text.append(res);

            } else {
                label.setText("Computer Wins!");
            }
        }
        userTurn = true;
        label.setText(USER_TURN);
    }
}
