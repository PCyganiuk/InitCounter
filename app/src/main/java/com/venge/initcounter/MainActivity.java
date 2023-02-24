package com.venge.initcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    LinearLayout initList;

    ArrayList<Creature> q = new ArrayList<>();
    String name;
    int init,initMod,id,hp,nr;
    Random rand = new Random();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText heroInit;
    private EditText heroDexMod;
    private EditText allyNr;
    private EditText allyDexMod;
    private EditText allyHp;
    TextView initEmpty,banner;
    Button addHero,addAlly,addEnemy,deleteSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList = findViewById(R.id.initlist);
        initEmpty = findViewById(R.id.initemtpy);
        addHero = findViewById(R.id.addhero);
        addHero.setOnClickListener(this);
        addAlly = findViewById(R.id.addally);
        addAlly.setOnClickListener(this);
        addEnemy = findViewById(R.id.addenemy);
        addEnemy.setOnClickListener(this);
        deleteSelected = findViewById(R.id.deleteselected);
        deleteSelected.setOnClickListener(this);
        deleteSelected.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.deleteall){
            q.clear();
            initList.removeAllViews();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        System.out.println(id);
        if(id==addHero.getId()){
            addNewHeroDialog();
        }
        else if(id==addAlly.getId()){
            addNewCreatureDialog(1);
        }
        else if(id==addEnemy.getId())
            addNewCreatureDialog(0);
        else if(id==deleteSelected.getId()){
            for(int i=0;i<initList.getChildCount();i++){
                if(initList.getChildAt(i).isSelected()){
                    initList.removeViewAt(i);
                    q.remove(i);
                    i--;
                }
            }
            addHero.setVisibility(View.VISIBLE);
            addAlly.setVisibility(View.VISIBLE);
            addEnemy.setVisibility(View.VISIBLE);
            deleteSelected.setVisibility(View.GONE);
        }
    }

    public void addNewHeroDialog(){
        name="";
        init=100;
        initMod=100;
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View HeroPopupView = getLayoutInflater().inflate(R.layout.newheropopup,null);
        EditText heroName = HeroPopupView.findViewById(R.id.heroname);
        heroInit = HeroPopupView.findViewById(R.id.heroinit);
        heroDexMod = HeroPopupView.findViewById(R.id.herodexmod);
        heroName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
            }
        });
        heroInit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                    init=100;
                else
                    init = Integer.parseInt(heroInit.getText().toString());
            }
        });
        heroDexMod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")||editable.toString().equals("-"))
                    initMod=100;
                else
                    initMod = Integer.parseInt(heroDexMod.getText().toString());
            }
        });
        dialogBuilder.setPositiveButton("save",null);
        dialogBuilder.setNegativeButton("cancel",null);
        dialogBuilder.setView(HeroPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        Button saveHero = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        saveHero.setOnClickListener(view -> {
            if( !name.isEmpty() && init!=100 && initMod!=100) {
                if(q.size()>0)
                    sortInit();
                q.add(id,new Hero(name, init, initMod));
                CharSequence display = q.get(id).display;
                CheckedTextView latest = new CheckedTextView(getApplicationContext());
                latest.setClickable(true);
                latest.setSelected(false);
                latest.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_green));
                latest.setTextAppearance(android.R.style.TextAppearance_Material_Display1);
                latest.setTextColor(Color.WHITE);
                latest.setId(View.generateViewId());
                latest.setText(display);
                latest.setOnLongClickListener(this);
                initList.addView(latest,id);
                if (q.size() > 0)
                    initEmpty.setVisibility(View.GONE);
                else
                    initEmpty.setVisibility(View.VISIBLE);

                dialog.dismiss();
            }
            else
                Toast.makeText(getApplicationContext(),"fill all fields",Toast.LENGTH_SHORT).show();
        });
    }
    public void addNewCreatureDialog(int type){
        name="";
        init=100;
        initMod=100;
        hp=-1;
        nr=1;
        id=0;
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View allyPopupView = getLayoutInflater().inflate(R.layout.newallypopup,null);
        banner = allyPopupView.findViewById(R.id.newally);
        allyNr = allyPopupView.findViewById(R.id.allynr);
        if(type==1) {
            banner.setText("New Ally");
            allyNr.setHint("number of allies");
        }
        else {
            banner.setText("New Enemy");
            allyNr.setHint("number of enemies");
        }
        EditText allyName = allyPopupView.findViewById(R.id.allyname);
        allyDexMod = allyPopupView.findViewById(R.id.allydexmod);
        allyHp = allyPopupView.findViewById(R.id.allyhp);
        allyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
            }
        });
        allyNr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                    nr=1;
                else
                    nr = Integer.parseInt(allyNr.getText().toString());
            }
        });
        allyDexMod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")||editable.toString().equals("-"))
                    initMod=100;
                else
                    initMod = Integer.parseInt(allyDexMod.getText().toString());
            }
        });
        allyHp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                    hp=-1;
                else
                    hp = Integer.parseInt(allyHp.getText().toString());
            }
        });
        dialogBuilder.setPositiveButton("save",null);
        dialogBuilder.setNegativeButton("cancel",null);
        dialogBuilder.setView(allyPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        Button saveAlly = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        saveAlly.setOnClickListener(view -> {
            if( !name.isEmpty() && initMod!=100) {
                for(int i=0;i<nr;i++) {
                    init = rand.nextInt(20) + 1 + initMod;
                    if (q.size() > 0)
                        sortInit();
                    if(nr==1)
                        q.add(id, new Npc(name, init, initMod,hp));
                    else
                        q.add(id, new Npc(name+i, init, initMod,hp));
                    CharSequence display = q.get(id).display;
                    CheckedTextView latest = new CheckedTextView(getApplicationContext());
                    if(type==1)
                        latest.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_yellow));
                    else
                        latest.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_red));
                    latest.setTextAppearance(android.R.style.TextAppearance_Material_Display1);
                    latest.setTextColor(Color.WHITE);
                    latest.setClickable(true);
                    latest.setSelected(false);
                    latest.setId(View.generateViewId());
                    latest.setOnLongClickListener(this);
                    latest.setText(display);
                    initList.addView(latest, id);
                    if (q.size() > 0)
                        initEmpty.setVisibility(View.GONE);
                    else
                        initEmpty.setVisibility(View.VISIBLE);

                    dialog.dismiss();
                }
            }
            else
                Toast.makeText(getApplicationContext(),"fill all fields",Toast.LENGTH_SHORT).show();
        });
    }

    public void sortInit(){
        id=q.size();
        for(int i=q.size()-1;i>=0;i--){
            if(i!=0&&(init>q.get(i).init||(init==q.get(i).init&&initMod>=q.get(i).initMod))&&(init<q.get(i-1).init||(init==q.get(i-1).init&&initMod<=q.get(i-1).initMod))){
                id=i;
            }
            else if(i==0&&(init>q.get(i).init||(init==q.get(i).init&&initMod>q.get(i).initMod)))
                id=i;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        boolean selected;
        int i;
        CheckedTextView ctv = findViewById(view.getId());
        if(ctv.isSelected()){
            ctv.setCheckMarkDrawable(R.drawable.unchecked);
            ctv.setSelected(false);
            for(i=0,selected=false;i<initList.getChildCount();i++){
                if(initList.getChildAt(i).isSelected()){
                    selected=true;
                    break;
                }
            }
            if(!selected){
                addHero.setVisibility(View.VISIBLE);
                addAlly.setVisibility(View.VISIBLE);
                addEnemy.setVisibility(View.VISIBLE);
                deleteSelected.setVisibility(View.GONE);
            }
        }
        else {
            ctv.setCheckMarkDrawable(R.drawable.checked_white);
            addHero.setVisibility(View.GONE);
            addAlly.setVisibility(View.GONE);
            addEnemy.setVisibility(View.GONE);
            deleteSelected.setVisibility(View.VISIBLE);
            ctv.setSelected(true);
        }
        return true;
    }
}