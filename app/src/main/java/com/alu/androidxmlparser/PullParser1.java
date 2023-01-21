package com.alu.androidxmlparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class PullParser1 extends AppCompatActivity {

    TextView InitialTxtView, OutputTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_parser1);


        InitialTxtView = (TextView)findViewById(R.id.InitialTxt);
        OutputTxtView = (TextView)findViewById(R.id.OutputTxt);

        //Make call to parsing code
        //Note this is not the best location
        parseData();
    }

    private void parseData()
    {
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            String textInput = "<foo>Hello World!</foo>";
            String textOutput = "";

            InitialTxtView.setText("Initial text: \n" + textInput);
            xpp.setInput( new StringReader( textInput ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_DOCUMENT)
                {
                    System.out.println("Start document");
                    Log.e("MyTag","Start document");
                }
                else
                if(eventType == XmlPullParser.START_TAG)
                {
                    String temp = xpp.getName();
                    System.out.println("Start tag "+temp);
                    Log.e("MyTag","Start tag "+temp);

                    OutputTxtView.setText(OutputTxtView.getText()+"\nStart tag : " + temp+"\n");
                    OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");
                }
                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    String temp = xpp.getName();
                    System.out.println("End tag "+temp);
                    Log.e("MyTag","End tag "+temp);

                    OutputTxtView.setText(OutputTxtView.getText()+"\nEnd tag : " + temp+"\n");
                    OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");

                }
                else
                if(eventType == XmlPullParser.TEXT)
                {
                    String temp = xpp.getText();
                    System.out.println("Text "+temp);
                    Log.e("MyTag","Text is "+temp);

                    OutputTxtView.setText(OutputTxtView.getText()+"\nText is : " + temp+"\n");
                    OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");
                }

                eventType = xpp.next();

            } // End of while
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        System.out.println("End document");

    }

}
