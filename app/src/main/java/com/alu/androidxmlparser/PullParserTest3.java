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
import java.util.LinkedList;

public class PullParserTest3 extends AppCompatActivity {

    TextView InitialTxtView, OutputTxtView;

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

        LinkedList<WidgetClass> alist = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_parser_test3);


        InitialTxtView = (TextView)findViewById(R.id.InitialTxt);
        OutputTxtView = (TextView)findViewById(R.id.OutputTxt);

        InitialTxtView.setText("Initial text: \n" + widgetString);

        //Make call to parsing code
        //Note this is not the best location
        alist = parseData(widgetString);

        // Write list to Log for testing
        if (alist != null)
        {
            Log.e("MyTag","List not null");
            for (Object o : alist)
            {
                Log.e("MyTag",o.toString());
            }
        }
        else
        {
            Log.e("MyTag","List is null");
        }

    }

    // XML that requires to be parsed
    // Note this is not the best place to define string
    // String defined here for testing purposes



    private LinkedList<WidgetClass> parseData(String dataToParse)
    {
        WidgetClass widget = null;
        LinkedList <WidgetClass> alist = null;
        try
        {
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
                    if (xpp.getName().equalsIgnoreCase("widgetcollection"))
                    {
                        alist  = new LinkedList<WidgetClass>();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("widget"))
                    {
                        Log.e("MyTag","Item Start Tag found");
                        widget = new WidgetClass();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("bolt"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text
                        Log.e("MyTag","Bolt is " + temp);
                        widget.setBolt(temp);
                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("Nut"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            Log.e("MyTag","Nut is " + temp);
                            widget.setNut(temp);
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("Washer"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                Log.e("MyTag","Washer is " + temp);
                                widget.setWasher(temp);
                            }
                }
                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("widget"))
                    {
                        Log.e("MyTag","widget is " + widget.toString());
                        alist.add(widget);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("widgetcollection"))
                    {
                        int size;
                        size = alist.size();
                        Log.e("MyTag","widgetcollection size is " + size);
                    }
                }


                // Get the next event
                eventType = xpp.next();

            } // End of while

            //return alist;
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

        DisplayItems(alist);

        return alist;

    }


    public void DisplayItems(LinkedList<WidgetClass> alist){

        if(alist !=null){
            for (WidgetClass data : alist)
            {
                OutputTxtView.setText(OutputTxtView.getText()+"\nbolt : " + data.getBolt()+"\n");
                OutputTxtView.setText(OutputTxtView.getText()+"\nwasher : " + data.getWasher()+"\n");
                OutputTxtView.setText(OutputTxtView.getText()+"\nnut : " + data.getNut()+"\n");
                OutputTxtView.setText(OutputTxtView.getText()+"-----------------------");
            }
        }
    }


}
