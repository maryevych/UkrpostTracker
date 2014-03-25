package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;


public class NewParcelActivity extends Activity {
    public static final int RESULT_OK=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parcel);
    }

    public void addButton_onClick(View view){
        String name=((EditText)findViewById(R.id.parcelNameEditText)).getText().toString();
        String barcode=((EditText)findViewById(R.id.parcelNameEditText)).getText().toString();
        ParcelTask task=new ParcelTask(NewParcelActivity.this);
        task.execute(barcode,name);
    }


    class ParcelTask extends AsyncTask<String, Void, Parcel> {
        private Activity activity;

        ParcelTask(Activity activity) {
            this.activity=activity;
        }

        @Override
        protected Parcel doInBackground(String... params) {
            HttpClient client=new DefaultHttpClient();
            HttpGet get=new HttpGet();
            String barcode=params[0];
            String name=params[1];
            get.setURI(URI.create("http://services.ukrposhta.com/barcodestatistic/barcodestatistic.asmx/GetBarcodeInfo?guid=fcc8d9e1-b6f9-438f-9ac8-b67ab44391dd&culture=uk&barcode=" + barcode));
            InputStream stream=null;
            try {
                Thread.sleep(5000);
                HttpEntity entity=client.execute(get).getEntity();
                stream=entity.getContent();
            }
            catch (Exception e){
                Toast.makeText(NewParcelActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            XmlPullParser parser= Xml.newPullParser();
            Parcel parcel=null;
            try {
                parser.setInput(new InputStreamReader(stream));
                int code=parser.getEventType();
                String status="";
                while (code!=XmlPullParser.END_DOCUMENT) {
                    if(code==XmlPullParser.START_TAG){
                        if(parser.getName().equals("eventdescription")){
                            status=parser.getName();
                        }
                    }
                    code=parser.next();
                }
                parcel=new Parcel(name,barcode,status,new Date());
                Parcel.insertParcelToDb(parcel);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return parcel;
        }

        @Override
        protected void onPostExecute(Parcel parcel) {
            super.onPostExecute(parcel);
            Toast.makeText(activity,parcel.getName(),Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }
}
