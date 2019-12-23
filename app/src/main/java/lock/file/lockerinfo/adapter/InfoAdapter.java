package lock.file.lockerinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lock.file.lockerinfo.R;
import lock.file.lockerinfo.model.DataModel;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<DataModel> itemListMain,itemListSearch;
    RecyclerView recyclerView;
    private int counter =0;

    public InfoAdapter(Context context, ArrayList<DataModel> itemListMain, RecyclerView recyclerView) {
        this.context = context;
        this.itemListMain = itemListMain;
        this.itemListSearch=itemListMain;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_diary_layout,null,false);
        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final DataModel dataModel = itemListMain.get(position);

        holder.titleTv.setText(dataModel.getTitle());
        holder.descriptionTV.setText(dataModel.getDescription());
        holder.datetv.setText(dataModel.getDate());
        holder.timeTV.setText(dataModel.getTime());



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
        public LinearLayout addtioonalLayout,viewDetailLayout,editLayout,deletLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_TV);
            descriptionTV = itemView.findViewById(R.id.description_TV);
            datetv = (TextView) itemView.findViewById(R.id.date_TV);
            timeTV = (TextView) itemView.findViewById(R.id.time_TV);

            fullchildCV = (CardView) itemView.findViewById(R.id.fullChildCV);

            addtioonalLayout = (LinearLayout) itemView.findViewById(R.id.additional_LAYOUT);
            viewDetailLayout = (LinearLayout) itemView.findViewById(R.id.view_details_LAYOUT);
            editLayout = (LinearLayout) itemView.findViewById(R.id.edit_LAYOUT);
            deletLayout = (LinearLayout) itemView.findViewById(R.id.delete_LAYOUT);
        }
    }
}
