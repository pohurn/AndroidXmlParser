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

public class PullParserTest2 extends AppCompatActivity {


    TextView InitialTxtView, OutputTxtView;

    // XML that requires to be parsed
    // Note this is not the best place to define string
    // String defined here for testing purposes
    private String widgetString = "<WidgetCollection> " +
            "<Widget> " +
            "<Bolt>M8 x 100</Bolt>	" +
            "<Nut>M8 Hex</Nut>	" +
            "<Washer>M8 Penny Washers</Washer>	" +
            "</Widget>" +
            "<Widget> " +
            "<Bolt>M8 x 150</Bolt>	" +
            "<Nut>M8 Hex</Nut>	" +
            "<Washer>M8 Penny Washers</Washer>	" +
            "</Widget>" +
            "<Widget> " +
            "<Bolt>M6 x 100</Bolt>	" +
            "<Nut>68 Hex</Nut>	" +
            "<Washer>M6 Penny Washers</Washer>	" +
            "	</Widget>" +
            "</WidgetCollection>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_parser_test2);


        InitialTxtView = (TextView)findViewById(R.id.InitialTxt);
        OutputTxtView = (TextView)findViewById(R.id.OutputTxt);

        //Make call to parsing code
        //Note this is not the best location
        parseData(widgetString);

    }

    private void parseData(String dataToParse)
    {
        try
        {
            InitialTxtView.setText("Initial text: \n" + widgetString);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("bolt"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text
                        Log.e("MyTag","Bolt is " + temp);

                        OutputTxtView.setText(OutputTxtView.getText()+"\nBolt is : " + temp+"\n");
                        OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");

                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("Nut"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            Log.e("MyTag","Nut is " + temp);

                            OutputTxtView.setText(OutputTxtView.getText()+"\nNut is : " + temp+"\n");
                            OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("Washer"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                Log.e("MyTag","Washer is " + temp);

                                OutputTxtView.setText(OutputTxtView.getText()+"\nWasher is : " + temp+"\n");
                                OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");
                            }
                }

                // Get the next event
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

        Log.e("MyTag","End document");

    }
}
