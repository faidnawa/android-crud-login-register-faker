package tk.faidnawa.latihanandroidcrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.faidnawa.latihanandroidcrud.adapter.Adapter;
import tk.faidnawa.latihanandroidcrud.app.AppController;
import tk.faidnawa.latihanandroidcrud.data.Data;
import tk.faidnawa.latihanandroidcrud.helper.SQLiteHandler;
import tk.faidnawa.latihanandroidcrud.helper.SessionManager;
import tk.faidnawa.latihanandroidcrud.until.Server;

import static tk.faidnawa.latihanandroidcrud.MainActivity.TAG_NO;

public class Crud extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
        ,NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama, txt_nim;
    String id, nama, nim;
    private Button btnLogout;
    private TextView na;
    private TextView em;
    private SQLiteHandler db;
    private SessionManager session;
    private static final String TAG = Crud.class.getSimpleName();

    private static String url_select 	 = Server.URL + "news.php?offset=";
    private static String url_insert 	 = Server.URL + "insert.php";
    private static String url_edit 	     = Server.URL + "edit.php";
    private static String url_update 	 = Server.URL + "update.php";
    private static String url_delete 	 = Server.URL + "delete.php";
    private int offSet = 0;
    int no;
    public static final String TAG_ID       = "id";
    public static final String TAG_NAMA     = "nama";
    public static final String TAG_NIM      = "nim";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Handler handler;
    Runnable runnable;
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // Fetching user details from SQLite
          HashMap<String, String> user = db.getUserDetails();

        String namee = user.get("name");
        String emaill = user.get("email");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_vi);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        na = (TextView)header.findViewById(R.id.namauser);
        em = (TextView)header.findViewById(R.id.emailuser);
        na.setText(namee);
        em.setText(emaill);
        userDetail.username = user.get("name");



        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);
        itemList.clear();

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new Adapter(Crud.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley(0);
                       }
                   }
        );

        // fungsi floating action button memanggil form kontak
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(Crud.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutt);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        ///
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    swipe.setRefreshing(true);
                    handler = new Handler();

                    runnable = new Runnable() {
                        public void run() {
                            callVolley(offSet);
                        }
                    };

                    handler.postDelayed(runnable, 3000);
                }
            }

        });





    }


    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley(0);
    }
    // untuk menampilkan semua data pada listview



    private void callVolley(int page){

        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest arrReq = new JsonArrayRequest(url_select + page,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Data item = new Data();

                                    no = obj.getInt(TAG_NO);

                                    item.setId(obj.getString(TAG_ID));
                                    item.setNama(obj.getString(TAG_NAMA));
                                    item.setnim(obj.getString(TAG_NIM));

                                    // adding news to news array
                                    itemList.add(item);

                                    if (no > offSet)
                                        offSet = no;

                                    Log.d(TAG, "offSet " + offSet);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }
                        }
                        swipe.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrReq);
    }



    // untuk mengosongi edittext pada form
    private void kosong(){
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_nim.setText(null);
    }

    // untuk menampilkan dialog from kontak
    private void DialogForm(String idx, String namax, String nimx, String button) {
        dialog = new AlertDialog.Builder(Crud.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_biodata, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Mahasiswa TIF" +
                "");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        txt_nim  = (EditText) dialogView.findViewById(R.id.txt_nim);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nama.setText(namax);
            txt_nim.setText(nimx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                nama    = txt_nama.getText().toString();
                nim  = txt_nim.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }




    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()){
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        onRefresh();
                        kosong();

                        Toast.makeText(Crud.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(Crud.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Crud.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("nama", nama);
                    params.put("nim", nim);
                } else {
                    params.put("id", id);
                    params.put("nama", nama);
                    params.put("nim", nim);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx      = jObj.getString(TAG_ID);
                        String namax    = jObj.getString(TAG_NAMA);
                        String nimx  = jObj.getString(TAG_NIM);

                        DialogForm(idx, namax, nimx, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(Crud.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Crud.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        onRefresh();

                        Toast.makeText(Crud.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(Crud.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Crud.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_Logout){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(Crud.this);
            builder1.setMessage("Apakah kamu ingin Logout.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ya",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            logoutUser();
                        }
                    });

            builder1.setNegativeButton(
                    "Tidak",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutt);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Crud.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"kalo udah login ya  tinggal logout ", Toast.LENGTH_LONG).show();

    }

    public boolean  onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.crud, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                return true;

            case R.id.chatting:
                startActivity(new Intent(this, userchat.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
