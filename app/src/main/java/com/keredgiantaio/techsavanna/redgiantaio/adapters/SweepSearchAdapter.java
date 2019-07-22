package com.keredgiantaio.techsavanna.redgiantaio.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.keredgiantaio.techsavanna.redgiantaio.R;
import com.keredgiantaio.techsavanna.redgiantaio.activities.RoadShowActivity;
import com.keredgiantaio.techsavanna.redgiantaio.activities.SingleSweepActivity;
import com.keredgiantaio.techsavanna.redgiantaio.methods.Sweep;

import java.util.List;


//To give the search bar functionalities

public class SweepSearchAdapter extends  RecyclerView.Adapter<SweepSearchAdapter.SweepSearchHolder> {

    String telephone = new Intent().getStringExtra("telephone");

    private List<Sweep> sweepList;
    private Context contexts;
    private LayoutInflater mInflater;
    public SweepSearchAdapter(List<Sweep> sweepList, Context context) {
        this.sweepList = sweepList;
        this.contexts = context;
    }

    @Override
    public SweepSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sweep, parent, false);

        return new SweepSearchHolder(view);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void onBindViewHolder(SweepSearchHolder holder, final int position) {
        final Sweep structure = sweepList.get(position);
        System.out.println("Data detiles" + structure.getName());
        // holder.cardView.set;
        holder.name.setText(structure.getName());
        holder.startdate.setText(structure.getDate());
        holder.enddate.setText(structure.getClosed_on());



        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                   // System.out.println("esthers "+structure.getName());
                    switch(structure.getName()) {
                        case "roadshow":


                            String roadshow = "roadshow";
                            Intent intent_roadshow = new Intent(contexts, SingleSweepActivity.class);

                            intent_roadshow.putExtra("STRUCTURE_NAME", roadshow);
                            intent_roadshow.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent_roadshow);
                                break;

                        case "marketstorm":
                            String marketstorm = "marketstorm";
                            Intent intent_marketstorm = new Intent(contexts, SingleSweepActivity.class);

                            intent_marketstorm.putExtra("STRUCTURE_NAME", marketstorm);
                            intent_marketstorm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent_marketstorm);
                            break;

                        case "instore":
                            String instore = "instore";
                            Intent intent_instore = new Intent(contexts, SingleSweepActivity.class);

                            intent_instore.putExtra("STRUCTURE_NAME", instore);
                           intent_instore.putExtra("telephone", telephone);
                            intent_instore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent_instore);
                            break;

                        case "doortodoor":
                            String doortodoor = "doortodoor";
                            Intent intent_doortodoor = new Intent(contexts, SingleSweepActivity.class);

                            intent_doortodoor.putExtra("STRUCTURE_NAME", doortodoor);
                            intent_doortodoor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent_doortodoor);
                            break;

                        case "merchandising":
                            String merchandising = "merchandising";
                            Intent intent_merchandising = new Intent(contexts, SingleSweepActivity.class);

                            intent_merchandising.putExtra("STRUCTURE_NAME", merchandising);
                            intent_merchandising.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent_merchandising);
                            break;
                    }

                    /*
                    *
                    * String myname = name.getText().toString();

                Intent welcome = new Intent(getApplicationContext(), Welcome.class);
                welcome.putExtra("value", myname);

                startActivity(welcome);*/


                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return sweepList.size();
    }

    public static class SweepSearchHolder extends RecyclerView.ViewHolder {
        TextView name, startdate, enddate;
        CheckBox confirm;

        CardView cardView;
        public SweepSearchHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            startdate = itemView.findViewById(R.id.startdate);
            enddate = itemView.findViewById(R.id.enddate);
            confirm = itemView.findViewById(R.id.confirm);
            cardView=itemView.findViewById(R.id.cardveiw);

        }
    }
}
