package lock.file.lockerinfo.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import lock.file.lockerinfo.dialog.DetailsDialogView;
import lock.file.lockerinfo.dialog.EditDialogView;
import lock.file.lockerinfo.R;
import lock.file.lockerinfo.model.DataModel;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> implements Filterable {

    Activity activity;
    ArrayList<DataModel> itemListMain,itemListSearch;
    RecyclerView recyclerView;
    private int counter =0;

//update layout
    EditText titleET,descriptionET;
    Button saveBTN;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String key_id, key;


    public InfoAdapter(Activity activity, ArrayList<DataModel> itemListMain, RecyclerView recyclerView) {
        this.activity = activity;
        this.itemListMain = itemListMain;
        this.itemListSearch=itemListMain;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_diary_layout,null,false);
        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final DataModel dataModel = itemListMain.get(position);
//main layout
        holder.titleTv.setText(dataModel.getTitle());
        holder.descriptionTV.setText(dataModel.getDescription());
        holder.datetv.setText(dataModel.getDate());
        holder.timeTV.setText(dataModel.getTime());

        //update firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        key_id = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(key_id);
        key = itemListMain.get(position).getKey();

        holder.shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Title : " + itemListMain.get(position).getTitle() +
                         "\n Description : " + itemListMain.get(position).getDescription());
                intent.setType("text/plain");
                activity.startActivity(Intent.createChooser(intent, "Send To"));
            }
        });
        holder.deletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setTitle("Are you sure you want to delete this?");
                ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.child(key).removeValue();
                        notifyDataSetChanged();

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }

        });

        //edit data pass
        final String  tt,dd;
        tt = itemListMain.get(position).getTitle();
        dd = itemListMain.get(position).getDescription();

        holder.editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDialogView dialogView = new EditDialogView();
                dialogView.showDialog((AppCompatActivity) activity,tt,dd,key);
            }
        });

        holder.viewDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsDialogView dialogView = new DetailsDialogView();
                dialogView.showDialog((AppCompatActivity) activity,tt,dd);
            }
        });
        holder.fullchildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if(counter %2 ==1)
                {
                    holder.addtioonalLayout.setVisibility(View.VISIBLE);
                }
                else
                    holder.addtioonalLayout.setVisibility(View.GONE);
            }
        });
        holder.fullchildCV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                counter ++;

                if (counter % 2 == 1){

                    holder.addtioonalLayout.setVisibility(View.VISIBLE);

                }else if (counter % 2 == 0){

                    holder.addtioonalLayout.setVisibility(View.GONE);
                }


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemListMain.size();
    }

    @Override
    public Filter getFilter() {
    /*    if (filter == null) {
            filter = new CustomFilter(itemListSearch,InfoAdapter.this);
        }

        return filter;*/

      return new Filter() {
          @Override
          protected FilterResults performFiltering(CharSequence charSequence) {

              String charString = charSequence.toString();
              if(charString.isEmpty())
              {
                  itemListMain =itemListSearch;
              }
              else {
                  ArrayList<DataModel> filterList = new ArrayList<>();

                  for(DataModel dataModel : itemListSearch)
                  {
                      if(dataModel.getTitle().toLowerCase().contains(charString))
                      {
                          filterList.add(dataModel);
                      }
                  }
                  itemListMain = filterList;
              }

              FilterResults filterResults = new FilterResults();
              filterResults.values = itemListMain;
              return filterResults;
          }

          @Override
          protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

              itemListMain = (ArrayList<DataModel>) filterResults.values;
              notifyDataSetChanged();
          }
      };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titleTv ,descriptionTV,datetv,timeTV;
        public CardView fullchildCV;
        public LinearLayout shareLayout, addtioonalLayout,viewDetailLayout,editLayout,deletLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_TV);
            descriptionTV = itemView.findViewById(R.id.description_TV);
            datetv = (TextView) itemView.findViewById(R.id.date_TV);
            timeTV = (TextView) itemView.findViewById(R.id.time_TV);

            fullchildCV = (CardView) itemView.findViewById(R.id.fullChildCV);
            shareLayout = itemView.findViewById(R.id.share_LAYOUT);
            addtioonalLayout = (LinearLayout) itemView.findViewById(R.id.additional_LAYOUT);
            viewDetailLayout = (LinearLayout) itemView.findViewById(R.id.view_details_LAYOUT);
            editLayout = (LinearLayout) itemView.findViewById(R.id.edit_LAYOUT);
            deletLayout = (LinearLayout) itemView.findViewById(R.id.delete_LAYOUT);
        }
    }

    private boolean checkValidity() {
        if (titleET.getText().toString().equals("")) {

            titleET.setError("This field is required !!!");
            return false;

        } else if (descriptionET.getText().toString().equals("")) {

            descriptionET.setError("This field is required !!!");
            return false;

        } else {

            return true;
        }
    }
}
