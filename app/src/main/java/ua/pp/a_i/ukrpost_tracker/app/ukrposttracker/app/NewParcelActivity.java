package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;


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
        task.execute(barcode);
    }


    class ParcelTask extends AsyncTask<String, Void, String> {
        private Activity activity;

        ParcelTask(Activity activity) {
            this.activity=activity;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client=new DefaultHttpClient();
            HttpGet get=new HttpGet();
            String barcode=params[0];
            get.setURI(URI.create("http://services.ukrposhta.com/barcodestatistic/barcodestatistic.asmx/GetBarcodeInfo?guid=fcc8d9e1-b6f9-438f-9ac8-b67ab44391dd&culture=uk&barcode=" + barcode));
            String result="";
            try {
                Thread.sleep(5000);
                HttpEntity entity=client.execute(get).getEntity();
                byte[] arr=new byte[(int)entity.getContentLength()];
                entity.getContent().read(arr);
                result=arr.toString();
            }
            catch (Exception e){
                Toast.makeText(NewParcelActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(activity,result,Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }
}
