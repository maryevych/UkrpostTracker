package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final int NEW_PARCEL=1;

    ArrayList<Parcel> parcels;
    TextView noParcelsTextView;
    ListView parcelsListView;
    LinearLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=(LinearLayout)findViewById(R.id.layout);
        noParcelsTextView=(TextView)findViewById(R.id.noParcelsTextView);
        parcelsListView=(ListView)findViewById(R.id.parcelsListView);
        parcels=new ArrayList<>();
       /* parcels=Parcel.getParcelsFromDb();

        if(parcels.size()!=0){
            layout.removeView(noParcelsTextView);
        }
        */

        ParcelArrayAdapter adapter=new ParcelArrayAdapter();
        parcelsListView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_parcel) {
            Intent intent=new Intent(MainActivity.this,NewParcelActivity.class);
            startActivityForResult(intent,NEW_PARCEL);
        }
        return super.onOptionsItemSelected(item);
    }



    class ParcelArrayAdapter extends ArrayAdapter<Parcel>{

        public ParcelArrayAdapter() {
            super(MainActivity.this, R.layout.parcel_array_item,parcels);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView=convertView;
            if(itemView==null){
                itemView=getLayoutInflater().inflate(R.layout.parcel_array_item,parent,false);
            }

            Parcel currentParcel=parcels.get(position);
            TextView nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            nameTextView.setText(currentParcel.getName());

            return itemView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case NEW_PARCEL:
                if(resultCode==NewParcelActivity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String name = bundle.getString("name");
                    String barcode = bundle.getString("barcode");

                }
                break;
        }
    }
}
